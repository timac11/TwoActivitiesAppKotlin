package com.example.aser2014.twoactivitieskotlin.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.aser2014.twoactivitieskotlin.R
import java.lang.StringBuilder

class TimerActivity : AppCompatActivity() {

    private var timer: CountDownTimer
    private val interval: Long = 1000
    private val allTime: Long = 1001000
    private var currentTime = 1
    private var button: Button? = null
    private var textView: TextView? = null
    private val START = "Start"
    private val STOP = "Stop"
    private var currentUnit = 0
    private var currentDozen = 0
    private var currentHundread = 0
    private var currentUnitString = ""
    private var currentDozenString = ""
    private var currentHundreadString = ""
    private var units: Array<String>? = null
    private var dozens: Array<String>? = null
    private var hundrets: Array<String>? = null

    init {
        timer = createTimer(1001L)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        button = findViewById<Button>(R.id.button)
        textView = findViewById<TextView>(R.id.textView)
        units = resources.getStringArray(R.array.currentUnit)
        dozens = resources.getStringArray(R.array.currentDozen)
        hundrets = resources.getStringArray(R.array.currentHundred)
        val oclButtonListener = createBtnListener()
        button!!.setOnClickListener(oclButtonListener)
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    override fun onResume() {
        super.onResume()
        timer = createTimer(allTime - (currentTime - 1) * 1000)
        button?.let {
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
                textView?.let { it.text = getCurrentNumber(currentTime) }
            }
            override fun onFinish() {}
        }
    }

    private fun createBtnListener(): View.OnClickListener {
        return View.OnClickListener {
            button?.let {
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
        currentUnit = millsAfterStart % 100
        if (millsAfterStart <= 19) {
            return units!![currentUnit]
        }
        if (millsAfterStart == 1000) {
            finishTimer()
            return getResources().getString(R.string.lastNum)
        }
        currentUnit = millsAfterStart % 100 % 10
        currentDozen = millsAfterStart / 10 % 10
        currentHundread = millsAfterStart / 100
        currentUnitString = units!![currentUnit]
        currentDozenString = dozens!![currentDozen]


        val currentTime = StringBuilder()

        when {
            currentHundread != 0 -> {
                currentHundreadString = hundrets!![currentHundread]
                currentTime.append(currentHundreadString).append(" ")
                if (currentDozen > 1) {
                    currentDozenString = dozens!![currentDozen]
                    currentTime.append(currentDozenString).append(" ")
                }
                if (millsAfterStart % 100 <= 19) {
                    currentUnit = millsAfterStart % 100
                }
                if (currentUnit != 0) {
                    currentUnitString = units!![currentUnit]
                    currentTime.append(currentUnitString)
                }
                return currentTime.toString()
            }
            currentDozen != 0 -> {
                currentDozenString = dozens!![currentDozen]
                currentTime.append(currentDozenString).append(" ")
                if (currentUnit != 0) {
                    currentUnitString = units!![currentUnit]
                    currentTime.append(currentUnitString)
                }
                return currentTime.toString()
            }
            else -> {
                currentUnitString = units!![currentUnit]
                currentTime.append(currentUnitString)
                return currentTime.toString()
            }
        }
    }

    protected fun finishTimer() {
        timer.let {
            it.cancel()
            it.onFinish()
        }
        currentTime = 1
        button!!.text = START
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(this.getString(R.string.currentTime), currentTime)
        outState.putString(this.getString(R.string.buttonState), button!!.text as String)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentTime = savedInstanceState.getInt(this.getString(R.string.currentTime))
        textView!!.text = getCurrentNumber(currentTime)
        button!!.text = savedInstanceState.getString(this.getString(R.string.buttonState))
    }
}