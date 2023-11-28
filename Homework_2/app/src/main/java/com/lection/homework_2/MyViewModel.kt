package com.lection.homework_2

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class State {
    SINGLE, LIST
}
typealias ScreenState = com.lection.homework_2.State

class MyViewModel : ViewModel() {
    private val _imageList = mutableStateOf<List<BeerData>?>(null)
    val imageList: State<List<BeerData>?> = _imageList

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> = _isError

    private val _hasLoaded = mutableStateOf(false)
    val hasLoaded: State<Boolean> = _hasLoaded

    private val _screenValue = mutableStateOf<ScreenState>(ScreenState.LIST)
    var screenValue: State<ScreenState> = _screenValue

    private val _chosenId = mutableStateOf<Int>(0)
    var chosenId: State<Int> = _chosenId

//    fun loadImageList() {
//        if (!hasLoaded.value && !isLoading.value && !isError.value) {
//            _isLoading.value = true
//            viewModelScope.launch {
//                try {
//                    val result = withContext(Dispatchers.IO) {
//                        loadDataFromApi()
//                    }
//                    _imageList.value = result
//                } catch (e: PunkApiException) {
//                    _isError.value = true
//                    _imageList.value = null
//                } finally {
//                    _isLoading.value = false
//                    _hasLoaded.value = true
//                }
//            }
//        }
//    }

    fun resetState() {
        _imageList.value = null
        _isLoading.value = false
        _isError.value = false
        _hasLoaded.value = false
    }
}