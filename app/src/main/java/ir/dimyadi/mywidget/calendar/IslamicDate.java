package ir.dimyadi.mywidget.calendar;

import ir.dimyadi.mywidget.calendar.islamic.FallbackIslamicConverter;
import ir.dimyadi.mywidget.calendar.islamic.IranianIslamicDateConverter;
import ir.dimyadi.mywidget.calendar.islamic.UmmAlQuraConverter;

/**
 * @author Amir
 * @author ebraminio
 */

public class IslamicDate extends AbstractDate {

    // Converters
    private static boolean useUmmAlQura = false;
    private static int islamicOffset = 0;

    public IslamicDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

    public IslamicDate(long jdn) {
        super(jdn);
    }

    public IslamicDate(AbstractDate date) {
        super(date);
    }

    @Override
    public long toJdn() {
        int year = getYear(), month = getMonth(), day = getDayOfMonth();

        long tableResult = useUmmAlQura
                ? UmmAlQuraConverter.toJdn(year, month, day)
                : IranianIslamicDateConverter.toJdn(year, month, day);

        if (tableResult != -1)
            return tableResult - islamicOffset;

        return FallbackIslamicConverter.toJdn(year, month, day) - islamicOffset;
    }

    @Override
    protected int[] fromJdn(long jdn) {
        jdn += islamicOffset;
        int[] result = useUmmAlQura
                ? UmmAlQuraConverter.fromJdn(jdn)
                : IranianIslamicDateConverter.fromJdn(jdn);

        if (result == null) result = FallbackIslamicConverter.fromJdn(jdn);

        return result;
    }
}
