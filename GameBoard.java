package org.cis120.wordle;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.swing.*;

public class GameBoard extends JPanel {
    private Wordle wordle;
    private JLabel status;
    private JLabel valid;
    private JLabel correct;
    private String enteredWord;
    private char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                              'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                              'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                              'y', 'z'};

    public static final int SQUARE_SIZE = 60;
    public static final int KEY_WIDTH = 45;
    public static final int KEY_HEIGHT = 60;

    public GameBoard(JButton b, JLabel statusInit, JLabel validInit, JLabel correctInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        wordle = new Wordle(); // initializes model for the game

        status = statusInit;

        correct = correctInit;

        setStatus(wordle);

        valid = validInit;

    /*
    listens for the enter button to be clicked.
    if it is clicked, the word entered will be played
    and edit the status and valid jlabels as appropriate.
    */
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!wordle.checkWord(enteredWord)) {
                    valid.setText("Invalid Word :(");
                } else {
                    wordle.playWord(enteredWord);
                    valid.setText("");
                    saveGameBoard();
                    setStatus(wordle);
                    repaint();
                }
            }
        });
        //saveGameBoard();
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        wordle.reset();
        setStatus(wordle);
        repaint();
    }

    /*
    sets the status label according to the game state.
     */
    public void setStatus(Wordle w) {
        int numWords = w.getNumWords();
        if (w.getGameOver()) {
            status.setText("Congrats!");
        } else if (numWords == 6) {
            status.setText("You Lost :(");
            correct.setText("Correct Word : " + wordle.getWord());
        } else if (numWords == 0) {
            status.setText("Good Luck!");
        } else {
            status.setText(wordle.getNumWords() + " Words Played");
        }
    }

    /*
    setter and getter
     */

    public void setEnteredWord(String st) {
        enteredWord = st;
    }

    public String getEnteredWord() {
        return this.enteredWord;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBoard(g);
        paintKeyBoard(g);

    }

    /*
    paints the gameboard and chooses the color of the blocks
    depending on the state of the letter (has been used,
    has not been used, in the word, etc)
     */
    public void paintBoard(Graphics g) {
        char[][] characters = wordle.getBoard();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                char c = characters[j][i];
                // if there is not character inputted.
                if (!validChar(c)) {
                    paintEmptySquare(g, (580 + (i * SQUARE_SIZE) + (i * 10)),
                            100 + (j * SQUARE_SIZE) + (j * 10));
                } else {
                    Letter l = wordle.getLetter(c);
                    Set<Integer> correctSpot = l.getCorrectSpot();
                    // if letter is not in the word.
                    if (l.getState() == 0 || l.getState() == 1) {
                        paintGreySquare(g, (580 + (i * SQUARE_SIZE) + (i * 10)),
                                100 + (j * SQUARE_SIZE) + (j * 10),
                                   characters[j][i]);
                    } else if (correctSpot.contains(i)) { // if the letter is in the correct spot
                        paintGreenSquare(g, (580 + (i * SQUARE_SIZE) + (i * 10)),
                                100 + (j * SQUARE_SIZE) + (j * 10), characters[j][i]);
                    } else { // if the letter is in the word but wrong place
                        paintYellowSquare(g, (580 + (i * SQUARE_SIZE) + (i * 10)),
                                100 + (j * SQUARE_SIZE) + (j * 10), characters[j][i]);
                    }
                }
            }
        }
    }
    /*
    paints keyboard and chooses key color based on
    whether the letter had been used, is not in the word,
    or is in the word.
     */
    public void paintKeyBoard(Graphics g) {
        Map<Character, Letter> map = wordle.getKeyboard();

        char[] firstRow = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'};
        char[] secondRow = {'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'};
        char[] thirdRow = {'z', 'x', 'c', 'v', 'b', 'n', 'm'};

        for (int i = 0; i < 10; i++) {
            Letter l = map.get(firstRow[i]);
            int state = l.getState();

            if (state == 0) {
                paintUnusedKey(g, (495 + (i * KEY_WIDTH) + (i * 5)), 550, firstRow[i]); // not used
            } else if (state == 1) {
                paintGreyKey(g, (495 + (i * KEY_WIDTH) + (i * 5)), 550, firstRow[i]); //not in word
            } else {
                paintGreenKey(g, (495 + (i * KEY_WIDTH) + (i * 5)), 550, firstRow[i]); // in word
            }
        }

        for (int i = 0; i < 9; i++) {
            Letter l = map.get(secondRow[i]);
            int state = l.getState();

            if (state == 0) {
                paintUnusedKey(g, (520 + (i * KEY_WIDTH) + (i * 5)), 615, secondRow[i]);
            } else if (state == 1) {
                paintGreyKey(g, (520 + (i * KEY_WIDTH) + (i * 5)), 615, secondRow[i]);
            } else {
                paintGreenKey(g, (520 + (i * KEY_WIDTH) + (i * 5)), 615, secondRow[i]);
            }
        }

        for (int i = 0; i < 7; i++) {
            Letter l = map.get(thirdRow[i]);
            int state = l.getState();

            if (state == 0) {
                paintUnusedKey(g, (570 + (i * KEY_WIDTH) + (i * 5)), 680, thirdRow[i]);
            } else if (state == 1) {
                paintGreyKey(g, (570 + (i * KEY_WIDTH) + (i * 5)), 680, thirdRow[i]);
            } else {
                paintGreenKey(g, (570 + (i * KEY_WIDTH) + (i * 5)), 680, thirdRow[i]);
            }
        }
    }

    /*
    paintUnusedKey, paintGreyKey, and paintGreenKey are helper function to paint keys
     */
    public void paintUnusedKey(Graphics g, int x, int y, Character ch) {
        Color c = new Color(190, 190, 190);
        g.setColor(c);
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRoundRect(x - (KEY_WIDTH / 2), y - (KEY_HEIGHT / 2), KEY_WIDTH, KEY_HEIGHT, 10, 10);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.setColor(Color.BLACK);
        if (validChar(ch)) {
            g2.drawString(ch.toString().toUpperCase(Locale.ROOT), x - (KEY_WIDTH / 4) + 6,
                    y + (KEY_HEIGHT / 4) - 9);
        }
    }

    public void paintGreyKey(Graphics g, int x, int y, Character ch) {
        Color c = new Color(86, 90, 92);
        g.setColor(c);
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRoundRect(x - (KEY_WIDTH / 2), y - (KEY_HEIGHT / 2), KEY_WIDTH, KEY_HEIGHT, 10, 10);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.setColor(Color.WHITE);
        if (validChar(ch)) {
            g2.drawString(ch.toString().toUpperCase(Locale.ROOT), x - (KEY_WIDTH / 4) + 6,
                    y + (KEY_HEIGHT / 4) - 9);
        }
    }

    public void paintGreenKey(Graphics g, int x, int y, Character ch) {
        Color c = new Color(46, 139, 87);
        g.setColor(c);
        Graphics2D g2 = (Graphics2D) g;
        g2.fillRoundRect(x - (KEY_WIDTH / 2), y - (KEY_HEIGHT / 2), KEY_WIDTH, KEY_HEIGHT, 10, 10);
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.setColor(Color.WHITE);
        if (validChar(ch)) {
            g2.drawString(ch.toString().toUpperCase(Locale.ROOT), x - (KEY_WIDTH / 4) + 6,
                    y + (KEY_HEIGHT / 4) - 9);
        }
    }

    /*
    paintEmpty, paintGreySquare, paintYellowSquare,
    and paintGreenSquare are helper function to paint keys
     */
    public void paintEmptySquare(Graphics g, int x, int y) {
        //int size = 50;
        Color c = new Color(150, 150, 150);
        g.setColor(c);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(x - (SQUARE_SIZE / 2), y - (SQUARE_SIZE / 2), SQUARE_SIZE, SQUARE_SIZE);
    }

    public void paintGreySquare(Graphics g, int x, int y, Character ch) {
        Color c = new Color(86, 90, 92);
        g.setColor(c);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.fillRect(x - (SQUARE_SIZE / 2), y - (SQUARE_SIZE / 2), SQUARE_SIZE, SQUARE_SIZE);
        g2.setFont(new Font("Arial", Font.BOLD, 35));
        g2.setColor(Color.WHITE);
        if (validChar(ch)) {
            g2.drawString(ch.toString().toUpperCase(Locale.ROOT), x - (SQUARE_SIZE / 4) + 2,
                    y + (SQUARE_SIZE / 4) - 2);
        }
    }

    public void paintYellowSquare(Graphics g, int x, int y, Character ch) {
        Color c = new Color(236, 213, 64);
        g.setColor(c);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.fillRect(x - (SQUARE_SIZE / 2), y - (SQUARE_SIZE / 2), SQUARE_SIZE, SQUARE_SIZE);
        g2.setFont(new Font("Arial", Font.BOLD, 35));
        g2.setColor(Color.WHITE);
        if (validChar(ch)) {
            g2.drawString(ch.toString().toUpperCase(Locale.ROOT), x - (SQUARE_SIZE / 4) + 2,
                    y + (SQUARE_SIZE / 4) - 2);
        }
    }

    public void paintGreenSquare(Graphics g, int x, int y, Character ch) {
        Color c = new Color(46, 139, 87);
        g.setColor(c);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.fillRect(x - (SQUARE_SIZE / 2), y - (SQUARE_SIZE / 2), SQUARE_SIZE, SQUARE_SIZE);
        g2.setFont(new Font("Arial", Font.BOLD, 35));
        g2.setColor(Color.WHITE);
        if (validChar(ch)) {
            g2.drawString(ch.toString().toUpperCase(Locale.ROOT), x - (SQUARE_SIZE / 4) + 2,
                    y + (SQUARE_SIZE / 4) - 2);
        }
    }

    /*
    saveGameBoard() and restoreGameBoard() saves the gameboard information.
     */
    public void saveGameBoard() {
        wordle.saveGameState();
    }

    public void restoreGameBoard() {
        wordle.restoreGameState();
    }

    /*
    checks if a character is one of the keyboard letters allowed.
     */
    public boolean validChar(char c) {
        for (char ch : letters) {
            if (c == ch) {
                return true;
            }
        }
        return false;
    }
}
