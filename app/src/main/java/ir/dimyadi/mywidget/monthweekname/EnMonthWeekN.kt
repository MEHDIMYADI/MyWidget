package ir.dimyadi.mywidget.monthweekname

import ir.dimyadi.mywidget.calendar.CivilDate
import java.util.*

/**
 * @author MEHDIMYADI
 **/

class EnMonthWeekN {

    private var gMonthName = ""
    private var gWeekName = ""

    private fun monthName() {
        val gDate =
            CivilDate(Calendar.getInstance())
        when (gDate.month) {
            1 -> {
                gMonthName = "January"
            }
            2 -> {
                gMonthName = "February"
            }
            3 -> {
                gMonthName = "March"
            }
            4 -> {
                gMonthName = "April"
            }
            5 -> {
                gMonthName = "May"
            }
            6 -> {
                gMonthName = "June"
            }
            7 -> {
                gMonthName = "July"
            }
            8 -> {
                gMonthName = "August"
            }
            9 -> {
                gMonthName = "September"
            }
            10 -> {
                gMonthName = "October"
            }
            11 -> {
                gMonthName = "November"
            }
            12 -> {
                gMonthName = "December"
            }
        }
    }

    private fun weekName(calendar: Calendar) {
        when (calendar[Calendar.DAY_OF_WEEK]) {
            1 -> gWeekName = "Sunday"
            2 -> gWeekName = "Monday"
            3 -> gWeekName = "Tuesday"
            4 -> gWeekName = "Wednesday"
            5 -> gWeekName = "Thursday"
            6 -> gWeekName = "Friday"
            7 -> gWeekName = "Saturday"
        }
    }
    val monthName: String
        get() = gMonthName

    val weekName: String
        get() = gWeekName

    init {
        val calendar: Calendar = GregorianCalendar()
        monthName()
        weekName(calendar)
    }
}