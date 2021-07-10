package com.comp103.idscanner.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity


const val address: String = "theAdmin@nathansoftware.com" // TODO remove test email
const val subject: String = "Test Email From IDScanner App" // TODO update to include useful information including data

/**
 * Email Intent to send the adapter
 *
 * @param context - content to output to
 * @param itemList - list to output
 */
fun emailAdapter(context: Context, itemList: List<String>) {
    val emailIntent = Intent(Intent.ACTION_SENDTO,
        Uri.parse("mailto:$address").buildUpon()
            .appendQueryParameter("subject", subject)
            .appendQueryParameter("body", listToEmailString(itemList))
            .build()
    )

    try {
        context.startActivity(Intent.createChooser(emailIntent, "Send Email"))
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "There are no email clients installed.",
            Toast.LENGTH_SHORT
        ).show()
    }
}
