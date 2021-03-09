/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.widget

import android.util.Log
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.androiddevchallenge.ui.timer.TimerViewModel

// stateful composable
@Composable
fun Timer(viewModel: TimerViewModel) {
    val hour: Int by viewModel.hour.collectAsState()
    val minute: Int by viewModel.minute.collectAsState()
    val second: Int by viewModel.second.collectAsState()
    val isStart by viewModel.isStart.collectAsState()
    Timer(
        isStart = isStart,
        hour = hour,
        minute = minute,
        second = second,
        onHourChanged = { viewModel.onHourChanged(it) },
        onMinuteChanged = { viewModel.onMinuteChanged(it) },
        onSecondChanged = { viewModel.onSecondChanged(it) }
    )
}

// stateless composable
@Composable
fun Timer(
    isStart: Boolean,
    hour: Int,
    minute: Int,
    second: Int,
    onHourChanged: (Int) -> Unit,
    onMinuteChanged: (Int) -> Unit,
    onSecondChanged: (Int) -> Unit,
) {
    Log.d("wavever_tag", "Timer(), hour=$hour, minute=$minute, second=$second, isStart=$isStart")
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Time(hour, !isStart, max = 99) { onHourChanged(it) }
        Split()
        Time(minute, !isStart) { onMinuteChanged(it) }
        Split()
        Time(second, !isStart) { onSecondChanged(it) }
    }
}

@Composable
private fun Time(
    time: Int,
    enableScroll: Boolean,
    max: Int = 59,
    onTimeChanged: (Int) -> Unit,
) {
    var offset by remember { mutableStateOf(0f) }
    Text(
        text = if (enableScroll) {
            if (time == -1 && offset >= 0) offset.toTime() else time.toTime()
        } else time.toTime(),
        Modifier.scrollable(
            enabled = enableScroll,
            orientation = Orientation.Vertical,
            // Scrollable state: describes how to consume
            // scrolling delta and update offset
            state = rememberScrollableState { delta ->
                when {
                    offset + delta >= max -> {
                        offset = max.toFloat()
                    }
                    offset + delta <= 0 -> {
                        offset = 0f
                    }
                    else -> {
                        offset += delta
                    }
                }
                onTimeChanged(offset.toInt())
                delta
            }
        ),
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.h2
    )
}

@Composable
private fun Split() {
    Text(
        text = " : ", color = Color.Black,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h4
    )
}

private fun Float.toTime(): String {
    var num = this
    if (this <= 0) num = 0f
    return if (num < 10) "0${num.toInt()}" else "${num.toInt()}"
}

private fun Int.toTime(): String {
    var num = this
    if (this <= 0) num = 0
    return if (num < 10) "0$num" else num.toString()
}
