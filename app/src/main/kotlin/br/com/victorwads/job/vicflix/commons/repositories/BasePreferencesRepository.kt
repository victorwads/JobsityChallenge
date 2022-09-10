package br.com.victorwads.job.vicflix.commons.repositories

import android.content.Context
import com.google.gson.Gson
import java.util.Base64

abstract class BasePreferencesRepository(
    context: Context,
    preferencesKey: String
) {

    protected val preferences = context.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE)
    protected val transform = Gson()
    private val encoder = Base64.getEncoder()
    private val decoder = Base64.getDecoder()

    protected fun decrypt(base: String): String = String(decoder.decode(base.toByteArray()))

    protected fun encrypt(json: String): String = encoder.encodeToString(json.toByteArray())
}
