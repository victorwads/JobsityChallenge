package br.com.victorwads.job.vicflix.features.showdetails.view

import android.os.Bundle
import android.view.Gravity
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
import com.squareup.picasso.Picasso

class ShowDetailsActivity : BaseActivity() {

    private lateinit var headerLayout: ShowdetailsHeaderBinding
    private val recyclerView by lazy { ShowdetailsActivityBinding.inflate(layoutInflater).root }
    private val showShortDetails by lazy { intent.getParcelableExtra<Show>(EXTRA_SHOW) }
    private lateinit var viewModel: ShowDetailsViewModel

    private val adapters = ConcatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showShortDetails?.let {
            viewModel = ShowDetailsViewModel(it)
        } ?: return finish()

        bindView()
    }

    private fun bindView() {
        title = showShortDetails?.name
        setContentView(recyclerView)
        SingleLayoutAdapter(
            {
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
            genre.text = show.genres?.joinToString()
            summary.text = show.summary

            show.image?.original?.let { url ->
                poster.contentDescription = getString(R.string.showdetails_poster_description, show.name)
                Picasso.get().load(url).fit().centerCrop(Gravity.TOP)
                    .error(R.drawable.ic_launcher_background)
                    .into(poster)
            }

            show.schedule?.let {
                schedule.text = if (it.time.isNullOrEmpty() && it.days.isNullOrEmpty()) {
                    null
                } else getString(
                    R.string.showdetails_label_schedule_description,
                    it.days?.joinToString(), it.time
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
            adapters.addAdapter(
                ShowSeasonEpisodesAdapter(it, layoutInflater, navigation, this, viewModel)
            )
        }
        adapters.notifyItemRangeInserted(1, seasons.size)
    }

    private fun showLoading() {
        headerLayout.root.showShimmer(true)
    }

    private fun hideLoading() {
        headerLayout.root.hideShimmer()
    }

    companion object {
        const val EXTRA_SHOW = "extra_show"
    }
}
