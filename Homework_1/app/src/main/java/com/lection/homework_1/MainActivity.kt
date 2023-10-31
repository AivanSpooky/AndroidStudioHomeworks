package com.lection.homework_1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.lection.homework_1.ui.theme.Homework_1Theme

class MainActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var numbers = viewModel.myList
            Homework_1Theme {
                val columnCount = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 4

                Surface(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(modifier = Modifier
                        .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            NumberListShow(numbers = numbers, columnCount = columnCount)
                        }
                        item{
                            AddNumberButton(numbers = numbers, getString(R.string.button_name))
                        }
                    }
                }
            }
        }
    }
}

