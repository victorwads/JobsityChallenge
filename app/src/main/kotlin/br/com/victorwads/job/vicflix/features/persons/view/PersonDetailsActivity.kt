package br.com.victorwads.job.vicflix.features.persons.view

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.ViewGroup
import br.com.victorwads.job.vicflix.R
import br.com.victorwads.job.vicflix.commons.view.listing.BaseListingActivity
import br.com.victorwads.job.vicflix.commons.view.listing.LoaderListingAdapter
import br.com.victorwads.job.vicflix.databinding.ListingActivityBinding
import br.com.victorwads.job.vicflix.features.persons.model.Person
import br.com.victorwads.job.vicflix.features.persons.viewModel.PersonListingStates
import br.com.victorwads.job.vicflix.features.persons.viewModel.PersonListingViewModel

class PersonDetailsActivity : BaseListingActivity<Person>() {

    private val layout by lazy { ListingActivityBinding.inflate(layoutInflater) }
    private val personAdapter by lazy { LoaderListingAdapter(layout.recyclerView, this) }
    private val _viewModel by lazy { PersonListingViewModel() }

    override val viewModel
        get() = _viewModel
    override val inputSearch
        get() = layout.inputSearch
    override val clearSearch
        get() = layout.clearSearch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViews()
        bindData()
    }

    private fun bindViews() {
        title = getString(R.string.personinfo_title)
        setContentView(layout.root)
        layout.root.layoutTransition = LayoutTransition()
        bindSearchViews()
    }

    private fun bindData() {
        viewModel.state.observe(this) {
            when (it) {
                PersonListingStates.Error -> TODO()
                PersonListingStates.Loading -> personAdapter.clear()
                is PersonListingStates.SearchedPersons -> personAdapter.setItems(it.persons)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup) = PersonViewHolder(layoutInflater, parent)

    override fun onSelectItem(item: Person) = Unit
}
