package com.lection.homework_1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NumberListShow(numbers: List<Int>, columnCount: Int) {
    Column( horizontalAlignment = Alignment.CenterHorizontally) {
        numbers.chunked(columnCount).forEach { rowNumbers ->
            Row(modifier = Modifier
                .align(Alignment.Start)
            ) {
                rowNumbers.forEach { number ->
                    val backgroundColor = if (number % 2 == 0) Color.Red else Color.Blue
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                            .background(backgroundColor)
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

@Preview
@Composable
fun NumberListShowPreview() {
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val columnCount = 3

    NumberListShow(numbers, columnCount)
}


@Composable
fun AddNumberButton(numbers: SnapshotStateList<Int>, button_name: String) {
    Column {
        Button(
            onClick = {
                numbers.add(numbers.size + 1)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(text = button_name)
        }
    }
}