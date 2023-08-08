package nik.borisov.weathercompose.presentation.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class LocationServiceUtil @Inject constructor(
    private val application: Application
) {

    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    fun isPermissionGranted(permission: PermissionsToRequest): Boolean {
        return ContextCompat.checkSelfPermission(
            application,
            permission.permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationServiceEnabled(): Boolean {
        val hasAccessFineLocationPermission =
            isPermissionGranted(PermissionsToRequest.ForegroundFineLocation())
        val hasAccessCoarseLocationPermission =
            isPermissionGranted(PermissionsToRequest.ForegroundCoarseLocation())
        val locationManager = application.getSystemService(
            Context.LOCATION_SERVICE
        ) as LocationManager
        val isLocationEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return isLocationEnabled && (hasAccessCoarseLocationPermission || hasAccessFineLocationPermission)
    }

    @SuppressLint("MissingPermission")
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getCurrentLocation(): Location? {
        if (!isLocationServiceEnabled()) {
            return null
        }
        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token
            ).apply {
                if (isComplete) {
                    if (isSuccessful) {
                        cont.resume(result) {}
                    } else {
                        cont.resume(null) {}
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(it) {}
                }
                addOnFailureListener {
                    cont.resume(null) {}
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }
}