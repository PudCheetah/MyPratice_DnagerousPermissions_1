package com.example.mypratice_dnagerouspermissions_1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mypratice_dnagerouspermissions_1.databinding.ActivityMainBinding
//
//
//傳統的作法
//
//
//
class MainActivity : AppCompatActivity() {
    private var myRequestCode_cameral = 100
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.btn2.setOnClickListener {
            checkAndlauchCameral()
        }
        setContentView(binding.root)
    }

    // 用來處理權限請求結果的回呼方法。
    // 當使用 ActivityCompat.requestPermissions 請求權限時，系統會顯示一個對話框，讓用戶授予或拒絕權限。
    // 用戶做出選擇後，系統會調用這個方法，並將結果傳遞回應用
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == myRequestCode_cameral) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                activityCameral()
            }
        }
    }

    //設計跳轉到相機的Intent
    fun activityCameral() {
        var myIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(myIntent)
    }

    //檢查應用是否已經獲得相機使用權限，如果已經獲得則啟動相機，否則請求相機使用權限
    fun checkAndlauchCameral() {
        var myPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (myPermission == PackageManager.PERMISSION_GRANTED){
            activityCameral()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                myRequestCode_cameral
            )
        }
    }
}