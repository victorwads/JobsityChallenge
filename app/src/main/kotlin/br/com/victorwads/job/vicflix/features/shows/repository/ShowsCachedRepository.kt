package br.com.victorwads.job.vicflix.features.shows.repository

import android.content.Context
import br.com.victorwads.job.vicflix.features.shows.model.Show
import com.google.gson.Gson
import java.util.*

class ShowsCachedRepository(context: Context) {

    private val preferences = context.getSharedPreferences(SHOWS_CACHE_STORE_KEY, Context.MODE_PRIVATE)
    private val transform = Gson()

    fun hasPage(page: Int) = preferences.contains(PAGE_CACHE_KEY + page)

    fun getPage(page: Int): List<Show>? {
        checkExpiration()
        if (hasPage(page)) {
            return preferences.getString(PAGE_CACHE_KEY + page, null)?.let {
                transform.fromJson(it, Array<Show>::class.java).toList()
            }
        }
        return null
    }

    fun savePage(page: Int, shows: List<Show>) {
        if (shows.isEmpty()) return
        checkExpiration()
        preferences.edit().apply {
            putInt(PAGE_COUNT_KEY, page)
            putString(PAGE_CACHE_KEY + page, transform.toJson(shows))
            apply()
        }
    }

    private fun checkExpiration() {
        if (preferences.contains(CACHE_DATE_KEY)) {
            preferences.getLong(CACHE_DATE_KEY, 0).let {
                val today = Date().time
                val expireDate = it + CACHE_EXPIRE_TIME_MILISECONDS
                if (today > expireDate) {
                    clearCache()
                }
            }
        } else {
            preferences.edit().apply {
                putLong(CACHE_DATE_KEY, Date().time)
                apply()
            }
        }
    }

    private fun clearCache() {
        val lastSavedPage = preferences.getInt(PAGE_COUNT_KEY, 0)
        preferences.edit().apply {
            for (page in 0..lastSavedPage) {
                remove(PAGE_CACHE_KEY + page)
            }
            remove(PAGE_COUNT_KEY)
            remove(CACHE_DATE_KEY)
            apply()
        }
    }

    companion object {
        const val SHOWS_CACHE_STORE_KEY = "showsListingCache"
        const val PAGE_CACHE_KEY = "showsPageCache"
        const val PAGE_COUNT_KEY = "showsPagesCount"
        const val CACHE_DATE_KEY = "showsPageCacheDate"
        const val CACHE_EXPIRE_TIME_MILISECONDS =
            1000 * 60 * 60 * 24 * 7 // 1 Week = 1000 Mili * 60 Seconds * 60 Minutes * 24 Hours * 7 Days
    }
}
