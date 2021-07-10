package com.comp103.idscanner.factories

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

/**
 * Gets Manual Entry TextWatcher.
 *
 * @param Manual EntryInput the Manual Entry input
 * @param okButton    the ok button
 * @return the Manual Entry
 */
fun getManualAddTextWatcher(editText : EditText, okButton: Button): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            try {
                val trimmedRefillInput = editText.text.toString().trim { it <= ' ' }
                trimmedRefillInput.toLong()
                val manualInputInputPass = trimmedRefillInput.isNotEmpty()
                okButton.isEnabled = manualInputInputPass
            } catch (e: NumberFormatException) {
                okButton.isEnabled = false
            }
        }
        override fun afterTextChanged(s: Editable) {}
    }
}
