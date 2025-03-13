package Wordle;

public class GuessException extends Exception {
    /**
     * Constructor for a GuessException
     * @param message message of the GuessException
     */
    public GuessException(String message) {
        super(message);
    }
}