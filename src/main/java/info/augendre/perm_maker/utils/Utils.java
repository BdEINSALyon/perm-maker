package info.augendre.perm_maker.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by gaugendre on 29/06/16
 */
public class Utils {
    public static LocalTime localTimeFromDate(Date date) {
        Instant startInstant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(startInstant, ZoneId.systemDefault()).toLocalTime();
    }
}
