package com.comp103.idscanner.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity


const val address: String = "theAdmin@nathansoftware.com" // TODO remove test email
const val subject: String =
    "Test Email From IDScanner App" // TODO update to include useful information including data

/**
 * Email Intent to send the adapter
 *
 * @param context - content to output to
 * @param input - list to output
 */
fun emailAdapter(context: Context, input: List<String>) {
    val intent = Intent(
        Intent.ACTION_SENDTO,
        Uri.fromParts("mailto", address, null)
    )
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, parseInput(input))

    try {
        context.startActivity(Intent.createChooser(intent, "Send Email"))
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "There are no email clients installed.",
            Toast.LENGTH_SHORT
        ).show()
    }
}

/**
 * Parse a list of strings into a single CSV
 *
 * NOTE: this util function is basic for the MVP, but kept in case it is needed to format the
 * output differently in future
 *
 * @param input - list to parse
 */
fun parseInput(input: List<String>): String {
    return input.joinToString()
}