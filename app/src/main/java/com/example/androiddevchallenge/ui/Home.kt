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
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.timer.TimerViewModel
import com.example.androiddevchallenge.ui.widget.Controller
import com.example.androiddevchallenge.ui.widget.Timer
import com.example.androiddevchallenge.ui.widget.WaveView

@Composable
fun Home() {
    val viewModel = viewModel(TimerViewModel::class.java)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        WaveView(viewModel)
        Column {
            Timer(viewModel)
            Spacer(modifier = Modifier.height(150.dp))
            Controller(viewModel)
        }
    }
}
