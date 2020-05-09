package ir.dimyadi.mywidget.monthweekname

import io.github.persiancalendar.calendar.CivilDate
import io.github.persiancalendar.calendar.PersianDate
import ir.dimyadi.mywidget.util.toCivilDate
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
        shMonthName = monthNamesList[PersianDate(Calendar.getInstance().toCivilDate()).month]
        shWeekName = weekDaysList[GregorianCalendar()[Calendar.DAY_OF_WEEK]]
    }
}