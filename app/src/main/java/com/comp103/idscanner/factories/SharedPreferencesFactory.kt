/**
 * @author Isaac Young
 */
package com.comp103.idscanner.factories

import android.content.Context
import android.content.SharedPreferences

fun getSP(context: Context): SharedPreferences? {
    return context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
}
