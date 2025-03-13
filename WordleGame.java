package Wordle;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class WordleGame {
    private String secretWord;
    private Stack<String> guesses;
    private Stack<Status[]> results;
    private Set<String> dictionary;

    public WordleGame(Set<String> dictionary) {
        int item = new Random().nextInt(dictionary.size());
        int i = 0;
        for(String s : dictionary) {
            if (i++ == item)
                this.secretWord = s;
        }
        this.guesses = new Stack<String>();
        this.results = new Stack<Status[]>();
        this.dictionary = dictionary;
    }

    public String finalScore() {
        StringBuilder score = new StringBuilder();
        boolean won = guesses.peek().equals(secretWord);

        if (won) {
            score.append("You won in ").append(guesses.size()).append(" tries!");
        } else {
            score.append("Game Over! The secret word was: ").append(secretWord);
        }

        return score.toString();
    }

    public int getTurn() {
        return guesses.size()+1;
    }

    public boolean gameOver() {
        return guesses.size() >= 6 || (guesses.size() > 0 && guesses.peek().equals(secretWord));
    }

    public String getSecretWord() {
        return secretWord;
    }

    public Status[] getResult() {
        return results.peek();
    }

    public Stack<String> getGuesses() {
        return guesses;
    }

    public Stack<Status[]> getResults() {
        return results;
    }

    public void guess(String guess) throws GuessException {
        if (guesses.size() == 6) {
            throw new GuessException("No more valid guesses.");
        } else if (!dictionary.contains(guess)) {
            throw new GuessException("Guess must be a valid 5 letter word.");
        } else if (guesses.contains(guess)) {
            throw new GuessException("That word was already guessed.");
        }
        guesses.add(guess);
        Status[] result = new Status[5];
        for(int i = 0; i < 5; i++) {
            char guessChar = guess.charAt(i);
            if(secretWord.charAt(i) == guessChar) {
                result[i] = Status.CORRECT;
            } else if (secretWord.contains(Character.toString(guessChar))) {
                result[i] = Status.PARTIAL;
            } else {
                result[i] = Status.INCORRECT;
            }
        }
        results.add(result);
    }
}