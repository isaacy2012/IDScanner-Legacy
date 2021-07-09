package com.comp103.idscanner.util

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.comp103.idscanner.itemAdapter.ItemAdapter


val address : String = "theAdmin@nathansoftware.com" // TODO remove test email

/**
 * Email Intent to send the adapter
 */
fun emailAdapter(input : List<String>) {

}

fun parseInput(input : List<String>) : String {

    val output = StringBuilder()

    for (item in input) {
        output.append(item)
    }

    return output.toString()
}