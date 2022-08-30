package br.com.victorwads.job.vicflix.commons.view.listing

import androidx.recyclerview.widget.RecyclerView

class LoadMoreListener(val onLoad: () -> Unit) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (!recyclerView.canScrollVertically(1)) {
            onLoad()
        }
    }
}
