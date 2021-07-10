package com.comp103.idscanner.util

import android.content.SharedPreferences

fun saveData(itemList: List<String>, sharedPreferences: SharedPreferences, spItemsStr: String) {
    val editor = sharedPreferences.edit()
    editor.putString(spItemsStr, listToString(itemList))
    editor.apply()
}