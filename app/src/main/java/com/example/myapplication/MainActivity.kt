package com.example.myapplication

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        // The implementation goes here
            permissions ->
        when {
            permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Access granted
                fusedLocationClient.lastLocation.addOnSuccessListener {
                        location : Location? ->
                    // Got last known location
                    findViewById<TextView>(R.id.textV).text = location.toString()
                }

            } else -> {
                // No location access granted
                findViewById<TextView>(R.id.textV).text = "Access was denied by app permission settings"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)

        locationPermissionRequest.launch(arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION))
    }

}