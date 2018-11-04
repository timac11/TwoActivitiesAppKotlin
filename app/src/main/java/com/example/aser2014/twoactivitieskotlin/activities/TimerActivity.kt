package com.example.aser2014.twoactivitieskotlin.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.aser2014.twoactivitieskotlin.R
import com.example.aser2014.twoactivitieskotlin.utils.getCurrentTimeInStringFormat

class TimerActivity : AppCompatActivity() {

    private var timer: CountDownTimer
    private val interval: Long = 1000
    private val allTime: Long = 1001000
    private var currentTime = 1
    private lateinit var button: Button
    private lateinit var textView: TextView
    private val START = "Start"
    private val STOP = "Stop"
    private lateinit var units: Array<String>
    private lateinit var dozens: Array<String>
    private lateinit var hundrets: Array<String>

    init {
        timer = createTimer(1001L)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        button = findViewById(R.id.button)
        textView = findViewById(R.id.textView)
        /*units = resources.getStringArray(R.array.currentUnit)
        dozens = resources.getStringArray(R.array.currentDozen)
        hundrets = resources.getStringArray(R.array.currentHundred)*/
        val oclButtonListener = createBtnListener()
        button.setOnClickListener(oclButtonListener)
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    override fun onResume() {
        super.onResume()
        timer = createTimer(allTime - (currentTime - 1) * 1000)
        button.let {
            if (it.text == STOP) {
                timer.start()
            } else {
                timer.cancel()
            }
        }
    }

    private fun createTimer(allTime: Long): CountDownTimer {
        return object : CountDownTimer(allTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                currentTime += 1
                textView.text = getCurrentNumber(currentTime)
            }
            override fun onFinish() {}
        }
    }

    private fun createBtnListener(): View.OnClickListener {
        return View.OnClickListener {
            button.let {
                if (it.text == START) {
                    timer.start()
                    it.text = STOP
                } else {
                    timer.cancel()
                    it.text = START
                }
            }
        }
    }

    private fun getCurrentNumber(millsAfterStart: Int): String {
        if (millsAfterStart == 1000) {
            finishTimer()
        }

        return getCurrentTimeInStringFormat(resources, millsAfterStart)
    }

    private fun finishTimer() {
        timer.let {
            it.cancel()
            it.onFinish()
        }
        currentTime = 1
        button.text = START
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(this.getString(R.string.currentTime), currentTime)
        outState.putString(this.getString(R.string.buttonState), button.text as String? ?: START)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentTime = savedInstanceState.getInt(this.getString(R.string.currentTime))
        textView.text = getCurrentNumber(currentTime)
        button.text = savedInstanceState.getString(this.getString(R.string.buttonState))
    }
}