package br.com.victorwads.job.vicflix.features.shows.view

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.InputMethodManager
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.view.BaseActivity
import br.com.victorwads.job.vicflix.databinding.ListingActivityBinding
import br.com.victorwads.job.vicflix.commons.view.listing.LoadMoreListener
import br.com.victorwads.job.vicflix.commons.view.listing.LoaderListingAdapter
import br.com.victorwads.job.vicflix.features.security.AuthHelper
import br.com.victorwads.job.vicflix.features.shows.view.adapter.ShowViewHolder
import br.com.victorwads.job.vicflix.features.shows.viewModel.ShowListingStates
import br.com.victorwads.job.vicflix.features.shows.viewModel.ShowListingViewModel

class ShowListingActivity : BaseActivity() {

    private val layout by lazy { ListingActivityBinding.inflate(layoutInflater) }
    private val showsAdapter by lazy {
        LoaderListingAdapter(navigation::openShowDetails) { ShowViewHolder(layoutInflater, it) }
    }
    private val viewModel by lazy { ShowListingViewModel(this) }
    private val authHandler by lazy { AuthHelper(this) }
    private val canAuth by lazy { authHandler.isAvailable() }
    private var autoScroll = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViews()
        bindData()
        authHandler.handleAuth({ finish() }, { viewModel.loadMore() })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        menu.findItem(R.id.favorite).let {
            updateFavorite(it, viewModel.favorite)
        }
        if (!canAuth) {
            menu.removeItem(R.id.settings)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> navigation.openPreferences()
            R.id.favorite -> updateFavorite(item, !viewModel.favorite)
            else -> return false
        }
        return true
    }

    private fun bindViews() {
        setContentView(layout.root)
        layout.shows.adapter = showsAdapter
        layout.shows.addOnScrollListener(
            LoadMoreListener {
                if (shouldAutoLoad()) viewModel.loadMore()
            }
        )
        layout.root.layoutTransition = LayoutTransition()
        layout.inputSearch.setOnEditorActionListener { _, action, key ->
            if (
                action == IME_ACTION_SEARCH ||
                (key?.action == ACTION_DOWN && key?.keyCode == KEYCODE_ENTER)
            ) {
                search()
                true
            } else {
                false
            }
        }
        layout.clearSearch.setOnClickListener {
            cleanSearch()
        }
    }

    private fun bindData() {
        viewModel.state.observe(this) {
            when (it) {
                is ShowListingStates.Error -> {}
                is ShowListingStates.ShowsEnded -> autoScroll = false
                is ShowListingStates.AddShows -> {
                    title = getString(R.string.shows_listing_title)
                    showsAdapter.addItems(it.shows)
                }
                is ShowListingStates.Favorites -> {
                    title = getString(R.string.favorites_title)
                    showsAdapter.setItems(it.shows)
                }
                is ShowListingStates.SearchedShows -> {
                    title = getString(R.string.shows_searching_title)
                    showsAdapter.setItems(it.shows)
                }
                ShowListingStates.Loading -> {
                    showsAdapter.clear()
                }
            }
        }
    }

    private fun search() {
        layout.clearSearch.visibility = View.VISIBLE
        layout.inputSearch.apply {
            if (text.isNullOrEmpty()) {
                cleanSearch()
            }
            viewModel.search(text.toString())
            clearFocus()
            (getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
                hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }

    private fun cleanSearch() {
        viewModel.search("")
        layout.inputSearch.setText("")
        layout.clearSearch.visibility = View.GONE
    }

    private fun shouldAutoLoad() = autoScroll && viewModel.state.value is ShowListingStates.AddShows

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

}
