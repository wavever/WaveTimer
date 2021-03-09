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
package com.example.androiddevchallenge.ui.timer

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel : ViewModel() {

    private var mCounter: CountDownTimer? = null
    private val _hour = MutableStateFlow(0)
    val hour: StateFlow<Int> = _hour

    private val _minute = MutableStateFlow(0)
    val minute: StateFlow<Int> = _minute

    private val _second = MutableStateFlow(0)
    val second: StateFlow<Int> = _second

    private val _isStart = MutableStateFlow(false)
    val isStart: StateFlow<Boolean> = _isStart

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private var initHour = 0
    private var initMinute = 0
    private var initSecond = 0
    var duration: Long = 0
    var currentProgress: Long = 0

    fun onHourChanged(h: Int) {
        initHour = h
    }

    fun onMinuteChanged(m: Int) {
        initMinute = m
    }

    fun onSecondChanged(s: Int) {
        initSecond = s
    }

    private fun start() {
        cancelCounter()
        Log.d(
            "wavever_tag",
            "start(), initHour=$initHour, initMinute=$initMinute, initSecond=$initSecond"
        )
        if (initHour <= 0 && initMinute <= 0 && initSecond <= 0) {
            return
        }
        _isPlaying.value = true
        _isStart.value = true
        startCounter(initHour, initMinute, initSecond)
    }

    private fun startCounter(h: Int, m: Int, s: Int) {
        duration = h.hour2Millis() + m.minute2Millis() + s.second2Millis()
        Log.d("wavever_tag", "start, duration=$duration")
        mCounter = object : CountDownTimer(duration, 1_000L) {
            override fun onTick(millis: Long) {
                _hour.value = (millis / 1.hour2Millis()).toInt()
                _minute.value = ((millis - _hour.value.hour2Millis()) / 1.minute2Millis()).toInt()
                _second.value = ((millis - _hour.value.hour2Millis() - _minute.value.minute2Millis()) / 1.second2Millis()).toInt()
                currentProgress = hour.value.hour2Millis() + minute.value.minute2Millis() + second.value.second2Millis()
            }

            override fun onFinish() {
                mCounter = null
                initHour = 0
                initMinute = 0
                initSecond = 0
                _hour.value = 0
                _minute.value = 0
                _second.value = 0
            }
        }
        mCounter?.start()
    }

    private fun cancelCounter() {
        if (mCounter == null) return
        mCounter?.cancel()
        mCounter = null
    }

    fun end() {
        Log.d("wavever_tag", "end()")
        _isStart.value = false
        _isPlaying.value = false
        cancelCounter()
        initHour = 0
        initMinute = 0
        initSecond = 0
        _hour.value = -1
        _minute.value = -1
        _second.value = -1
        duration = 0
    }

    fun play() {
        if (!isStart.value) {
            start()
            return
        }
        if (isPlaying.value) {
            _isPlaying.value = false
            mCounter?.cancel()
            mCounter = null
        } else {
            _isPlaying.value = true
            startCounter(hour.value, minute.value, second.value)
        }
    }

    fun replay() {
        cancelCounter()
        if (initHour <= 0 && initMinute <= 0 && initSecond <= 0) return
        _hour.value = initHour
        _minute.value = initMinute
        _second.value = initSecond
    }

    fun Int.hour2Millis(): Long = (this * 60 * 60).second2Millis()
    fun Int.minute2Millis(): Long = (this * 60).second2Millis()
    fun Int.second2Millis(): Long = this * 1_000L
}
