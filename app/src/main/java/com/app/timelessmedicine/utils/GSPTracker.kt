package com.app.timelessmedicine.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.Log
import com.app.timelessmedicine.R

class GSPTracker(val context: Context,val activity: Activity, val showSettingsDialog:Boolean):Service() {


    private val mContext: Context? = null

    // Flag for GPS status
    internal var isGPSEnabled = false

    // Flag for network status
    internal var isNetworkEnabled = false

    // Flag for GPS status
    internal var canGetLocation = false

    internal var location: Location? = null // Location
    internal var latitude: Double = 0.toDouble() // Latitude
    internal var longitude: Double = 0.toDouble() // Longitude

    // The minimum distance to change Updates in meters
    private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 1000 // 10 meters

    // The minimum time between updates in milliseconds
    private val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong() // 1 minute

    // Declaring a Location Manager
    protected var locationManager: LocationManager? = null


    @SuppressLint("MissingPermission")
    fun getLocation(): Location? {
        try {


            locationManager = mContext?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // Getting GPS status
            isGPSEnabled = locationManager!!
                .isProviderEnabled(LocationManager.GPS_PROVIDER)

            // Getting network status
            isNetworkEnabled = locationManager!!
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGPSEnabled && !isNetworkEnabled) {
                // No network provider is enabled
                showSettingsAlert()
            } else {
                this.canGetLocation = true
                if (isNetworkEnabled) {
                    val requestPermissionsCode = 50

                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), mLocationListener
                    )
                    Log.d("Network", "Network")
                    if (locationManager != null) {
                        location = locationManager!!
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (location != null) {
                            latitude = location!!.latitude
                            longitude = location!!.longitude
                        }
                    }
                }
            }
            // If GPS enabled, get latitude/longitude using GPS Services
            if (isGPSEnabled) {
                if (location == null) {
                    if (ContextCompat.checkSelfPermission(
                            activity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            activity,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            50
                        )

                    } else {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), mLocationListener
                        )
                        Log.d("GPS Enabled", "GPS Enabled")
                        if (locationManager != null) {

                            location = locationManager!!
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (location != null) {
                                latitude = location!!.latitude
                                longitude = location!!.longitude
                            }
                        }
                    }
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return location
    }


    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app.
     */
    fun stopUsingGPS() {

    }


    private val mLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {

            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

        }

        override fun onProviderEnabled(provider: String) {

        }

        override fun onProviderDisabled(provider: String) {

        }
    }

    /**
     * Function to get latitude
     */
    fun getLatitude(): Double {
        if (location != null) {
            latitude = location!!.latitude
        }
        // return latitude
        return latitude
    }


    /**
     * Function to get longitude
     */
    fun getLongitude(): Double {
        if (location != null) {
            longitude = location!!.longitude
        }

        // return longitude
        return longitude
    }

    /**
     * Function to check GPS/Wi-Fi enabled
     *
     * @return boolean
     */
    fun canGetLocation(): Boolean {
        return this.canGetLocation
    }


    /**
     * Function to show settings alert dialog.
     * On pressing the Settings button it will launch Settings Options.
     */
    fun showSettingsAlert() {
        val alertDialog = AlertDialog.Builder(mContext)

        // Setting Dialog Title
        alertDialog.setTitle(mContext?.resources?.getString(R.string.GPS_is_settings))

        // Setting Dialog Message
        alertDialog.setMessage(mContext?.resources?.getString(R.string.GPS_is_not_enabled_Do_you_want_to_go_to_settings_menu))

        // On pressing the Settings button.
        alertDialog.setPositiveButton(
            mContext?.resources?.getString(R.string.settings)
        ) { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            mContext?.startActivity(intent)
        }

        // On pressing the cancel button
        alertDialog.setNegativeButton(
            mContext?.resources?.getString(R.string.cancel)
        ) { dialog, which -> dialog.cancel() }

        // Showing Alert Message
        alertDialog.show()
    }


    override fun onBind(arg0: Intent): IBinder? {
        return null
    }
}