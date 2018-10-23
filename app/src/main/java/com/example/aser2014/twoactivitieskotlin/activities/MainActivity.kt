package com.example.aser2014.twoactivitieskotlin.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import com.example.aser2014.twoactivitieskotlin.R

class MainActivity : AppCompatActivity() {

    private var timer: CountDownTimer
    private val interval: Long = 1000
    private val allTime: Long = 2000
    private var currentState = 0

    init {
        timer = createTimer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timer.start()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putInt(this.getString(R.string.currentTime1), currentState)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            currentState = savedInstanceState.getInt(this.getString(R.string.currentTime1))
        }
    }

    private fun createTimer(): CountDownTimer {
        return object : CountDownTimer(allTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                currentState++
                if (currentState == 2) {
                    val intent = Intent(this@MainActivity, TimerActivity::class.java)
                    this@MainActivity.finish()
                    startActivity(intent)
                }
            }
            override fun onFinish() {}
        }
    }
}
