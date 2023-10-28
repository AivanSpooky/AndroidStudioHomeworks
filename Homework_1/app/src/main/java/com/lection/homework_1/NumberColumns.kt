package com.lection.homework_1

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.lection.homework_1.ui.theme.Homework_1Theme

@Composable
fun NumberListShow(numbers: SnapshotStateList<Int>, columnCount: Int) {
    Column(Modifier.fillMaxSize()) {
        numbers.chunked(columnCount).forEach { rowNumbers ->
            Row(Modifier.fillMaxWidth()) {
                rowNumbers.forEach { number ->
                    val backgroundColor = if (number % 2 == 0) Color.Red else Color.Blue
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                            .background(backgroundColor)
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = number.toString(),
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddNumberButton(numbers: SnapshotStateList<Int>) {
    Column {
        Button(
            onClick = {
                numbers.add(numbers.size + 1)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(text = "Add Number")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    Homework_1Theme {
//        val numbers = remember { mutableStateListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) }
//        val columnCount = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 4
//
//        Surface(modifier = Modifier.fillMaxSize()) {
//            Column(Modifier.fillMaxSize()) {
//                NumberListShow(numbers = numbers, columnCount = columnCount)
//                AddNumberButton(numbers = numbers)
//            }
//        }
//    }
//}