package com.mechamanul.avitointernshipweatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.mechamanul.avitointernshipweatherapp.domain.LocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume


@ExperimentalCoroutinesApi
class DefaultLocationProvider @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val app: Application
) : LocationService {
    override suspend fun getCurrentLocation(): Location? = with(Dispatchers.Main) {

        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            app,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager =
            app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!hasAccessFineLocationPermission) {
            throw Exception("You have to turn on both fine and coarse location")
        }
        if(!isGpsEnabled){
            throw Exception("You have to enable gps for thi")
        }

        return suspendCancellableCoroutine { cont ->
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


    }


}