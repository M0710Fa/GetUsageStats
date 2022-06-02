package com.example.getusagestats

import android.app.AppOpsManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.getusagestats.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fileName = "datafile.txt"

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        permissionMessage()

        val repository = Repository(this)

        lifecycleScope.launch{
            val past = repository.readFile(fileName)
            val current = GetUsageStats(this@MainActivity).getUsageString()
            val usageData = past + current
            if (usageData != null) {
                repository.saveFile(fileName,usageData)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun permissionMessage(){
        if (!checkForPermission()) {
            Log.i(ContentValues.TAG, "The user may not allow the access to apps usage. ")
            Toast.makeText(
                this,
                "Failed to retrieve app usage statistics. " +
                        "You may need to enable access for this app through " +
                        "Settings > Security > Apps with usage access",
                Toast.LENGTH_LONG
            ).show()
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        } else {
            // We have the permission. Query app usage stats.
            val filePath = filesDir.path + "/myText.txt"
            Log.d("Test", "path : $filePath")
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkForPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), packageName)
        return mode == AppOpsManager.MODE_ALLOWED
    }
}