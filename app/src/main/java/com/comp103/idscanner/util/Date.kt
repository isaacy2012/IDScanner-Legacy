/**
 * @author Nathanael Rais
 */
package com.comp103.idscanner.util

import android.icu.text.SimpleDateFormat
import java.util.*

fun getCurrentTime() : String {
    val c: Date = Calendar.getInstance().time
    println("Current time => $c")

    val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
    return df.format(c)
}