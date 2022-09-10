package br.com.victorwads.job.vicflix.features.favorites.repository

import android.content.Context
import android.content.SharedPreferences
import br.com.victorwads.job.vicflix.commons.repositories.BasePreferencesRepository
import br.com.victorwads.job.vicflix.features.shows.model.Show
import java.util.*

class FavoritesRepository(context: Context) : BasePreferencesRepository(context, FAVORITES_STORE_KEY) {

    init {
        if (favoritesIndexCache == null) {
            favoritesIndexCache = preferences.getString(FAVORITES_INDEX_KEY, null)?.let {
                transform.fromJson(it, Array<Int>::class.java).toMutableList()
            } ?: arrayListOf()
        }
    }

    private val index: MutableList<Int>
        get() = favoritesIndexCache ?: throw FavoritesUnexpectedException()

    private fun SharedPreferences.Editor.saveIndex() {
        putString(FAVORITES_INDEX_KEY, transform.toJson(index))
    }

    private fun get(id: Int): Show? = preferences.run {
        val key = SHOW_KEY + id
        if (preferences.contains(key)) {
            try {
                preferences.getString(key, null)?.let {
                    transform.fromJson(decrypt(it), Show::class.java)
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
                remove(id)
                null
            }
        } else {
            null
        }
    }

    private fun getKey(id: Int) = SHOW_KEY + id.toString()

    fun isFavorite(show: Show): Boolean = index.contains(show.id)

    fun add(show: Show) = preferences.edit().apply {
        index.add(show.id)
        putString(getKey(show.id), encrypt(transform.toJson(show)))
        saveIndex()
        apply()
        favoritesCache?.add(show)
    }

    fun remove(show: Show) = remove(show.id)

    fun remove(id: Int) = preferences.edit().apply {
        index.remove(id)
        remove(getKey(id))
        saveIndex()
        apply()
        favoritesCache?.removeIf { it.id == id }
    }

    fun getAll(): List<Show> = favoritesCache ?: index.mapNotNull {
        get(it)
    }.sortedBy { it.name }.toMutableList().also { favoritesCache = it }

    private class FavoritesCorruptedException : Throwable()
    private class FavoritesUnexpectedException : Throwable()

    companion object {
        private const val FAVORITES_STORE_KEY = "favoritesStore"
        private const val FAVORITES_INDEX_KEY = "showListing"
        private var favoritesIndexCache: MutableList<Int>? = null

        private const val SHOW_KEY = "showItemCache"
        private var favoritesCache: MutableList<Show>? = null
    }
}
