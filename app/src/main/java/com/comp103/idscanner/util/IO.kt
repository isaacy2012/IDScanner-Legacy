/**
 * @author Isaac Young
 */
package com.comp103.idscanner.util

import android.content.SharedPreferences
import com.comp103.idscanner.Id

fun write(stringToWrite: String, sharedPreferences: SharedPreferences, spItemsStr: String) {
    val editor = sharedPreferences.edit()
    editor.putString(spItemsStr, stringToWrite)
    editor.apply()
}

fun saveData(itemList: List<Id>, sharedPreferences: SharedPreferences, spItemsStr: String) {
    write(listToString(itemList), sharedPreferences, spItemsStr)
}

fun clearData(sharedPreferences: SharedPreferences, spItemsStr: String) {
    write("", sharedPreferences, spItemsStr)
}
