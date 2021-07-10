/**
 * @author Isaac Young
 */
package com.comp103.idscanner.activities

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.comp103.idscanner.Id
import com.comp103.idscanner.R
import com.comp103.idscanner.databinding.MainActivityBinding
import com.comp103.idscanner.databinding.ManualInputBinding
import com.comp103.idscanner.factories.emptyItemAdapter
import com.comp103.idscanner.factories.getManualAddTextWatcher
import com.comp103.idscanner.factories.getSP
import com.comp103.idscanner.factories.itemAdapterFromString
import com.comp103.idscanner.itemAdapter.ItemAdapter
import com.comp103.idscanner.util.clearData
import com.comp103.idscanner.util.saveData
import com.comp103.idscanner.util.sendEmail
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.integration.android.IntentIntegrator
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


/**
 * Main Activity Class
 */
class MainActivity : AppCompatActivity() {

    lateinit var g: MainActivityBinding
    lateinit var adapter: ItemAdapter
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        g = MainActivityBinding.inflate(layoutInflater)
        val view: View = g.root
        setContentView(view)
        setSupportActionBar(g.toolbar)

        getSP(this).let {
            if (it != null) {
                sharedPreferences = it
            }
        }

        // Create adapter from sharedPreferences
        adapter = if (sharedPreferences.getString(getString(R.string.sp_items), null) != null) {
            itemAdapterFromString(
                this,
                sharedPreferences.getString(
                    getString(R.string.sp_items),
                    null
                )!!
            )
        } else {
            emptyItemAdapter(this)
        }

        // Attach the adapter to the recyclerview to populate items
        g.rvItems.adapter = adapter
        adapter.notifyDataSetChanged()
        // Set layout manager to position the items
        g.rvItems.layoutManager = LinearLayoutManager(this)

        g.fab.setOnClickListener {
            initiateScan()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun askToClearIds() {
        // Use the Builder class for convenient dialog construction
        val builder =
            MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)

        builder.setMessage(
            "Are you sure you wish to clear all IDs?"
        )
            .setPositiveButton(
                "Delete"
            ) { _: DialogInterface?, _: Int ->
                adapter.reset()
                clearData(sharedPreferences, getString(R.string.sp_items))
            }
            .setNegativeButton(
                "Cancel"
            ) { _: DialogInterface?, _: Int -> }
        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Manually add an entry
     */
    private fun addManually() {
        // Use the Builder class for convenient dialog construction
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
        val manualG: ManualInputBinding = ManualInputBinding.inflate(layoutInflater)

        manualG.editText.requestFocus()

        builder.setMessage("Student ID:")
            .setView(manualG.getRoot())
            .setPositiveButton(
                "Ok"
            ) { _: DialogInterface?, _: Int ->
                addItem(Id(manualG.editText.text.toString()))
            }
            .setNegativeButton(
                "Cancel"
            ) { _: DialogInterface?, _: Int ->
            }
        val dialog = builder.create()
        dialog.show()
        dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        val okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        okButton.isEnabled = true
        manualG.editText.addTextChangedListener(
            getManualAddTextWatcher(
                manualG.editText,
                okButton
            )
        )

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_add_manually -> {
                addManually()
                true
            }
            R.id.action_send -> {
                emailData()
                true
            }
            R.id.action_clear -> {
                askToClearIds()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Initiate a scan
     */
    private fun initiateScan() {
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Press back to finish");
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(false);
        integrator.captureActivity = (CaptureActivityPortrait::class.java)
        integrator.initiateScan()
    }

    /**
     * Email the data in the adapter
     */
    private fun emailData() {
        sendEmail(this, adapter.itemList);
    }

    /**
     * Add an item to the adapter
     */
    private fun addItem(item: Id) {
        adapter.addItem(item)
        saveData()
    }

    internal fun removeItem(item: Id) {
        adapter.removeItem(item)
        saveData()
    }

    internal fun saveData() {
        saveData(adapter.itemList, sharedPreferences, getString(R.string.sp_items))
    }

    /**
     * Get the results
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                // User cancelled
            } else {
                addItem(Id(result.contents))
                initiateScan()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}