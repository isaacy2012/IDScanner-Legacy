/**
 * @author Isaac Young
 */
package com.comp103.idscanner.assertions

import com.comp103.idscanner.BuildConfig

fun assert(predicate: Boolean) {
    if (BuildConfig.DEBUG && predicate == false) {
        error("Assertion failed")
    }
}

fun assert(predicate: Boolean, message: String) {
    if (BuildConfig.DEBUG && predicate == false) {
        error(message)
    }

}