package ir.dimyadi.mywidget.monthweekname

import ir.dimyadi.mywidget.calendar.CivilDate
import ir.dimyadi.mywidget.calendar.PersianDate
import java.util.*

/**
 * @author MEHDIMYADI
 **/

class PersianMonthWeekN {

    private var shMonthName = ""
    private var shWeekName = ""

    private fun monthName() {
        val persianDate = PersianDate(
            CivilDate(Calendar.getInstance())
        )
        when (persianDate.month) {
            1 -> {
                shMonthName = "فروردين"
            }
            2 -> {
                shMonthName = "ارديبهشت"
            }
            3 -> {
                shMonthName = "خرداد"
            }
            4 -> {
                shMonthName = "تير"
            }
            5 -> {
                shMonthName = "مرداد"
            }
            6 -> {
                shMonthName = "شهريور"
            }
            7 -> {
                shMonthName = "مهر"
            }
            8 -> {
                shMonthName = "آبان"
            }
            9 -> {
                shMonthName = "آذر"
            }
            10 -> {
                shMonthName = "دي"
            }
            11 -> {
                shMonthName = "بهمن"
            }
            12 -> {
                shMonthName = "اسفند"
            }
        }
    }

    private fun weekName(calendar: Calendar) {
        when (calendar[Calendar.DAY_OF_WEEK]) {
            1 -> shWeekName = "يکشنبه"
            2 -> shWeekName = "دوشنبه"
            3 -> shWeekName = "سه شنبه"
            4 -> shWeekName = "چهارشنبه"
            5 -> shWeekName = "پنج شنبه"
            6 -> shWeekName = "جمعه"
            7 -> shWeekName = "شنبه"
        }
    }
    val monthName: String
        get() = shMonthName

    val weekName: String
        get() = shWeekName

    init {
        val calendar: Calendar = GregorianCalendar()
        monthName()
        weekName(calendar)
    }
}