package br.com.victorwads.job.vicflix.features.shows.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.view.listing.ShimmerCallBack
import br.com.victorwads.job.vicflix.databinding.ListingShowItemBinding
import br.com.victorwads.job.vicflix.features.favorites.repository.FavoritesRepository
import br.com.victorwads.job.vicflix.commons.view.listing.ShimmerLoadingViewHolder
import com.squareup.picasso.Picasso

class ShowViewHolder private constructor(private val layout: ListingShowItemBinding) : ShimmerLoadingViewHolder<Show>(
    layout.root, layout.root, R.string.shows_listing_select_action_description
) {

    constructor(inflater: LayoutInflater, parent: ViewGroup) : this(
        ListingShowItemBinding.inflate(inflater, parent, false)
    )

    private val favoritesRepository: FavoritesRepository = FavoritesRepository(layout.root.context)

    override fun clear() = with(layout) {
        favorite.visibility = View.GONE
        poster.setImageDrawable(null)
    }

    override fun loaded(show: Show, onSelectShow: (Show) -> Unit) = with(layout) {
        labelName.text = show.name
        favorite.setOnClickListener {
            val isFavorite = favoritesRepository.isFavorite(show)
            if (isFavorite) {
                favoritesRepository.remove(show)
            } else {
                favoritesRepository.add(show)
            }
            updateIcon(show)
        }
        loadPoster(show)
        updateIcon(show)
    }

    private fun ListingShowItemBinding.loadPoster(show: Show) {
        show.image?.medium?.let {
            Picasso.get().load(it).fit().centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(poster, ShimmerCallBack(root))
        }
    }

    private fun ListingShowItemBinding.updateIcon(show: Show) = with(favorite) {
        visibility = View.VISIBLE
        setImageResource(
            if (favoritesRepository.isFavorite(show)) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite
        )
    }
}
