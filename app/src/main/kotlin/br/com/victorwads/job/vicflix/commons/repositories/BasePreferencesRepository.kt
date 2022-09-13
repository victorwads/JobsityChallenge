package br.com.victorwads.job.vicflix.commons.repositories

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.Base64

abstract class BasePreferencesRepository<T>(context: Context, preferencesKey: String) {

    private val encoder = Base64.getEncoder()
    private val decoder = Base64.getDecoder()

    protected val preferences: SharedPreferences = context.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE)
    protected val transform = Gson()

    protected fun decrypt(base: String): String = String(decoder.decode(base.toByteArray()))

    protected fun encrypt(json: String): String = encoder.encodeToString(json.toByteArray())

    abstract fun get(id: Int): T?

    abstract fun remove(id: Int)

    abstract fun save(item: T, id: Int)
}
