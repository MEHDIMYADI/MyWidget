package ir.dimyadi.mywidget.calendar.persian

import java.util.*
import kotlin.math.ceil

// Sad that we have to do this but in order to be compatible
// with https://calendar.ut.ac.ir/Fa/News/Data/Doc/KabiseShamsi1206-1498.pdf
// I see no other way
object LookupTableConverter {
    private const val startingYear = 1206
    private val yearsStartingJdn =
        LongArray(1498 - startingYear)

    fun toJdn(year: Int, month: Int, day: Int): Long {
        return if (year < startingYear || year > startingYear + yearsStartingJdn.size - 1) -1 else (yearsStartingJdn[year - startingYear]
                + (if (month <= 7) (month - 1) * 31 else (month - 1) * 30 + 6) // total days of months
                + day) - 1
    }

    fun fromJdn(jdn: Long): IntArray? {
        if (jdn < yearsStartingJdn[0] || jdn > yearsStartingJdn[yearsStartingJdn.size - 1]
        ) return null
        var year = (jdn - yearsStartingJdn[0]).toInt() / 366
        while (year < yearsStartingJdn.size - 1) {
            if (jdn < yearsStartingJdn[year + 1]) break
            ++year
        }
        val startOfYearJdn = yearsStartingJdn[year]
        year += startingYear
        val dayOfYear = jdn - startOfYearJdn + 1
        val month: Int
        month = if (dayOfYear <= 186) ceil(dayOfYear / 31.0)
            .toInt() else ceil((dayOfYear - 6) / 30.0).toInt()
        val day =
            dayOfYear.toInt() - if (month <= 7) (month - 1) * 31 else (month - 1) * 30 + 6
        return intArrayOf(year, month, day)
    }

    init {
        val leapYears = intArrayOf(
            1210, 1214, 1218, 1222, 1226, 1230, 1234, 1238, 1243, 1247, 1251, 1255, 1259, 1263,
            1267, 1271, 1276, 1280, 1284, 1288, 1292, 1296, 1300, 1304, 1309, 1313, 1317, 1321,
            1325, 1329, 1333, 1337, 1342, 1346, 1350, 1354, 1358, 1362, 1366, 1370, 1375, 1379,
            1383, 1387, 1391, 1395, 1399, 1403, 1408, 1412, 1416, 1420, 1424, 1428, 1432, 1436,
            1441, 1445, 1449, 1453, 1457, 1461, 1465, 1469, 1474, 1478, 1482, 1486, 1490, 1494,
            1498
        )
        yearsStartingJdn[0] = 2388438 /* jdn of 1206 */
        for (i in 0 until yearsStartingJdn.size - 1) yearsStartingJdn[i + 1] = yearsStartingJdn[i] +
                if (Arrays.binarySearch(leapYears,
                        i + startingYear
                    ) < 0
                ) 365 else 366
    }
}