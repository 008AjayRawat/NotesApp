package com.app.notesapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateUtils {

    @SuppressLint("SimpleDateFormat")
    fun convertMillsToDateFormat(mills: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateString = simpleDateFormat.format(mills)
        return String.format("%s", dateString)
    }
}