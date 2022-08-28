package br.com.victorwads.job.vicflix.features.showdetails.view

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.repositories.model.Season
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.view.BaseActivity
import br.com.victorwads.job.vicflix.databinding.ShowdetailsActivityBinding
import br.com.victorwads.job.vicflix.databinding.ShowdetailsHeaderBinding
import br.com.victorwads.job.vicflix.features.showdetails.view.adapter.ShowSeasonEpisodesAdapter
import br.com.victorwads.job.vicflix.features.showdetails.view.adapter.SingleLayoutAdapter
import br.com.victorwads.job.vicflix.features.showdetails.viewModel.ShowDetailsStates
import br.com.victorwads.job.vicflix.features.showdetails.viewModel.ShowDetailsViewModel
import br.com.victorwads.job.vicflix.features.shows.viewModel.ShowListingViewModel
import com.squareup.picasso.Picasso
import java.lang.RuntimeException

class ShowDetailsActivity : BaseActivity() {

    private lateinit var headerLayout: ShowdetailsHeaderBinding
    private val recyclerView by lazy { ShowdetailsActivityBinding.inflate(layoutInflater).root }
    private val showShortDetails by lazy { intent.getParcelableExtra<Show>(EXTRA_SHOW) }
    private val viewModel by lazy {
        ShowDetailsViewModel(showShortDetails ?: throw RuntimeException("no show provided"))
    }

    private val adapters = ConcatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (showShortDetails == null) {
            return finish()
        }

        bindView()
    }

    private fun bindView() {
        setContentView(recyclerView)
        SingleLayoutAdapter({
            ShowdetailsHeaderBinding.inflate(layoutInflater, it, false).also { layout ->
                headerLayout = layout
                bindLiveDate()
            }
        }, {
            bindData(it)
        }
        ).also { adapters.addAdapter(it) }
        recyclerView.adapter = adapters
    }

    private fun bindData(layout: ShowdetailsHeaderBinding) = layout.apply {
        showShortDetails?.let { show ->
            title.text = show.name
            genre.text = show.genres?.joinToString()
                ?: getString(R.string.no_info)
            summary.text = show.summary?.let { Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT) }
                ?: getString(R.string.no_info)

            show.image?.original?.let { url -> Picasso.get().load(url).into(poster) }
            show.schedule?.let {
                schedule.text = getString(
                    R.string.showdetails_label_schedule_description,
                    it.days.joinToString(), it.time
                )
            }
        }
    }

    private fun bindLiveDate() {
        viewModel.state.observe(this) {
            when (it) {
                is ShowDetailsStates.SeasonsLoaded -> addSeasons(it.seasons)
                is ShowDetailsStates.Error -> hideLoading()
                ShowDetailsStates.HideLoading -> hideLoading()
                ShowDetailsStates.Loading -> showLoading()
            }
        }
    }

    private fun addSeasons(seasons: List<Season>) {
        hideLoading()
        seasons.forEach {
            adapters.addAdapter(ShowSeasonEpisodesAdapter(it, layoutInflater))
            adapters.notifyDataSetChanged()
        }
    }

    private fun showLoading() {
        headerLayout.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        headerLayout.progressBar.visibility = View.GONE
    }

    companion object {
        const val EXTRA_SHOW = "extra_show"
    }
}