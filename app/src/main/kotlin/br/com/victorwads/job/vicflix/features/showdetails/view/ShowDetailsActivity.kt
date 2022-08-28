package br.com.victorwads.job.vicflix.features.showdetails.view

import android.os.Bundle
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.view.BaseActivity
import br.com.victorwads.job.vicflix.databinding.ShowdetailsActivityBinding
import com.squareup.picasso.Picasso

class ShowDetailsActivity : BaseActivity() {

    private val layout by lazy { ShowdetailsActivityBinding.inflate(layoutInflater) }
    private val showShortDetails by lazy { intent.getParcelableExtra<Show>(EXTRA_SHOW) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.root)

        bindData()
    }

    private fun bindData() = layout.apply {
        showShortDetails?.let {
            title.text = it.name
            genre.text = it.genres.joinToString()
            summary.text = it.summary
            it.image?.original?.let { url ->
                Picasso.get().load(url).into(poster)
            }
            schedule.text = getString(
                R.string.showdetails_label_schedule_description,
                it.schedule.days.joinToString(),
                it.schedule.time
            )
        }
    }

    companion object {
        const val EXTRA_SHOW = "extra_show"
    }
}