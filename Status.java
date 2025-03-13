package Wordle;

/**
 * Enum for result of each character guessed
 * @author Jai Wargacki
 */
public enum Status {
    INCORRECT("\u2B1B"),
    CORRECT("\uD83D\uDFE9"),
    PARTIAL("\uD83D\uDFE8");

    /**
     * String of the result status
     */
    private String string;

    /**
     * Constructor for the enum
     * @param string string of the result status
     */
    private Status(String string) {
        this.string = string;
    }

    /**
     * To string for this enum
     * @return proper colored square emoji
     */
    @Override
    public String toString() {
        return this.string;
    }
}
