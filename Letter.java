package org.cis120.wordle;

import java.util.*;

/*
class that contains information about every letter of the alphabet.
Used to keep track of whether a letter has been used, whether it is in the word,
 whether it is in the correct position, etc.
 */
class Letter {

    /* if state is 0, the letter has not been used.
    if the state is 1, the letter has been used but is not in the word
       if state is 2, the letter has been used and is in the word.
     */
    private int state;
    private Set<Integer> correctSpot; // list of spaces where the letter is in the correct spot

    // constructor
    public Letter(char c) {
        state = 0;
        correctSpot = new TreeSet<>();
    }

    /*
    getters and setters
     */

    public int getState() {
        return state;
    }

    public void changeState(int b) {
        state = b;
    }

    public Set<Integer> getCorrectSpot() {
        return correctSpot;
    }

    public void addCorrectSpot(int i) {
        correctSpot.add(i);
    }
}

