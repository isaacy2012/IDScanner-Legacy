/**
 * Author: Isaac Young
 */
package com.comp103.idscanner.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.comp103.idscanner.R
import com.comp103.idscanner.databinding.MainActivityBinding
import com.comp103.idscanner.factories.emptyItemAdapter
import com.comp103.idscanner.factories.getSP
import com.comp103.idscanner.factories.itemAdapterFromString
import com.comp103.idscanner.itemAdapter.ItemAdapter
import com.comp103.idscanner.util.emailAdapter
import com.comp103.idscanner.util.saveData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.integration.android.IntentIntegrator


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
                sharedPreferences.getString(getString(R.string.sp_items),
                null)!!)
        } else {
            emptyItemAdapter()
        }

        // Attach the adapter to the recyclerview to populate items
        g.rvItems.adapter = adapter
        adapter.notifyDataSetChanged()
        // Set layout manager to position the items
        g.rvItems.layoutManager = LinearLayoutManager(this)

        // TEST method to activate emailing methods
        findViewById<Button>(R.id.testButton).setOnClickListener {
            emailData()
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            initiateScan()
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
        emailAdapter(this, adapter.itemList);
    }

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                // User cancelled
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                // QR Code successfully scanned
//                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                adapter.addItem(result.contents)
                saveData(adapter.itemList, sharedPreferences, getString(R.string.sp_items))
                initiateScan()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}