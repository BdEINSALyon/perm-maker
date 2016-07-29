package info.augendre.perm_maker.utils;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
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

    public static String normalizeVersionNumber(String versionNumber) {
        if (versionNumber.startsWith("v")) versionNumber = versionNumber.substring(1);
        return versionNumber;
    }

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

    public static boolean isMac() {
        return System.getProperty("os.name").contains("Mac");
    }
}
