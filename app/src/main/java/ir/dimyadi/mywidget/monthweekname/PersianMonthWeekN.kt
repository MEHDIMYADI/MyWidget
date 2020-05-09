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

    private val monthNamesList = listOf(
        "", "فروردين", "ارديبهشت", "خرداد", "تير", "مرداد", "شهريور", "مهر", "آبان", "آذر",
        "دي", "بهمن", "اسفند"
    )

    private val weekDaysList = listOf(
        "", "يکشنبه", "دوشنبه", "سه‌شنبه", "چهارشنبه", "پنج‌شنبه", "جمعه", "شنبه"
    )

    val monthName: String
        get() = shMonthName

    val weekName: String
        get() = shWeekName

    init {
        shMonthName = monthNamesList[PersianDate(CivilDate(Calendar.getInstance())).month]
        shWeekName = weekDaysList[GregorianCalendar()[Calendar.DAY_OF_WEEK]]
    }
}