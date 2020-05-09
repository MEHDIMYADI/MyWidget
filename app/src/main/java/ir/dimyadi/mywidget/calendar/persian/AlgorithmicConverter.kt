package ir.dimyadi.mywidget.calendar.persian

import kotlin.math.ceil

/**
 * @author Amir
 * @author ebraminio
 * @author MEHDIMYADI
 */
object AlgorithmicConverter {
    private fun floor(d: Double): Long {
        return kotlin.math.floor(d).toLong()
    }

    fun toJdn(year: Int, month: Int, day: Int): Long {
        val PERSIAN_EPOCH: Long = 1948321 // The JDN of 1 Farvardin 1
        val epbase: Long = if (year >= 0) year - 474.toLong() else year - 473.toLong()
        val epyear = 474 + epbase % 2820
        val mdays: Long = if (month <= 7) (month - 1) * 31.toLong() else (month - 1) * 30 + 6.toLong()
        return day + mdays + (epyear * 682 - 110) / 2816 + (epyear - 1) * 365 + epbase / 2820 * 1029983 + (PERSIAN_EPOCH - 1)
    }

    fun fromJdn(jdn: Long): IntArray {
        val depoch = jdn - 2121446 // or toJdn(475, 1, 1);
        val cycle = depoch / 1029983
        val cyear = depoch % 1029983
        val ycycle: Long
        val aux1: Long
        val aux2: Long
        if (cyear == 1029982L) ycycle = 2820 else {
            aux1 = cyear / 366
            aux2 = cyear % 366
            ycycle = (floor((2134 * aux1 + 2816 * aux2 + 2815) / 1028522.0)
                    + aux1 + 1)
        }
        var year: Int
        val month: Int
        val day: Int
        year = (ycycle + 2820 * cycle + 474).toInt()
        if (year <= 0) year -= 1
        val yday = jdn - toJdn(year, 1, 1) + 1
        month = if (yday <= 186) ceil(yday / 31.0).toInt() else ceil((yday - 6) / 30.0)
            .toInt()
        day = (jdn - toJdn(year, month, 1)).toInt() + 1
        return intArrayOf(year, month, day)
    }
}