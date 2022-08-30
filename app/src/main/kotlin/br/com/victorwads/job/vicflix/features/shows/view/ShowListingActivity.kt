package br.com.victorwads.job.vicflix.features.shows.view

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.view.listing.BaseListingActivity
import br.com.victorwads.job.vicflix.databinding.ListingActivityBinding
import br.com.victorwads.job.vicflix.commons.view.listing.LoaderListingAdapter
import br.com.victorwads.job.vicflix.features.security.AuthHelper
import br.com.victorwads.job.vicflix.features.shows.view.adapter.ShowViewHolder
import br.com.victorwads.job.vicflix.features.shows.viewModel.ShowListingStates
import br.com.victorwads.job.vicflix.features.shows.viewModel.ShowListingViewModel

class ShowListingActivity : BaseListingActivity<Show>() {

    private val layout by lazy { ListingActivityBinding.inflate(layoutInflater) }
    private val showsAdapter by lazy { LoaderListingAdapter(layout.recyclerView, this) }
    private val authHandler by lazy { AuthHelper(this) }
    private val _viewModel by lazy { ShowListingViewModel(this) }

    override val viewModel
        get() = _viewModel
    override val inputSearch
        get() = layout.inputSearch
    override val clearSearch
        get() = layout.clearSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViews()
        bindData()
        authHandler.handleAuth({ finish() }, { viewModel.loadMore() })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        if (!authHandler.isAvailable()) menu.removeItem(R.id.settings)
        updateFavoriteViews(menu.findItem(R.id.favorite), viewModel.favorite)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> navigation.openPreferences()
            R.id.favorite -> updateFavoriteViews(item, !viewModel.favorite)
            else -> return false
        }
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup) = ShowViewHolder(layoutInflater, parent)

    override fun onSelectItem(show: Show) = navigation.openShowDetails(show)

    private fun bindViews() {
        setContentView(layout.root)
        layout.root.layoutTransition = LayoutTransition()
        bindSearchViews()
    }

    private fun bindData() {
        viewModel.state.observe(this) {
            when (it) {
                is ShowListingStates.Error -> {}
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

    private fun updateFavoriteViews(item: MenuItem, value: Boolean) {
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
