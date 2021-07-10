package com.comp103.idscanner.util

/**
 * Parse a list of strings into a single CSV
 *
 * NOTE: this util function is basic for the MVP, but kept in case it is needed to format the
 * output differently in future
 *
 * @param list - list to parse
 */
fun listToString(list: List<String>): String {
    return list.joinToString(",\n")
}

/**
 * Parse a list of strings into a single CSV for email
 *
 * NOTE: this util function is basic for the MVP, but kept in case it is needed to format the
 * output differently in future
 *
 * @param list - list to parse
 */
fun listToEmailString(list: List<String>): String {
    return list.joinToString(",<br>")
}
