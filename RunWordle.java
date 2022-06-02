package org.cis120.wordle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RunWordle implements Runnable {
    public void run() {
        final JFrame frame = new JFrame("Wordle"); // create frame
        frame.setSize(350,100);
        frame.setLocation(350, 100);

        JPanel p = new JPanel(); // panel for textField and submit button

        JTextField textField = new JTextField(5); // textField
        p.add(textField);

        JButton b = new JButton("Submit"); // Submit button
        p.add(b);

        frame.add(p, BorderLayout.SOUTH); // add panel to bottom of screen

        // Status panel
        final JLabel status = new JLabel("Setting up...", SwingConstants.CENTER);
        status.setFont(new Font("Arial", Font.PLAIN, 15));

        final JLabel valid = new JLabel("", SwingConstants.CENTER);
        valid.setFont(new Font("Arial", Font.PLAIN, 15));

        final JLabel correct = new JLabel("", SwingConstants.CENTER);
        correct.setFont(new Font("Arial", Font.PLAIN, 15));

        JPanel info = new VerticalPanel(status, correct, valid);
        info.setPreferredSize(new Dimension(150, 100));

        frame.add(info, BorderLayout.EAST); // add info panel to the right of screen

        // Game board
        final GameBoard board = new GameBoard(b, status, valid, correct);
        board.restoreGameBoard();
        board.repaint();
        frame.add(board, BorderLayout.CENTER);


        /*
        action listener for textfield submit button.
        when the button is clicked, the string in the textfield
         is played and will reset the textField.
         */
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setEnteredWord(textField.getText());
                System.out.println(board.getEnteredWord());
                textField.setText("");
            }
        });

        /*final JButton save = new JButton(("Save"));
        save.addActionListener(e -> board.saveGameBoard());
        control_panel.add(save); */

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());

        // title JLabel
        final JLabel title = new JLabel("WORDLE", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setForeground(new Color(46, 139, 87));

        /*
        adds reset button and title to one vertical panel and places it at the top of the screen
         */
        final VerticalPanel control_panel = new VerticalPanel(title, reset);
        frame.add(control_panel, BorderLayout.NORTH);


        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
//        board.reset();
    }

}
