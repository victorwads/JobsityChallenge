package br.com.victorwads.job.vicflix.features.listing.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.victorwads.job.vicflix.commons.view.BaseActivity
import br.com.victorwads.job.vicflix.databinding.ListingActivityBinding
import br.com.victorwads.job.vicflix.features.listing.view.adapter.ShowsAdapter
import br.com.victorwads.job.vicflix.features.listing.viewModel.ShowListingStates
import br.com.victorwads.job.vicflix.features.listing.viewModel.ShowListingViewModel

class ShowListingActivity : BaseActivity() {

    private val layout by lazy { ListingActivityBinding.inflate(layoutInflater) }
    private val showsAdapter by lazy { ShowsAdapter(layoutInflater, navigation::openShowDetails) }
    private val viewModel by lazy { ShowListingViewModel() }

    private var autoScroll = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.root)

        bindViews()
        bindData()
    }

    private fun bindViews() {
        layout.shows.adapter = showsAdapter
        layout.shows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (autoScroll && recyclerView.canScrollVertically(1) && isNotLoading()) {
                    viewModel.loadMore()
                }
            }
        })
    }

    private fun isNotLoading() = viewModel.state.value !is ShowListingStates.Loading

    private fun bindData() {
        viewModel.state.observe(this) {
            when (it) {
                is ShowListingStates.AddShows -> addShows(it)
                is ShowListingStates.CleanAddShows -> TODO()
                is ShowListingStates.Error -> TODO()
                ShowListingStates.HideLoading -> hideLoading()
                ShowListingStates.Loading -> showLoading()
                ShowListingStates.ShowsEnded -> autoScroll = false
            }
        }
    }

    private fun addShows(it: ShowListingStates.AddShows) {
        showsAdapter.addItems(it.moreShows)
        hideLoading()
    }

    private fun showLoading() = layout.apply { progressBar.visibility = View.VISIBLE }

    private fun hideLoading() = layout.apply { progressBar.visibility = View.GONE }

}