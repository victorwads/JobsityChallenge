package br.com.victorwads.job.vicflix.commons.view

import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Callback

class ShimmerCallBack(private val shimmer: ShimmerFrameLayout) : Callback {
    override fun onSuccess() = shimmer.hideShimmer()
    override fun onError(e: Exception?) = shimmer.hideShimmer()
}
