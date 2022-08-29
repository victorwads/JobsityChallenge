package br.com.victorwads.job.vicflix.features.shows.repository

import android.content.Context
import br.com.victorwads.job.vicflix.commons.repositories.model.Show
import com.google.gson.Gson

class ShowsCachedRepository(context: Context) {

    private val preferences = context.getSharedPreferences(SHOWS_CACHE_STORE_KEY, Context.MODE_PRIVATE)
    private val transform = Gson()

    fun hasPage(page: Int) = preferences.contains(SHOWS_CACHE_KEY + page)

    fun getPage(page: Int): List<Show>? {
        if (hasPage(page)) {
            return preferences.getString(SHOWS_CACHE_KEY + page, null)?.let {
                transform.fromJson(it, Array<Show>::class.java).toList()
            }
        }
        return null
    }

    fun savePage(page: Int, shows: List<Show>) {
        if (shows.isEmpty()) return
        preferences.edit().apply {
            putString(SHOWS_CACHE_KEY + page, transform.toJson(shows))
            apply()
        }
    }

    companion object {
        const val SHOWS_CACHE_STORE_KEY = "showsListingCache"
        const val SHOWS_CACHE_KEY = "showsPageCache"
    }
}
