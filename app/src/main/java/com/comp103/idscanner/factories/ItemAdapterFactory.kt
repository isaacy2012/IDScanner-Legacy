package com.comp103.idscanner.factories

import com.comp103.idscanner.itemAdapter.ItemAdapter
import com.comp103.idscanner.util.stringToList
import java.util.ArrayList

/**
 * Empty item adapter.
 *
 * @return the item adapter
 */
fun emptyItemAdapter(): ItemAdapter {
    return ItemAdapter(ArrayList())
}

fun itemAdapterFromString(str: String): ItemAdapter {
    val list = stringToList(str)
    return ItemAdapter(list)
}

