package com.developer.currency.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.LocaleList
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import java.util.*

class LocaleManager(context: Context) {
    companion object {
        const val LANGUAGE_RUSSIAN = "ru"
        private const val LANGUAGE_KEY = "language_key"
    }

    private var prefs: SharedPreferences? = null

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setLocale(c: Context): Context {
        return updateResources(c, getLanguage())
    }

    fun setNewLocale(c: Context, language: String): Context {
        persistLanguage(language)
        return updateResources(c, language)
    }

    private fun getLanguage(): String? {
        return prefs!!.getString(LANGUAGE_KEY, LANGUAGE_RUSSIAN)
    }

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String) {
        prefs?.edit()?.putString(LANGUAGE_KEY, language)?.commit()
    }

    @Suppress("DEPRECATION")
    private fun updateResources(context: Context, language: String?): Context {
        var ctx = context
        if (language != null) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val res = ctx.resources
            val config = Configuration(res.configuration)
            if (isAtLeastVersion(VERSION_CODES.N)) {
                setLocaleForApi24(config, locale)
                ctx = ctx.createConfigurationContext(config)
            } else {
                config.setLocale(locale)
                res.updateConfiguration(config, res.displayMetrics)
            }
        }
        return ctx
    }

    @RequiresApi(VERSION_CODES.N)
    private fun setLocaleForApi24(config: Configuration, target: Locale) {
        val set: MutableSet<Locale> = LinkedHashSet()
        set.add(target)
        val all = LocaleList.getDefault()
        for (i in 0 until all.size()) set.add(all[i])
        val locales = set.toTypedArray()
        config.setLocales(LocaleList(*locales))
    }

    private fun isAtLeastVersion(version: Int): Boolean {
        return Build.VERSION.SDK_INT >= version
    }
}