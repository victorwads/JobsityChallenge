package br.com.victorwads.job.vicflix.commons.view

import android.app.Activity
import android.content.Intent
import br.com.victorwads.job.vicflix.commons.repositories.model.Episode
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.features.settings.view.SettingsActivity
import br.com.victorwads.job.vicflix.features.showdetails.view.ShowDetailsActivity
import br.com.victorwads.job.vicflix.features.showdetails.view.ShowDetailsActivity.Companion.EXTRA_SHOW
import br.com.victorwads.job.vicflix.features.showepisode.view.ShowEpisodeActivity
import br.com.victorwads.job.vicflix.features.showepisode.view.ShowEpisodeActivity.Companion.EXTRA_EPISODE

class Navigation(val activity: Activity) {

    fun openShowDetails(show: Show) = activity.startActivity(
        Intent(activity, ShowDetailsActivity::class.java).apply {
            putExtra(EXTRA_SHOW, show)
        }
    )

    fun openEpisodeDetails(episode: Episode) = activity.startActivity(
        Intent(activity, ShowEpisodeActivity::class.java).apply {
            putExtra(EXTRA_EPISODE, episode)
        }
    )

    fun openPreferences() = activity.startActivity(
        Intent(activity, SettingsActivity::class.java)
    )
}
