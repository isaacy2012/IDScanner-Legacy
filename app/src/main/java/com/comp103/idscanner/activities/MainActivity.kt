/**
 * Author: Isaac Young
 */
package com.comp103.idscanner.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.comp103.idscanner.R
import com.comp103.idscanner.databinding.MainActivityBinding
import com.comp103.idscanner.itemAdapter.ItemAdapter
import com.comp103.idscanner.itemAdapter.emptyItemAdapter
import com.comp103.idscanner.util.Utils
import com.comp103.idscanner.util.emailAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.integration.android.IntentIntegrator


/**
 * Main Activity Class
 */
class MainActivity : AppCompatActivity() {

    lateinit var g: MainActivityBinding
    lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        g = MainActivityBinding.inflate(layoutInflater)
        val view: View = g.root
        setContentView(view)
        setSupportActionBar(g.toolbar)

        // Create adapter from empty adapter
        adapter = emptyItemAdapter()
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
        emailAdapter(adapter.get());
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
                initiateScan()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}