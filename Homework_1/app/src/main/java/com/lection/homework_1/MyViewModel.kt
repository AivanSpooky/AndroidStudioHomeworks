package com.lection.homework_1

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf

class MyViewModel: ViewModel() {
    val myList = mutableStateListOf<Int>()
}