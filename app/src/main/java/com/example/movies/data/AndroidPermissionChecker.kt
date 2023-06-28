package com.example.movies.data

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class AndroidPermissionChecker(private val application: Application) :
    com.example.movies.data.PermissionChecker {

    override fun check(permission: com.example.movies.data.PermissionChecker.Permission): Boolean =
        ContextCompat.checkSelfPermission(
            application,
            permission.toAndroidId()
        ) == PackageManager.PERMISSION_GRANTED
}

private fun com.example.movies.data.PermissionChecker.Permission.toAndroidId() = when (this) {
    com.example.movies.data.PermissionChecker.Permission.COARSE_LOCATION -> Manifest.permission.ACCESS_COARSE_LOCATION
}