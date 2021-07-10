/**
 * @author Nathanael Rais, Isaac Young
 */
package com.comp103.idscanner.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.comp103.idscanner.Id


const val address: String = "theAdmin@nathansoftware.com" // TODO remove test email
const val subject: String = "[Comp103] IDScanner Participation - " // TODO update to include useful information including data

/**
 * Email Intent to send the adapter
 *
 * @param context - content to output to
 * @param itemList - list to output
 */
fun sendEmail(context: Context, itemList: List<Id>) {
    // generate a selector to point the intent to ACTION_SENDTO
    val emailSelectorIntent = Intent(Intent.ACTION_SENDTO)
    emailSelectorIntent.data = Uri.parse("mailto:")

    // generate intent to contain "EXTRA"s which are the address, subject, body of the email
    val emailIntent = Intent(Intent.ACTION_SEND)

    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject + getCurrentTime())
    emailIntent.putExtra(Intent.EXTRA_TEXT, listToEmailString(itemList))

    emailIntent.selector = emailSelectorIntent  // assign selector

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
