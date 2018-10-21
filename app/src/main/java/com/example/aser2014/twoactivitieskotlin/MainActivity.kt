package com.example.aser2014.twoactivitieskotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle

class MainActivity : AppCompatActivity() {

    private var timer: CountDownTimer? = null
    private val interval: Long = 1000
    private val allTime: Long = 2000
    private var currentState = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createTimer()
    }

    override fun onStop() {
        super.onStop()
        if (timer != null) {
            timer!!.cancel()
        }
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

    private fun createTimer() {
        timer = object : CountDownTimer(allTime, interval) {
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
        (timer as CountDownTimer).start()
    }
}
