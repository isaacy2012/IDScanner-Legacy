package com.comp103.idscanner.util

import android.content.Context
import android.content.Intent
import android.net.Uri


val address : String = "theAdmin@nathansoftware.com" // TODO remove test email
val subject : String = "Test Email From IDScanner App" // TODO update to include useful information including data

/**
 * Email Intent to send the adapter
 *
 * @param context - content to output to
 * @param input - list to output
 */
fun emailAdapter(context : Context, input : List<String>) {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.setData(Uri.parse("mailto:")) // only email apps should handle this

    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, parseInput(input))
    intent.setType("message/rfc822");

    if (intent.resolveActivity(context.getPackageManager()) != null) {
        context.startActivity(intent)
    }
}

/**
 * Parse a list of strings into a single CSV
 *
 * NOTE: this util function is basic for the MVP, but kept in case it is needed to format the output differently in future
 *
 * @param input - list to parse
 */
fun parseInput(input : List<String>) : String {

    return input.joinToString()
}