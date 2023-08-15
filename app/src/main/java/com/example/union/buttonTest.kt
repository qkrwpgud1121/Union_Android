package com.example.union

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class buttonTest : AppCompatActivity() {

    private var calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_test)

        val btn_cal = findViewById<Button>(R.id.btn_cal)
        var tv_date = findViewById<TextView>(R.id.tv_date)

        currentDate(tv_date)

        btn_cal.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                tv_date.text = year.toString() + "-" + month.toString() + "-" + day.toString()
            }, year, month, day)
            datePickerDialog.show()
        }
    }

    fun currentDate(date: TextView) {
        val datetime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        date.text = datetime
    }
}