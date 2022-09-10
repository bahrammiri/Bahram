package com.bahram.weather7.util

import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun timeStampToLocalHour(timeStamp: Long): String {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = timeStamp * 1000
            val hours = cal[Calendar.HOUR].toString()
            val minutes = cal[Calendar.MINUTE].toString()
            return "$hours:$minutes"
        }

        fun timeStampToLocalDay(timeStamp: Long): String {
            return SimpleDateFormat("EEE", Locale.ENGLISH).format(timeStamp * 1000)
        }
    }
}

