package info.augendre.perm_maker.utils;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.*;
import java.util.Date;

/**
 * Holds some utilities static methods.
 * @author gaugendre
 */
public class Utils {
    /**
     * Converts a {@link Date} object into {@link LocalTime}.
     * @param date The date to convert.
     * @return A {@link LocalTime} containing the time info of the given {@code date}.
     * @see #dateFromLocalTime(LocalTime)
     * @see #localDateFromDate(Date)
     */
    public static LocalTime localTimeFromDate(Date date) {
        Instant startInstant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(startInstant, ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * Converts a {@link LocalTime} object into {@link Date}.
     * @param localTime The local time to convert.
     * @return A {@link Date} containing the time info of the given {@code localTime}. The date is set to January 1st, 2000.
     * @see #localDateFromDate(Date)
     * @see #localTimeFromDate(Date)
     */
    public static Date dateFromLocalTime(LocalTime localTime) {
        Instant instant = localTime.atDate(LocalDate.of(2000, 1, 1)).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * Converts a {@link Date} object into {@link LocalDate}.
     * @param date The date to convert.
     * @return A {@link LocalTime} containing the date info of the given {@code date}.
     * @see #dateFromLocalTime(LocalTime)
     * @see #localTimeFromDate(Date)
     */
    public static LocalDate localDateFromDate(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Allow links contained in a {@link JEditorPane} to be opened in default system browser when clicked.
     * Adds a {@link javax.swing.event.HyperlinkListener} to the given {@code editorPane}.
     * @param editorPane The editor pane to modify.
     */
    public static void makeUrlClickableEditorPane(JEditorPane editorPane) {
        editorPane.addHyperlinkListener(event -> {
            if (event.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                try {
                    Desktop.getDesktop().browse(event.getURL().toURI());
                }
                catch (IOException | URISyntaxException e1) {
                    System.err.println("Can't open desktop default browser.");
                }
            }
        });
    }

    /**
     * Builds a {@link JEditorPane} given a HTML to display and a style to apply.
     * @param html The HTML to display. Must not contain {@code <html>} nor {@code <body>} tags.
     * @param style The style to apply. Must be formatted as CSS.
     * @return The built {@link JEditorPane} with the given CSS and HTML.
     * @see #htmlEditorPaneFactory(String)
     */
    public static JEditorPane htmlEditorPaneFactory(String html, String style) {
        JLabel label = new JLabel();
        JEditorPane editorPane = new JEditorPane(
            "text/html",
            "<html><body style=\"" + style + "\">" + html + "</body></html>"
        );

        Utils.makeUrlClickableEditorPane(editorPane);

        editorPane.setEditable(false);
        editorPane.setBackground(label.getBackground());

        return editorPane;
    }

    /**
     * Builds a {@link JEditorPane} given a HTML to display. Will apply a default style.
     * @param html The HTML to display. Must not contain {@code <html>} nor {@code <body>} tags.
     * @return The built {@link JEditorPane} with the given CSS and HTML.
     * @see #htmlEditorPaneFactory(String, String)
     */
    public static JEditorPane htmlEditorPaneFactory(String html) {
        // for copying style
        JLabel label = new JLabel();
        Font font = label.getFont();

        // create some css from the label's font
        String style = "font-family:" + font.getFamily() + ";" +
            "font-weight:" + (font.isBold() ? "bold" : "normal") + ";" +
            "font-size:" + font.getSize() + "pt;";

        return htmlEditorPaneFactory(html, style);
    }

    /**
     * Checks if the app is running on a Mac system.
     * @return true if running on a mac, false otherwise.
     */
    public static boolean isMac() {
        return System.getProperty("os.name").contains("Mac");
    }

    /**
     * Returns the day of week from the given string.
     * Only parses French.
     * @param s The day of week, in French ("lundi", "mardi", ...).
     * @return The {@link DayOfWeek} value.
     */
    public static DayOfWeek dayOfWeekFromString(String s) {
        s = s.toLowerCase();
        if (s.contains("lun")) {
            return DayOfWeek.MONDAY;
        }
        else if (s.contains("mar")) {
            return DayOfWeek.TUESDAY;
        }
        else if (s.contains("mer")) {
            return DayOfWeek.WEDNESDAY;
        }
        else if (s.contains("jeu")) {
            return DayOfWeek.THURSDAY;
        }
        else if (s.contains("ven")) {
            return DayOfWeek.FRIDAY;
        }
        else if (s.contains("sam")) {
            return DayOfWeek.SATURDAY;
        }
        else if (s.contains("dim")) {
            return DayOfWeek.SUNDAY;
        }
        return null;
    }
}
