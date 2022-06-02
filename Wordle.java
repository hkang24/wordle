package org.cis120.wordle;

import java.io.*;
import java.util.*;

class Wordle<T extends Comparable<T>> {

    private char[][] board; // board of words played so far
    private int numWords; // number of words played by the player so far
    private boolean gameOver; // whether the game is over
    private String word; // the answer word for the given game
    private Map<Character, Letter> keyboard; // map that keeps track of the letter state
    private char[] wordLetters; // char array version of word.
    private int numLetters; // which letter of the users entered word is being references
    private Set<String> dictionary; // all the possible 5 letter words in the dictionary
    private final char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                                    'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                                    's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /*
    sets all variables to the initial state. Used when the reset button is clicked.
     */
    public void reset() {
        board = new char[6][5];
        numWords = 0;
        gameOver = false;
        keyboard = new TreeMap<>();
        numLetters = 0;
        for (char c : letters) {
            keyboard.put(c, new Letter(c));
        }

        dictionary = fileToArray("src/main/java/org/cis120/wordle/valid_words.txt");
        word = getRandomWord();
        wordLetters = stringToArray(word);
        //restoreGameState();
    }

    /*
    constructor
     */
    public Wordle() {
        //reset();
        restoreGameState();
    }

    /*
    checks whether the entered string is 5 letters and is in the dictionary
     */
    public boolean checkWord(String st) {
        if (st.length() != 5 || !dictionary.contains(st)) {
            return false;
        }
        return true;
    }

    /*
    runs when a player enters a word. if the word is valid,
     it will be inputted into the board and other variables
    will be updated.
     */
    public void playWord(String st) {
        // check if the attempted word fits the requirement to be played.
        if (checkWord(st)) {
            //throw new IllegalArgumentException("invalid word");
            char[] wordString = stringToArray(st);
            // go through every letter of the played word.
            for (int i = 0; i < wordString.length; i++) {
                // put letter into its respective board placement
                board[numWords][i] = wordString[i];
                /* go through every letter in the answer word and
                  if the letter is in the correct spot, add
                  the character to the letter.correctSpot treeset
                  and update the letter in keyboard to "correct spot"
                  (2). if the character is in the word but not in the
                  right placement, mark the letter on the keyboard
                  as 1 (in word but wrong spot).
                 */
                for (int j = 0; j < 5; j++) {
                    Letter l = keyboard.get(wordString[i]);
                    if (wordString[i] == wordLetters[j]) {
                        l.changeState(2);
                        if (i == j) {
                            l.addCorrectSpot(i);
                        }
                    } else {
                        if (l.getState() != 2) {
                            l.changeState(1);
                        }
                    }
                }
            }
            numWords++;
            numLetters = 0;
            // if the entered word is the same as the correct word, game is over
            if (word.equals(st)) {
                gameOver = true;
            }
        }
    }

    /*
    makes a string a char array
     */
    public char[] stringToArray(String st) {
        char[] wordString = new char[st.length()];
        for (int i = 0; i < st.length(); i++) {
            wordString[i] = st.charAt(i);
        }
        return wordString;
    }

    /*
    prints the board for testing purposes
     */
    public void printGameState() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j == 4) {
                    System.out.println("  ");
                }
            }
        }

    }

    /*
    takes in a string and inputs every String line into a Set.
    Used to create dictionary of valid words
    */
    public Set<String> fileToArray(String file) {
        BufferedReader reader;
        Set<String> validWords = new TreeSet<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            boolean nonEmpty = true;
            while (nonEmpty) {
                String line = reader.readLine();
                if (line != null) {
                    if (!line.isEmpty()) {
                        validWords.add(line);
                    }
                } else {
                    nonEmpty = false;
                }
            }
            return validWords;
        } catch (Exception e) {
            throw new IllegalArgumentException("filePath DNE or null");
        }
    }

    /*
    saves all the information of the current game state that is needed to recreate it later.
     */
    public void saveGameState() {
        try {
            FileWriter file = new FileWriter("src/main/java/org/cis120/wordle/game_state.csv");

            BufferedWriter br = new BufferedWriter(file);

            br.write(word);
            br.newLine();

            for (int i = 0; i < numWords; i++) {
                String boardWords = "";
                for (int j = 0; j < 5; j++) {
                    boardWords = boardWords + board[i][j];
                }
                br.write(boardWords);
                br.newLine();
            }

            br.flush();
            br.close();
        } catch (Exception e) {
        }
    }

    /*
    takes in a file containing information about the game state
    and inputs it into the board and all other
    applicable variables.
     */
    public void restoreGameState() {
        try {
            FileReader saveFile = new FileReader("src/main/java/org/cis120/wordle/game_state.csv");

            BufferedReader br = new BufferedReader(saveFile);

            reset();

            word = br.readLine();
            String str = br.readLine();

            while (str != null) {
                playWord(str);
                str = br.readLine();
            }

        } catch (EOFException e) {
        } catch (Exception e2) {
        }
    }

    /*
    gets a random word from the dictionary
     */
    public String getRandomWord() {
        Object[] words = dictionary.toArray();
        double randomDouble = (Math.random() * words.length);
        int random = (int)randomDouble;
        String randomString = (String) words[random];
        return randomString;
    }

    /*
    getters and setters.
     */
    public char[][] getBoard() {
        return board;
    }

    public Letter getLetter(char ch) {
        return keyboard.get(ch);
    }

    public int getNumWords() {
        return numWords;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String str) {
        word = str;
    }

    public void setWordLetters() {
        wordLetters = word.toCharArray();
    }

    public Map<Character, Letter> getKeyboard() {
        return keyboard;
    }

    // for testing purposes
    public boolean validChar(char c) {
        for (char ch : letters) {
            if (c == ch) {
                return true;
            }
        }
        return false;
    }
}
