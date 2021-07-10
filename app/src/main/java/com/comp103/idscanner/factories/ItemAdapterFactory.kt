/**
 * @author Isaac Young
 */
package com.comp103.idscanner.factories

import android.content.Context
import com.comp103.idscanner.itemAdapter.ItemAdapter
import com.comp103.idscanner.util.stringToList
import java.util.ArrayList

/**
 * Empty item adapter.
 *
 * @return the item adapter
 */
fun emptyItemAdapter(context: Context): ItemAdapter {
    return ItemAdapter(context, ArrayList())
}

fun itemAdapterFromString(context: Context, str: String): ItemAdapter {
    val list = stringToList(str)
    return ItemAdapter(context, list)
}

