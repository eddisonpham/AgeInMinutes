package com.example.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var selectDate :Button
    private lateinit var selectedDate :TextView
    private lateinit var minsTillDate :TextView
    private lateinit var timeSelect: Button

    private var inMinOrHour :Int = 60000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectDate = findViewById(R.id.BTNselectDate)
        selectedDate = findViewById(R.id.TVselectedDate)
        minsTillDate = findViewById(R.id.TVminsTillDate)
        timeSelect = findViewById(R.id.BTNtime)

        selectDate.setOnClickListener{ view->
            clickDatePicker(view)
        }
        timeSelect.setOnClickListener{
            var textLabel = timeSelect.text
            when(textLabel){
                "MINUTES"->{
                    timeSelect.setText("HOURS")
                    inMinOrHour=3600000
                }
                else ->{
                    timeSelect.setText("MINUTES")
                    inMinOrHour=60000
                }
            }
        }
    }

    private fun clickDatePicker(view: View){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{
                    view,selectedYear,selectedMonth,selectedDayOfMonth ->
                val selectedDate_ = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                selectedDate.setText(selectedDate_)
                val sdf = SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate_)
                theDate?.let{
                    val selectedDateInMinutes = theDate.time/inMinOrHour
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let{
                        val currentDateInMinutes = currentDate.time/inMinOrHour
                        val differenceInMinutes = currentDateInMinutes-selectedDateInMinutes
                        minsTillDate.text = differenceInMinutes.toString()
                    }
                }

            }
            ,year
            ,month
            ,day
        )
        dpd.datePicker.maxDate=System.currentTimeMillis()-86400000
        dpd.show()
    }
}