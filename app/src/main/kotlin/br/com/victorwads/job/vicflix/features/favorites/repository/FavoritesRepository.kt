package br.com.victorwads.job.vicflix.features.favorites.repository

import android.content.Context
import android.content.SharedPreferences
import br.com.victorwads.job.vicflix.commons.repositories.BasePreferencesRepository
import br.com.victorwads.job.vicflix.features.shows.model.Show
import com.google.gson.JsonSyntaxException
import java.util.*

class FavoritesRepository(context: Context) : BasePreferencesRepository<Show>(context, FAVORITES_STORE_KEY) {

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

    override fun get(id: Int): Show? {
        val key = SHOW_KEY + id
        if (preferences.contains(key)) {
            try {
                return preferences.getString(key, null)?.let {
                    transform.fromJson(decrypt(it), Show::class.java)
                }
            } catch (_: JsonSyntaxException) {
                remove(id)
            } catch (_: IllegalArgumentException) {
                remove(id)
            }
        }
        return null
    }

    private fun getKey(id: Int) = SHOW_KEY + id.toString()

    fun isFavorite(show: Show): Boolean = index.contains(show.id)

    public override fun save(item: Show, id: Int) {
        preferences.edit().apply {
            index.add(id)
            putString(getKey(id), encrypt(transform.toJson(item)))
            saveIndex()
            apply()
            favoritesCache?.add(item)
        }
    }

    fun remove(show: Show) = remove(show.id)

    override fun remove(id: Int) {
        preferences.edit().apply {
            index.remove(id)
            remove(getKey(id))
            saveIndex()
            apply()
            favoritesCache?.removeIf { it.id == id }
        }
    }

    fun getAll(): List<Show> = favoritesCache ?: index
        .mapNotNull { get(it) }
        .sortedBy { it.name }
        .also { favoritesCache = it.toMutableList() }

    private class FavoritesUnexpectedException : Throwable()

    companion object {
        private const val FAVORITES_STORE_KEY = "favoritesStore"
        private const val FAVORITES_INDEX_KEY = "showListing"
        private var favoritesIndexCache: MutableList<Int>? = null

        private const val SHOW_KEY = "showItemCache"
        private var favoritesCache: MutableList<Show>? = null
    }
}
