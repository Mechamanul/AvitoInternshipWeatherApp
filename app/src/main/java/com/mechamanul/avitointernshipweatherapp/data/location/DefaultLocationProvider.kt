package com.mechamanul.avitointernshipweatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.util.asRunnable
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.tasks.Task
import com.mechamanul.avitointernshipweatherapp.domain.LocationService
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ExperimentalCoroutinesApi
class DefaultLocationProvider @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val app: Application
) : LocationService {
    override suspend fun getCurrentLocation(): Location? = withContext(Dispatchers.Main) {
        Log.d("LocationCall", "Started")

        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            app,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            app,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager =
            app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        Log.d("locationManager", "$locationManager")
        Log.d("gpsEnabled", "$isGpsEnabled")
        Log.d("coarseEnabled", "$hasAccessCoarseLocationPermission")
        Log.d("fineEnabled", "$hasAccessFineLocationPermission")
        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
            return@withContext null
        }

        val result = suspendCancellableCoroutine<Location?> { cont ->
            locationClient.lastLocation.apply {
                Log.d("coroutineSuspended", "started")
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result)
                    } else {
                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it)
                }
                addOnFailureListener {
                    cont.resume(null)
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
        Log.d("result", "$result")
        return@withContext result


    }


}