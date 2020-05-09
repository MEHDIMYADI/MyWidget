package ir.dimyadi.mywidget.monthweekname

import ir.dimyadi.mywidget.calendar.CivilDate
import ir.dimyadi.mywidget.calendar.IslamicDate
import java.util.*

/**
 * @author MEHDIMYADI
 **/

class IslamicMonthWeekN {

    private var hMonthName = ""
    private var hWeekName = ""

    private fun monthName() {
        val islamicDate = IslamicDate(
            CivilDate(Calendar.getInstance())
        )
        when (islamicDate.month) {
            1 -> {
                hMonthName = "مُحَرَّم"
            }
            2 -> {
                hMonthName = "صَفَر"
            }
            3 -> {
                hMonthName = "ربيع الأول"
            }
            4 -> {
                hMonthName = "ربیع الثاني"
            }
            5 -> {
                hMonthName = "جمادى الأولى"
            }
            6 -> {
                hMonthName = "جمادی الثانية"
            }
            7 -> {
                hMonthName = "رجب"
            }
            8 -> {
                hMonthName = "شعبان"
            }
            9 -> {
                hMonthName = "رمضان"
            }
            10 -> {
                hMonthName = "شوال"
            }
            11 -> {
                hMonthName = "ذو القعده"
            }
            12 -> {
                hMonthName = "ذو الحجهه"
            }
        }
    }

    private fun weekName(calendar: Calendar) {
        when (calendar[Calendar.DAY_OF_WEEK]) {
            1 -> hWeekName = "الأحد"
            2 -> hWeekName = "الاثنين"
            3 -> hWeekName = "الثلاثاء"
            4 -> hWeekName = "الأربعاء"
            5 -> hWeekName = "الخميس"
            6 -> hWeekName = "جمعه"
            7 -> hWeekName = "السبت"
        }
    }
    val monthName: String
        get() = hMonthName

    val weekName: String
        get() = hWeekName

    init {
        val calendar: Calendar = GregorianCalendar()
        monthName()
        weekName(calendar)
    }
}