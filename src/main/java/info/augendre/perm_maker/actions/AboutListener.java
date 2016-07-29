package info.augendre.perm_maker.actions;

import javax.swing.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by gaugendre on 29/07/2016 03:38
 */
public class AboutListener implements InvocationHandler {
    private JFrame mainFrame;

    public AboutListener(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public Object invoke(Object proxy, Method method, Object[] args) {
        new AboutAction(mainFrame).showDialog();
        return null;
    }
}
