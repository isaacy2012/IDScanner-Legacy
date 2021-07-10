package com.comp103.idscanner.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.comp103.idscanner.R
import com.comp103.idscanner.databinding.MainActivityBinding
import com.comp103.idscanner.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {

    lateinit var g: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        g = SettingsActivityBinding.inflate(layoutInflater)
        val view: View = g.root
        setContentView(view)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}