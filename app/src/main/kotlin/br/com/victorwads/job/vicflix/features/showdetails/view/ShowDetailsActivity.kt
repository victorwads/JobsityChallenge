package br.com.victorwads.job.vicflix.features.showdetails.view

import android.os.Bundle
import br.com.victorwads.job.vicflix.commons.view.BaseActivity
import br.com.victorwads.job.vicflix.databinding.ShowdetailsActivityBinding

class ShowDetailsActivity : BaseActivity() {

    private val layout by lazy { ShowdetailsActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.root)
    }
}