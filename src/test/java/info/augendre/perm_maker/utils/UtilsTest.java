package info.augendre.perm_maker.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by gaugendre on 01/08/2016 16:22
 */
public class UtilsTest {
    @Test
    public void dateFromLocalTime() throws Exception {
        LocalTime localTime = LocalTime.now();
        Date date = Utils.dateFromLocalTime(localTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        assertEquals(localTime.getHour(), cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(localTime.getMinute(), cal.get(Calendar.MINUTE));
        assertEquals(localTime.getSecond(), cal.get(Calendar.SECOND));
    }

    @Test
    public void localDateFromDate() throws Exception {
        Date date = new Date();
        LocalDate localDate = Utils.localDateFromDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        assertEquals(localDate.getDayOfMonth(), cal.get(Calendar.DAY_OF_MONTH));
        assertEquals((localDate.getDayOfWeek().getValue() % 7) + 1, cal.get(Calendar.DAY_OF_WEEK));
        assertEquals(localDate.getDayOfYear(), cal.get(Calendar.DAY_OF_YEAR));
        assertEquals(localDate.getMonthValue(), cal.get(Calendar.MONTH) + 1);
        assertEquals(localDate.getYear(), cal.get(Calendar.YEAR));
    }

    @Test
    public void localTimeFromDate() throws Exception {
        Date date = new Date();
        LocalTime localTime = Utils.localTimeFromDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        assertEquals(cal.get(Calendar.HOUR_OF_DAY), localTime.getHour());
        assertEquals(cal.get(Calendar.MINUTE), localTime.getMinute());
        assertEquals(cal.get(Calendar.SECOND), localTime.getSecond());
    }
}