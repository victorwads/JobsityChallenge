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

    fun clear() = with(layout) {
        favorite.visibility = View.GONE
        poster.setImageDrawable(null)
        root.apply {
            contentDescription = context.getString(R.string.listing_loading)
            showShimmer(true)
            setOnClickListener(null)
        }
    }

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
