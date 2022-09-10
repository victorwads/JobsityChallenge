package br.com.victorwads.job.vicflix.features.persons.view

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.view.listing.ShimmerCallBack
import br.com.victorwads.job.vicflix.commons.view.listing.ShimmerLoadingViewHolder
import br.com.victorwads.job.vicflix.databinding.PersonsListingItemBinding
import br.com.victorwads.job.vicflix.features.persons.model.Person
import com.squareup.picasso.Picasso

class PersonViewHolder private constructor(private val layout: PersonsListingItemBinding) :
    ShimmerLoadingViewHolder<Person>(
        layout.root, layout.root, R.string.persons_listing_select_action_description
    ) {

    constructor(inflater: LayoutInflater, parent: ViewGroup) : this(
        PersonsListingItemBinding.inflate(inflater, parent, false)
    )

    override fun clear() = with(layout) {
        poster.setImageDrawable(null)
        labelName.text = ""
    }

    override fun loaded(data: Person, onSelectItem: (Person) -> Unit) = with(layout) {
        labelName.text = data.name
        loadPoster(data)
    }

    private fun PersonsListingItemBinding.loadPoster(person: Person) {
        person.image?.medium?.let {
            Picasso.get().load(it).fit().centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(poster, ShimmerCallBack(root))
        }
    }
}
