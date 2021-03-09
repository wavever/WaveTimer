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

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.example.androiddevchallenge.ui.timer.TimerViewModel
import kotlin.math.sin

const val A = 50
const val W = 2 * Math.PI / 1080

/**
 * https://github.com/lygttpod/AndroidCustomView
 * y=A*sin(wx+b)+k
 * <p>
 * A—振幅越大，波形在y轴上最大与最小值的差值越大
 * w—角速度， 控制正弦周期(单位角度内震动的次数)
 * b—初相，反映在坐标系上则为图像的左右移动。这里通过不断改变b,达到波浪移动效果
 * k—偏距，反映在坐标系上则为图像的上移或下移。
 */
@Composable
fun WaveView(viewModel: TimerViewModel) {
    val isStart by viewModel.isStart.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val path = remember { Path() }.apply { reset() }
    val goDuration = viewModel.duration
    if (!isStart) {
        WaveGone(path)
        return
    }
    if (!isStart) {
        WaveGone(path)
    }
    if (isPlaying) {
        WaveGo(path, goDuration, isPlaying)
    }
}

@Composable
fun WaveGo(path: Path, duration: Long, isPlaying: Boolean) {
    val K = remember {
        Animatable(0f)
    }
    var b = remember { 0f }
    LaunchedEffect(isPlaying) {
        K.animateTo(1f, animationSpec = tween(durationMillis = duration.toInt(), easing = LinearEasing))
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        // Log.d("wavever_tag", "k=${K.value}, canvasWidth=$canvasWidth, canvasHeight=$canvasHeight")
        path.reset()
        path.moveTo(0f, canvasHeight)
        val k = (1 - K.value) * canvasHeight
        b -= 0.1f
        var y: Float
        for (x in 0 until canvasWidth.toInt() step 20) {
            y = (A * sin((W * x + b)) + k).toFloat()
            path.lineTo(x.toFloat(), y)
        }
        path.lineTo(canvasHeight, canvasHeight)
        path.lineTo(0f, canvasHeight)
        path.close()
        drawPath(path, color = Color.Yellow)
    }
}

@Composable
fun WaveGone(path: Path) {
// TODO: 2021/3/10
}
