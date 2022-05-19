package com.developer.valyutaapp.core.database

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    private val PREFS_NAME = "currency"
    private val LANG = "language"
    private val BOOL = "bool"

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveLang(lang: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(LANG, lang).apply()
    }

    fun getLang(): String {
        return sharedPref.getString(LANG, "")!!
    }

    fun saveBool(bool: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(BOOL, bool).apply()
    }

    fun getBool(): String {
        return sharedPref.getString(BOOL, "")!!
    }
}