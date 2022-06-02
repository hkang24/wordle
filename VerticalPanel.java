package org.cis120.wordle;

import javax.swing.*;
import java.awt.*;

/*
stacks JLabels and JButtons vertically in one panel.
 */
public class VerticalPanel extends JPanel {

    public VerticalPanel(JLabel status, JLabel correct, JLabel valid) {
        setLayout(new GridLayout(0, 1));
        add(status);
        add(correct);
        add(valid);
    }

    public VerticalPanel(JLabel title, JButton reset) {
        setLayout(new GridLayout(0, 1));
        add(reset);
        add(title);
    }
}
