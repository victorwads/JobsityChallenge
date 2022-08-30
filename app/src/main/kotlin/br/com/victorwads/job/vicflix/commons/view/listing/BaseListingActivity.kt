package br.com.victorwads.job.vicflix.commons.view.listing

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import br.com.victorwads.job.vicflix.commons.view.BaseActivity

abstract class BaseListingActivity<Model> : BaseActivity(), LoaderListingAdapter.EventsCallback<Model> {

    abstract val viewModel: ListingViewModel
    abstract val inputSearch: EditText
    abstract val clearSearch: View

    protected fun bindSearchViews() {
        inputSearch.setOnEditorActionListener { _, action, key ->
            if (
                action == EditorInfo.IME_ACTION_SEARCH ||
                (key?.action == KeyEvent.ACTION_DOWN && key.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                search()
                true
            } else {
                false
            }
        }
        clearSearch.setOnClickListener {
            cleanSearch()
        }
    }

    private fun search() {
        clearSearch.visibility = View.VISIBLE
        inputSearch.apply {
            if (text.isNullOrEmpty()) {
                cleanSearch()
            } else {
                viewModel.search(text.toString())
            }
            clearFocus()
            (getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
                hideSoftInputFromWindow(windowToken, 0)
            }
        }
    }

    private fun cleanSearch() {
        viewModel.search("")
        inputSearch.setText("")
        clearSearch.visibility = View.GONE
    }

    final override fun onScrollEnded() {
        if (viewModel.hasMorePages()) viewModel.loadMore()
    }
}
