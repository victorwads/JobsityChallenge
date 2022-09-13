package br.com.victorwads.job.vicflix.features.shows.repository

import android.content.Context
import br.com.victorwads.job.vicflix.commons.repositories.BasePreferencesRepository
import br.com.victorwads.job.vicflix.features.shows.model.Show
import java.lang.IllegalArgumentException
import java.util.Date

class ShowsCachedRepository(context: Context) : BasePreferencesRepository<List<Show>>(context, SHOWS_CACHE_STORE_KEY) {

    fun hasPage(page: Int) = preferences.contains(PAGE_CACHE_KEY + page)

    override fun get(id: Int): List<Show>? {
        checkExpiration()
        if (hasPage(id)) {
            try {
                return preferences.getString(PAGE_CACHE_KEY + id, null)?.let {
                    transform.fromJson(decrypt(it), Array<Show>::class.java).toList()
                }
            } catch (_: IllegalArgumentException) {
                remove(id)
            }
        }
        return null
    }

    override fun save(item: List<Show>, id: Int) {
        if (item.isEmpty()) return
        checkExpiration()
        preferences.edit().apply {
            putInt(PAGE_COUNT_KEY, id)
            putString(PAGE_CACHE_KEY + id, encrypt(transform.toJson(item)))
            apply()
        }
    }

    override fun remove(id: Int) {
        preferences.edit().apply {
            remove(PAGE_CACHE_KEY + id)
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
