package com.lection.homework_2

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.StateObject
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.lection.homework_2.ui.theme.Homework_2Theme
import coil.compose.rememberImagePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext




class MainActivity : ComponentActivity() {
    private val viewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Homework_2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ImageListScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun ImageListScreen(viewModel: MyViewModel) {
    // Загрузка данных из API
//    var imageList by remember { mutableStateOf<List<BeerData>?>(null) }
//    var isLoading by remember { mutableStateOf(false) }
//    var isError by remember { mutableStateOf(false) }
//    var hasLoaded by remember { mutableStateOf(false) }
    var imageList: List<BeerData>? by rememberSaveable { mutableStateOf(viewModel.imageList.value) }
    var isLoading: Boolean by rememberSaveable { mutableStateOf(viewModel.isLoading.value) }
    var isError: Boolean by rememberSaveable { mutableStateOf(viewModel.isError.value) }
    var hasLoaded: Boolean by rememberSaveable { mutableStateOf(viewModel.hasLoaded.value) }

    var screenValue: ScreenState by rememberSaveable { mutableStateOf(viewModel.screenValue.value) }
    var chosenId: Int by rememberSaveable { mutableStateOf(viewModel.chosenId.value) }

    LaunchedEffect(key1 = hasLoaded) {
        if (!hasLoaded && !isLoading && !isError) {
            isLoading = true
            try {
                println("READING BERRS XDD")
                val result = withContext(Dispatchers.IO) {
                    loadDataFromApi()
                }
                imageList = result
            } catch (e: PunkApiException) {
                isError = true
                isLoading = false
                imageList = null
            } finally {
                isLoading = false
                hasLoaded = true
            }
        }
    }

    // Отображение кнопки для повторной загрузки
    if (isError) {
        IconButton(onClick = {
//            viewModel.resetState()
            isError = false
            hasLoaded = false
        }) {
            Icon(Icons.Default.Refresh, contentDescription = "Retry")
        }
    }
    if (screenValue == State.LIST) {
        // Отображение экрана с изображениями
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (imageList == null) {
                CircularProgressIndicator()
            } else {
                ImageList(imageList = imageList!!, chosenId = chosenId, screenValue = screenValue, OnClick = { newValue -> screenValue = newValue }, cId = { newValue -> chosenId = newValue })
            }
        }
    }
    else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            ImageList(imageList = imageList!!, chosenId = chosenId, screenValue = screenValue, OnClick = { newValue -> screenValue = newValue }, cId = { newValue -> chosenId = newValue })
        }
    }
}

@Composable
fun ImageList(imageList: List<BeerData>, chosenId: Int, screenValue: State, OnClick: (State) -> Unit, cId: (Int) -> Unit) {
    LazyColumn {
        items(imageList) { beerData ->
            ImageItem(beerData = beerData, chosenId = chosenId, screenValue = screenValue, OnClick = OnClick, cId = cId)
        }
    }
}

@Composable
fun ImageItem(beerData: BeerData, chosenId: Int, screenValue: State, OnClick: (State) -> Unit, cId: (Int) -> Unit) {
    val painter = rememberImagePainter(beerData.image_url)
    if (screenValue == State.LIST || (screenValue != State.LIST && beerData.id == chosenId)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(8.dp)
                .border(1.dp, Color.Black, CircleShape)
//                .clickable { OnClick(beerData.id) }
                .clickable {
                    OnClick(State.SINGLE)
                    cId(beerData.id)
                }
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
            if (screenValue != State.LIST) {
                IconButton(
                    onClick = {
                        OnClick(State.LIST)
                        cId(0) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        }
    }
}

//@Composable
//fun BeerInfoScreen() {
//    val painter = rememberImagePainter(beerData.image_url)
////    BackHandler {
////
////    }
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(1f)
//            .padding(8.dp)
//            .border(1.dp, Color.Black, CircleShape)
//    ) {
//        Image(
//            painter = painter,
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Fit
//        )
////        IconButton(onClick = { /*TODO*/ }) {
////            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
////        }
//    }
//}