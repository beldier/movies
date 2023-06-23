package com.example.movies.data

import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    fun check(permission: Permission): Boolean
}

