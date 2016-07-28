package info.augendre.perm_maker.actions;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

/**
 * Created by gaugendre on 29/07/2016 01:20
 */
public class AboutAction extends AbstractAction {
    private JFrame frame;
    private static ResourceBundle stringsBundle = ResourceBundle.getBundle("strings");

    public AboutAction(JFrame frame) {
        super(stringsBundle.getString("menu-about"));
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {// for copying style
        JLabel label = new JLabel();
        Font font = label.getFont();

        // create some css from the label's font
        StringBuilder style = new StringBuilder("font-family:" + font.getFamily() + ";");
        style.append("font-weight:").append(font.isBold() ? "bold" : "normal").append(";");
        style.append("font-size:").append(font.getSize()).append("pt;");

        JEditorPane editorPane = new JEditorPane(
            "text/html",
            "<html><body style=\"" + style + "\">" +
                stringsBundle.getString("menu-aboutdialog-message") +
                "</body></html>"
        );

        editorPane.addHyperlinkListener(event -> {
            if (event.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                try {
                    Desktop.getDesktop().browse(event.getURL().toURI());
                }
                catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });

        editorPane.setEditable(false);
        editorPane.setBackground(label.getBackground());

        JOptionPane.showMessageDialog(
            frame,
            editorPane,
            stringsBundle.getString("menu-aboutdialog-title"),
            JOptionPane.PLAIN_MESSAGE
        );
    }
}
