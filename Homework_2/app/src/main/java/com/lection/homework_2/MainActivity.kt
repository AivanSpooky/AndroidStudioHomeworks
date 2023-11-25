package com.lection.homework_2

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.inappmessaging.model.ImageData
import com.lection.homework_2.ui.theme.Homework_2Theme
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import coil.compose.rememberImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    private val viewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var recompose = viewModel.recompose
        setContent {
            Homework_2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ImageListScreen(recompose)
                }
            }
        }
    }
}

@Composable
fun ImageListScreen(recompose: MutableState<Int>) {
    // Загрузка данных из API
    var imageList by remember { mutableStateOf<List<BeerData>?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var hasLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasLoaded && !isLoading && !isError) {
            isLoading = true
            try {
                val result = withContext(Dispatchers.IO) {
                    loadDataFromApi()
                }
                imageList = result
                recompose.value += 1
            } catch (e: PunkApiException) {
                isError = true
                isLoading = false
                imageList = null
                recompose.value += 1
            } finally {
                isLoading = false
                hasLoaded = true
            }
        }
    }

    // Отображение кнопки для повторной загрузки
    if (isError) {
        IconButton(onClick = {
            isError = false
            hasLoaded = false
            recompose.value += 1
        }) {
            Icon(Icons.Default.Refresh, contentDescription = "Retry")
        }
    } else {
        // Отображение экрана с изображениями
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (imageList == null) {
                CircularProgressIndicator()
            } else {
                ImageList(imageList = imageList!!)
            }
        }
    }
}

@Composable
fun ImageList(imageList: List<BeerData>) {
    LazyColumn {
        items(imageList) { beerData ->
            ImageItem(beerData = beerData)
        }
    }
}

@Composable
fun ImageItem(beerData: BeerData) {
    val painter = rememberImagePainter(beerData.image_url)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp)
            .border(1.dp, Color.Black, CircleShape)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}