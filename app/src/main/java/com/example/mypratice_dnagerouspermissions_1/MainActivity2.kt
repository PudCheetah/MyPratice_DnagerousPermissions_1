package com.example.mypratice_dnagerouspermissions_1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mypratice_dnagerouspermissions_1.databinding.ActivityMainBinding

//
//
//使用Activity Result API的作法
//請求單一權限
//
//
class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var captureImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionsLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestPermissionsLauncher = requestPermissionsLauncher_set()
        captureImageLauncher = captureImageLauncher_set()
        binding.btn2.setOnClickListener {
            requestPermissionsLauncher.launch(Manifest.permission.CAMERA)
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
    fun requestPermissionsLauncher_set(): ActivityResultLauncher<String> {
        // 創建並註冊一個 ActivityResultLauncher，用來處理權限請求的結果
        var launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                // 當權限請求的結果返回時，這個 lambda 表達式會被調用
            if (isGranted) {
                // 如果權限被授予，則調用 activityCameral() 方法
                activityCameral()
            } else {
                // 如果權限被拒絕，則輸出一條 Log 訊息
                Log.d("myTag", "權限請求失敗")
            }
            }
        // 返回這個註冊的 ActivityResultLauncher
        return launcher
    }
}