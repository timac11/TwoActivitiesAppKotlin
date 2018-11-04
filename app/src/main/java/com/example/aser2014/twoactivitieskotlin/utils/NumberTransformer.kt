package com.example.aser2014.twoactivitieskotlin.utils

import android.content.res.Resources
import com.example.aser2014.twoactivitieskotlin.R
import java.lang.StringBuilder

fun getCurrentTimeInStringFormat(resources: Resources, millsAfterStart: Int): String {

    val units = resources.getStringArray(R.array.currentUnit)
    val dozens = resources.getStringArray(R.array.currentDozen)
    val hundrets = resources.getStringArray(R.array.currentHundred)

    var currentUnit = millsAfterStart % 100

    if (millsAfterStart <= 19) {
        return units[currentUnit]
    }

    if (millsAfterStart == 1000) {
        return Resources.getSystem().getString(R.string.lastNum)
    }
    currentUnit = millsAfterStart % 100 % 10

    val currentDozen = millsAfterStart / 10 % 10
    val currentHundread = millsAfterStart / 100
    val currentUnitString: String
    val currentDozenString: String

    val currentTime = StringBuilder()

    when {
        currentHundread != 0 -> {
            val currentHundreadString = hundrets[currentHundread]
            currentTime.append(currentHundreadString).append(" ")
            if (currentDozen > 1) {
                currentDozenString = dozens[currentDozen]
                currentTime.append(currentDozenString).append(" ")
            }
            if (millsAfterStart % 100 <= 19) {
                currentUnit = millsAfterStart % 100
            }
            if (currentUnit != 0) {
                currentUnitString = units[currentUnit]
                currentTime.append(currentUnitString)
            }
        }
        currentDozen != 0 -> {
            currentDozenString = dozens[currentDozen]
            currentTime.append(currentDozenString).append(" ")
            if (currentUnit != 0) {
                currentUnitString = units[currentUnit]
                currentTime.append(currentUnitString)
            }
        }
        else -> {
            currentUnitString = units[currentUnit]
            currentTime.append(currentUnitString)
        }
    }
    return currentTime.toString()
}