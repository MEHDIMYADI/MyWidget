package ir.dimyadi.mywidget.monthweekname

import io.github.persiancalendar.calendar.IslamicDate
import ir.dimyadi.mywidget.util.toCivilDate
import java.util.*

/**
 * @author MEHDIMYADI
 **/

class IslamicMonthWeekN {

    private var hMonthName = ""
    private var hWeekName = ""

    private val monthNamesList = listOf(
        "", "مُحَرَّم", "صَفَر", "ربيع الأول", "ربیع الثاني", "جمادى الأولى", "جمادی الثانية",
        "رجب", "شعبان", "رمضان", "شوال", "ذو القعده", "ذو الحجهه"
    )

    private val weekDaysList = listOf(
        "", "الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "جمعه", "السبت"
    )

    val monthName: String
        get() = hMonthName

    val weekName: String
        get() = hWeekName

    init {
        hMonthName = monthNamesList[IslamicDate(Calendar.getInstance().toCivilDate()).month]
        hWeekName = weekDaysList[GregorianCalendar()[Calendar.DAY_OF_WEEK]]
    }
}