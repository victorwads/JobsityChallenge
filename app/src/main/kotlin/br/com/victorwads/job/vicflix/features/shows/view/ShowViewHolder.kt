package br.com.victorwads.job.vicflix.features.shows.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.features.shows.model.Show
import br.com.victorwads.job.vicflix.commons.view.listing.ShimmerCallBack
import br.com.victorwads.job.vicflix.databinding.ShowsListingItemBinding
import br.com.victorwads.job.vicflix.features.favorites.repository.FavoritesRepository
import br.com.victorwads.job.vicflix.commons.view.listing.ShimmerLoadingViewHolder
import com.squareup.picasso.Picasso

class ShowViewHolder private constructor(private val layout: ShowsListingItemBinding) : ShimmerLoadingViewHolder<Show>(
    layout.root, layout.root, R.string.shows_listing_select_action_description
) {

    constructor(inflater: LayoutInflater, parent: ViewGroup) : this(
        ShowsListingItemBinding.inflate(inflater, parent, false)
    )

    private val favoritesRepository: FavoritesRepository = FavoritesRepository(layout.root.context)

    override fun clear() = with(layout) {
        favorite.visibility = View.GONE
        poster.setImageDrawable(null)
    }

    override fun loaded(data: Show, onSelectItem: (Show) -> Unit) = with(layout) {
        labelName.text = data.name
        favorite.setOnClickListener {
            val isFavorite = favoritesRepository.isFavorite(data)
            if (isFavorite) {
                favoritesRepository.remove(data)
            } else {
                favoritesRepository.add(data)
            }
            updateIcon(data)
        }
        loadPoster(data)
        updateIcon(data)
    }

    private fun ShowsListingItemBinding.loadPoster(show: Show) {
        show.image?.medium?.let {
            Picasso.get().load(it).fit().centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(poster, ShimmerCallBack(root))
        }
    }

    private fun ShowsListingItemBinding.updateIcon(show: Show) = with(favorite) {
        visibility = View.VISIBLE
        setImageResource(
            if (favoritesRepository.isFavorite(show)) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite
        )
    }
}
