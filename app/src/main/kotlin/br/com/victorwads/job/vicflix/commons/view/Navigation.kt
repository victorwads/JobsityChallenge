package br.com.victorwads.job.vicflix.commons.view

import android.app.Activity
import android.content.Intent
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.features.showdetails.view.ShowDetailsActivity
import br.com.victorwads.job.vicflix.features.showdetails.view.ShowDetailsActivity.Companion.EXTRA_SHOW

class Navigation(val activity: Activity) {

    fun openShowDetails(show: Show) = activity.startActivity(
        Intent(activity, ShowDetailsActivity::class.java).apply {
            putExtra(EXTRA_SHOW, show)
        }
    )
}