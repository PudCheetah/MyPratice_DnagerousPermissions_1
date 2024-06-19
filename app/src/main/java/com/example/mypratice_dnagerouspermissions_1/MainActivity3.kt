package com.example.mypratice_dnagerouspermissions_1

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mypratice_dnagerouspermissions_1.databinding.ActivityMainBinding

//
//
//使用Activity Result API的作法
//請求多重權限
//
//
class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var captureImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionsLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestPermissionsLauncher = requestPermissionsLauncher_set()
        captureImageLauncher = captureImageLauncher_set()
        binding.btn2.setOnClickListener {
            requestPermissionsLauncher.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_CALENDAR))
        }
        setContentView(binding.root)
    }

    //設計跳轉的intent
    fun activityCameral() {
        var myIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        captureImageLauncher.launch(myIntent)
    }

    //負責處理跳轉
    fun captureImageLauncher_set(): ActivityResultLauncher<Intent> {
        var launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    Log.d("myTag", "StartActivityForResult()")
                }
            }
        return launcher
    }

    //負責處理權限申請
    fun requestPermissionsLauncher_set(): ActivityResultLauncher<Array<String>> {
        // 創建並註冊一個 ActivityResultLauncher，用來處理權限請求的結果
        var launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // 當權限請求的結果返回時，這個 lambda 表達式會被調用
            when (permissions[Manifest.permission.CAMERA]) {
                true -> {activityCameral()}
                false -> {
                    Log.d("myTag", "相機權限請求失敗")}
                else -> {}
            }
            when (permissions[Manifest.permission.READ_CALENDAR]) {
                true -> {
                    Log.d("myTag", "日曆權限請求成功")}
                false -> {
                    Log.d("myTag", "日曆權限請求失敗")}
                else -> {}
            }
        }
        // 返回這個註冊的 ActivityResultLauncher
        return launcher
    }
}