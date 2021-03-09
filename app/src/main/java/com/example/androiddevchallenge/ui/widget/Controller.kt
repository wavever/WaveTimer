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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.timer.TimerViewModel

@Composable
fun Controller(viewModel: TimerViewModel) {
    val isStart by viewModel.isStart.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    Log.i("wavever_tag", "Controller(), isPlaying=$isPlaying")
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        // Replay
        Button(
            onClick = { viewModel.replay() },
            shape = CircleShape,
            enabled = !isPlaying,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_replay_24), contentDescription = null)
        }
        // Play & Pause
        Button(
            onClick = {
                viewModel.play()
            },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary)
        ) {
            Icon(
                painter = painterResource(if (isPlaying) R.drawable.ic_pause_24 else R.drawable.ic_play_24),
                contentDescription = null
            )
        }
        // End
        Button(
            onClick = {
                viewModel.end()
            },
            shape = CircleShape, enabled = isStart,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_stop_24), contentDescription = null)
        }
    }
}
