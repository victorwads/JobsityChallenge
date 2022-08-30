package br.com.victorwads.job.vicflix.commons.view.listing

interface ListingViewModel {
    var hasMorePages: Boolean
    fun search(query: String)
    fun loadMore()
}
