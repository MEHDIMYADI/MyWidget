package ir.dimyadi.mywidget.monthweekname

import ir.dimyadi.mywidget.calendar.CivilDate
import java.util.*

/**
 * @author MEHDIMYADI
 **/

class EnMonthWeekN {

    private var gMonthName = ""
    private var gWeekName = ""

    private val monthNamesList = listOf(
        "", "January", "February", "March", "April", "May", "June", "July", "August",
        "September", "October", "November", "December"
    )

    private val weekDaysList = listOf(
        "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    )

    val monthName: String
        get() = gMonthName

    val weekName: String
        get() = gWeekName

    init {
        gMonthName = monthNamesList[CivilDate(Calendar.getInstance()).month]
        gWeekName = weekDaysList[GregorianCalendar()[Calendar.DAY_OF_WEEK]]
    }
}