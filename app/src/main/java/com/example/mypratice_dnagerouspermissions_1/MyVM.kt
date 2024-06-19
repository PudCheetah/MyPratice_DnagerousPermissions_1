package com.example.mypratice_dnagerouspermissions_1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyVM: ViewModel() {
    var spinnerSelect = MutableLiveData<Int>()

    init {
        spinnerSelect.value = 0
    }
}