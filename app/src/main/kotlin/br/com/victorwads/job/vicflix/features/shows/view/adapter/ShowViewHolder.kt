package br.com.victorwads.job.vicflix.features.shows.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.accessibility.AccessibilityExtensions.Companion.configAccessibility
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import br.com.victorwads.job.vicflix.commons.view.ShimmerCallBack
import br.com.victorwads.job.vicflix.databinding.ListingShowItemBinding
import br.com.victorwads.job.vicflix.features.favorites.repository.FavoritesRepository
import com.squareup.picasso.Picasso

class ShowViewHolder(
    private val layout: ListingShowItemBinding,
    private val favoritesRepository: FavoritesRepository = FavoritesRepository(layout.root.context)
) : RecyclerView.ViewHolder(layout.root) {

    fun bindData(show: Show, onSelectShow: (Show) -> Unit) = with(layout) {
        labelName.text = show.name

        root.apply {
            showShimmer(true)
            configAccessibility(R.string.listing_select_action_description)
            setOnClickListener {
                onSelectShow(show)
            }
        }
        favorite.setOnClickListener {
            val isFavorite = favoritesRepository.isFavorite(show)
            if (isFavorite) {
                favoritesRepository.remove(show)
            } else {
                favoritesRepository.add(show)
            }
            updateIcon(show, !isFavorite)
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

    private fun ListingShowItemBinding.updateIcon(
        show: Show,
        isFavorite: Boolean = favoritesRepository.isFavorite(show)
    ) {
        favorite.visibility = View.VISIBLE
        favorite.setImageResource(
            if (favoritesRepository.isFavorite(show)) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite
        )
    }
}
