package info.augendre.perm_maker.utils;

import org.junit.Before;
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
    private Calendar cal;
    private Date date;

    @Before
    public void setUp() throws Exception {
        date = new Date();
        cal = Calendar.getInstance();
        cal.setTime(date);
    }

    @Test
    public void dateFromLocalTime() throws Exception {
        LocalTime localTime = LocalTime.now();
        date = Utils.dateFromLocalTime(localTime);
        cal.setTime(date);

        assertEquals(localTime.getHour(), cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(localTime.getMinute(), cal.get(Calendar.MINUTE));
        assertEquals(localTime.getSecond(), cal.get(Calendar.SECOND));
    }

    @Test
    public void localDateFromDate() throws Exception {
        LocalDate localDate = Utils.localDateFromDate(date);

        assertEquals(localDate.getDayOfMonth(), cal.get(Calendar.DAY_OF_MONTH));
        assertEquals((localDate.getDayOfWeek().getValue() % 7) + 1, cal.get(Calendar.DAY_OF_WEEK));
        assertEquals(localDate.getDayOfYear(), cal.get(Calendar.DAY_OF_YEAR));
        assertEquals(localDate.getMonthValue(), cal.get(Calendar.MONTH) + 1);
        assertEquals(localDate.getYear(), cal.get(Calendar.YEAR));
    }

    @Test
    public void localTimeFromDate() throws Exception {
        LocalTime localTime = Utils.localTimeFromDate(date);

        assertEquals(cal.get(Calendar.HOUR_OF_DAY), localTime.getHour());
        assertEquals(cal.get(Calendar.MINUTE), localTime.getMinute());
        assertEquals(cal.get(Calendar.SECOND), localTime.getSecond());
    }
}