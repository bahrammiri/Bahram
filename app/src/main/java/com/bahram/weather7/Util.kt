package com.bahram.weather7

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId

class Util {
    companion object {


        @RequiresApi(Build.VERSION_CODES.O)
        fun timeStampToLocalHour(timeStamp: Long): String {
            val localTime = timeStamp.let {
                Instant.ofEpochSecond(it)
                    .atZone(ZoneId.systemDefault()).toLocalTime()
            }
            return localTime.toString()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun timeStampToLocalDay(timeStamp: Long): String {
            val localTime = timeStamp.let {
                Instant.ofEpochSecond(it)
                    .atZone(ZoneId.systemDefault()).dayOfWeek
            }
            return localTime.toString().lowercase()
        }


    }

}

