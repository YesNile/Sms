package com.example.sms

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.requirePermission(
    permission: String,
    successDelegate: () -> Unit,
    failureDelegate: () -> Unit,
) {
    val permissionState = ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    )

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                successDelegate()
            } else {
                failureDelegate()
            }
        }

    if (permissionState != PackageManager.PERMISSION_GRANTED) {
        requestPermissionLauncher.launch(permission)
    } else {
        successDelegate()
    }
}