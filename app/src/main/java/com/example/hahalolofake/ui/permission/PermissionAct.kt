package com.example.hahalolofake.ui.permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.hahalolofake.base.activities.BaseActivity
import com.example.hahalolofake.databinding.ActivityPermissionBinding
import com.example.hahalolofake.ui.main_v2.MainV2Act
import com.example.hahalolofake.utils.DeviceUtil

@Suppress("CAST_NEVER_SUCCEEDS", "DEPRECATION")
class PermissionAct : BaseActivity() {
    private lateinit var binding: ActivityPermissionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnGo.setOnClickListener {
            if (binding.switch1.isChecked && binding.switchNotification.isChecked) {
                startActivity(Intent(this, MainV2Act::class.java))
                finish()
            }
        }
        binding.switch1.setOnCheckedChangeListener { _, _ ->
            checkStorePermission()
        }
        binding.switchNotification.setOnCheckedChangeListener { _, _ ->
            checkNotificationPermission()
        }
        maxPermission()
        hideSystemUI()
    }

    private fun checkStorePermission() {
        try {
            if (DeviceUtil.hasStoragePermission(this)) {
                binding.switch1.isChecked = true
                binding.switch1.isEnabled = false
            } else {
                binding.switch1.isEnabled = true
                binding.switch1.isChecked = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_VIDEO),
                    REQUEST_CODE_STORAGE_PERMISSION
                )
                else ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_CODE_STORAGE_PERMISSION
                )
            }
        } catch (e: Exception) {

        }
    }

    private fun checkNotificationPermission() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", packageName, null)
        startActivity(intent)
    }

    private fun maxPermission() {
        if (!binding.switch1.isChecked) {
            binding.switch1.setOnClickListener {
                indexClickPermissionStore++
                if (indexClickPermissionStore > 2) {
                    startSettingApp()
                }
            }
        }
    }

    private fun startSettingApp() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        requestAppSettingsLauncher.launch(intent)
    }

    private val requestAppSettingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        }

    private fun checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE
            )
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (binding.switch1.isChecked) {
            binding.btnGo.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (indexClickPermissionStore > 2) {
            checkStorePermission()
        }
        if (indexClickPermissionCamera > 2) {
            checkCameraPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_STORAGE_PERMISSION -> {
                binding.switch1.isChecked =
                    grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.switch1.isChecked = DeviceUtil.hasStoragePermission(this)
    }

    override fun onStop() {
        super.onStop()
        hideSystemUI()
    }

    companion object {
        const val REQUEST_CODE_STORAGE_PERMISSION = 101
        const val CAMERA_PERMISSION_CODE = 1001
        var indexClickPermissionStore = 0
        var indexClickPermissionCamera = 0
    }
}