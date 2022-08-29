package br.com.victorwads.job.vicflix.features.shows.view

import android.animation.LayoutTransition
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.view.BaseActivity
import br.com.victorwads.job.vicflix.databinding.ListingActivityBinding
import br.com.victorwads.job.vicflix.features.shows.view.adapter.ShowsAdapter
import br.com.victorwads.job.vicflix.features.shows.viewModel.ShowListingStates
import br.com.victorwads.job.vicflix.features.shows.viewModel.ShowListingViewModel

class ShowListingActivity : BaseActivity() {

    private val layout by lazy { ListingActivityBinding.inflate(layoutInflater) }
    private val showsAdapter by lazy { ShowsAdapter(layoutInflater, navigation::openShowDetails) }
    private val showsFavoritesAdapter by lazy { ShowsAdapter(layoutInflater, navigation::openShowDetails, false) }
    private val viewModel by lazy { ShowListingViewModel(this) }

    private var autoScroll = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.root)

        bindViews()
        bindData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavorites()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.favorite).let {
            updateFavorite(it, viewModel.favorite)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> navigation.openPreferences()
            R.id.favorite -> {
                updateFavorite(item, !viewModel.favorite)
                filterFavorites()
            }
            else -> return false
        }
        return true
    }

    private fun updateFavorite(item: MenuItem, value: Boolean) {
        viewModel.favorite = value
        item.setIcon(
            if (value) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite
        )
        layout.inputSearch.visibility =
            if (value) View.GONE
            else View.VISIBLE
    }

    private fun filterFavorites() {
        if (viewModel.favorite) {
            layout.shows.adapter = showsFavoritesAdapter
        } else {
            layout.shows.adapter = showsAdapter
        }
    }

    private fun bindViews() {
        layout.shows.adapter = showsAdapter
        layout.shows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1) && shouldAutoLoad()) {
                    viewModel.loadMore()
                }
            }
        })
        layout.root.layoutTransition = LayoutTransition()
        layout.inputSearch.setOnEditorActionListener { textView, _, _ ->
            textView.apply {
                viewModel.search(text.toString())
                clearFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
                    hideSoftInputFromWindow(windowToken, 0)
                }
            }
            true
        }
    }

    private fun shouldAutoLoad() = autoScroll &&
            viewModel.state.value is ShowListingStates.AddShows

    private fun bindData() {
        viewModel.state.observe(this) {
            when (it) {
                is ShowListingStates.AddShows -> addShows(it.shows)
                is ShowListingStates.CleanAddShows -> addShows(it.shows, true)
                is ShowListingStates.Favorites -> showsFavoritesAdapter.addItems(it.shows, true)
                is ShowListingStates.Error -> hideLoading()
                ShowListingStates.Loading -> showLoading()
                ShowListingStates.ShowsEnded -> {
                    hideLoading()
                    autoScroll = false
                }
            }
        }
    }

    private fun addShows(it: List<Show>, clean: Boolean = false) {
        showsAdapter.addItems(it, clean)
        hideLoading()
    }

    private fun showLoading() = layout.apply { progressBar.visibility = View.VISIBLE }

    private fun hideLoading() = layout.apply { progressBar.visibility = View.GONE }
}
