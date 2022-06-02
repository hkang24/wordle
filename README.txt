Name : Hye-Jin Kang
Game : Wordle
Concepts Used : 2D Arrays, Collections/Maps, File I/O, JUnit Testable Component

Instructions to Play :

When you run the game, the user will see a 5 by 6 grid along with a keyboard. The goal of the game is to guess a
5 letter word within 6 tries. To do so, the user can input a word into the textField and click the Submit button.
If the word is in the list of valid words (pulled from an online file) and is 5 letters long, the word will be inputted
into the grid with either, dark grey, yellow, or green backgrounds. If the letter is dark grey, that means the letter
inputted is not in the correct word at all. If the letter is yellow,the letter is in the word but not in the correct
letter space. If the letter is green, the letter is in the word and also in the correct spot. The keyboard letters will
also reflect the letter states with light grey meaning the letter has not been used, dark grey meaning the letter
is not present in the word, and green meaning the letter is in the word.

If the user exits the game at any point, they can run the game again and the past game state will appear as it was.
If the user wants to restart the game, they can click the reset button, and it will be a new game with a new random
word.

Concept Justifications :

2D Array -

I decided to use a 2D array because it seemed to be the best option to store information that I need to be able to
reference easily. In addition, since the words are always 5 letters long and the user has 6 guesses, a 2D array worked
very well since it could be referenced like a grid.
I decided to store Characters in the 2D array so each row could represent a word and it would be easily referencable
when printing letters in the GUI.

Collections / Map -

I decided to use a TreeMap to store information regarding all the letters in the alphabet. Because I wanted to store
both the state of the letter along with the correct positions of the letter, I could not use a Set or an Array.
By using a Map, I was able to have each letter character point to a Letter object that stored both the state and
correct position as variables. Although I could have possibly used an array of Letters, the order of the letters did
not matter much for my implementation so it would have simply made it harder to reference the letters I wanted since I
would have to keep track of the position of the Letter as an integer.

File I/O -

I used BufferedReader and BufferedWriter to save information regarding the correct word of the game state and also
what words the player inputted into a file. When the player exits the game and returns, the BufferedReader read the
file line by line and recreated the game state by setting the correct word, and playing each word the player inputted
prior to exiting the game.

JUnit Testable Component -

I used JUnit Tests to test the game logic contained in my Wordle file. I tested all the internal functions along with
instances when the word is not valid, too long, game has been saved and restored, etc.