package br.com.victorwads.job.vicflix.features.showepisode.view

import android.os.Bundle
import android.view.Gravity
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.repositories.model.Episode
import br.com.victorwads.job.vicflix.commons.view.BaseActivity
import br.com.victorwads.job.vicflix.databinding.EpisodeActivityBinding
import com.squareup.picasso.Picasso

class ShowEpisodeActivity : BaseActivity() {

    private val layout by lazy { EpisodeActivityBinding.inflate(layoutInflater) }
    private val episodeDetails by lazy { intent.getParcelableExtra<Episode>(EXTRA_EPISODE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (episodeDetails == null) {
            return finish()
        }

        bindView()
        bindData()
    }

    private fun bindData() {
        episodeDetails?.let { episode ->
            title = episode.name.takeIf {
                !it.isNullOrEmpty()
            } ?: getString(R.string.showdetails_episode, episode.number)
        }
        setContentView(layout.root)
    }

    private fun bindView() = layout.apply {
        episodeDetails?.let { episode ->
            season.text = episode.season.toString()
            number.text = episode.number.toString()
            summary.text = episode.summary

            episode.image?.original?.let { url ->
                Picasso.get().load(url).fit().centerCrop(Gravity.TOP).into(poster)
            }
        }
    }

    companion object {
        const val EXTRA_EPISODE = "extra_episode"
    }
}
