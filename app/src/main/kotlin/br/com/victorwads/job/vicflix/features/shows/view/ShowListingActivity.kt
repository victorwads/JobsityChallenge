package br.com.victorwads.job.vicflix.features.shows.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.view.BaseActivity
import br.com.victorwads.job.vicflix.databinding.ListingActivityBinding
import br.com.victorwads.job.vicflix.features.shows.view.adapter.ShowsAdapter
import br.com.victorwads.job.vicflix.features.shows.viewModel.ShowListingStates
import br.com.victorwads.job.vicflix.features.shows.viewModel.ShowListingViewModel

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
                if (recyclerView.canScrollVertically(1) && shouldAutoLoad()) {
                    viewModel.loadMore()
                }
            }
        })
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
                is ShowListingStates.Error -> TODO()
                ShowListingStates.Loading -> showLoading()
                ShowListingStates.ShowsEnded -> autoScroll = false
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
