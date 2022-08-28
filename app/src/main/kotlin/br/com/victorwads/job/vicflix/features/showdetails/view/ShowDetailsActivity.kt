package br.com.victorwads.job.vicflix.features.showdetails.view

import android.os.Bundle
import androidx.recyclerview.widget.ConcatAdapter
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.view.BaseActivity
import br.com.victorwads.job.vicflix.databinding.ShowdetailsActivityBinding
import br.com.victorwads.job.vicflix.databinding.ShowdetailsHeaderBinding
import br.com.victorwads.job.vicflix.features.showdetails.view.adapter.SingleLayoutAdapter
import com.squareup.picasso.Picasso

class ShowDetailsActivity : BaseActivity() {

    private val recyclerView by lazy { ShowdetailsActivityBinding.inflate(layoutInflater).root }
    private val showShortDetails by lazy { intent.getParcelableExtra<Show>(EXTRA_SHOW) }

    private val adapters = ConcatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(recyclerView)

        bindView()
    }

    private fun bindView() {
        SingleLayoutAdapter(
            { ShowdetailsHeaderBinding.inflate(layoutInflater, it, false) },
            { bindData(it) }
        ).let { adapters.addAdapter(it) }
        recyclerView.adapter = adapters
    }

    private fun bindData(layout: ShowdetailsHeaderBinding) = layout.apply {
        showShortDetails?.let { show ->
            title.text = show.name
            genre.text = show.genres?.joinToString() ?: getString(R.string.no_info)
            summary.text = show.summary ?: getString(R.string.no_info)

            show.image?.original?.let { url -> Picasso.get().load(url).into(poster) }
            show.schedule?.let {
                schedule.text = getString(
                    R.string.showdetails_label_schedule_description,
                    it.days.joinToString(), it.time
                )
            }
        }
    }

    companion object {
        const val EXTRA_SHOW = "extra_show"
    }
}