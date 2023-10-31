package com.lection.homework_1

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    val myList = mutableStateListOf<Item>()
    var recompose = mutableStateOf<Int>(0)
}

class Item(var text: String, color: Color ) {
    var color = mutableStateOf(color)
}