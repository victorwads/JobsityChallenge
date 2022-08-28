package br.com.victorwads.job.vicflix.commons.view

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected val navigation by lazy { Navigation(this) }

}