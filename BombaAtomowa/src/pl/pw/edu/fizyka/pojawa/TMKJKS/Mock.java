package pl.pw.edu.fizyka.pojawa.TMKJKS;

import javax.swing.*;
import java.awt.*;

/**
 * Just a panel that shows very big button.
 */
public class Mock extends JPanel {

    public Mock(String name) {
        super();
        setLayout(new BorderLayout());
        add(new JButton(name), BorderLayout.CENTER);
        validate();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(640, 480);
        frame.add(new Mock("Wykres"));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}