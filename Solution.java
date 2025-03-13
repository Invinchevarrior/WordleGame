package Wordle;


import java.util.*;

public class Solution{
    private Set<String> possibleWords;
    private static final String COMMON_STARTERS = "STARE"; // One of the best starting words

    public Solution(Set<String> dictionary) {
        this.possibleWords = new HashSet<>(dictionary);
    }

    public String getHint(Stack<String> previousGuesses, Stack<Status[]> results) {
        // If no guesses made yet, return a common starter word
        if (previousGuesses.isEmpty()) {
            return COMMON_STARTERS.toLowerCase();
        }

        // Filter possible words based on previous guesses and their results
        filterPossibleWords(previousGuesses, results);

        // If only one word remains, that's the answer
        if (possibleWords.size() == 1) {
            return possibleWords.iterator().next();
        }

        // Calculate letter frequencies for remaining possible words
        return findBestGuess();
    }

    private void filterPossibleWords(Stack<String> guesses, Stack<Status[]> results) {
        Iterator<String> guessIter = guesses.iterator();
        Iterator<Status[]> resultIter = results.iterator();

        while (guessIter.hasNext() && resultIter.hasNext()) {
            String guess = guessIter.next();
            Status[] result = resultIter.next();

            possibleWords.removeIf(word -> !isWordCompatible(word, guess, result));
        }
    }

    private boolean isWordCompatible(String word, String guess, Status[] result) {
        for (int i = 0; i < 5; i++) {
            char guessChar = guess.charAt(i);
            char wordChar = word.charAt(i);

            switch (result[i]) {
                case CORRECT:
                    if (wordChar != guessChar) return false;
                    break;
                case PARTIAL:
                    if (wordChar == guessChar || !word.contains(String.valueOf(guessChar)))
                        return false;
                    break;
                case INCORRECT:
                    if (word.contains(String.valueOf(guessChar))) return false;
                    break;
            }
        }
        return true;
    }

    private String findBestGuess() {
        Map<Character, Integer> letterFreq = new HashMap<>();
        Map<Integer, Integer> positionFreq = new HashMap<>();

        // Calculate letter frequencies
        for (String word : possibleWords) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                letterFreq.merge(c, 1, Integer::sum);
                positionFreq.merge(i * 26 + (c - 'a'), 1, Integer::sum);
            }
        }

        // Find word with highest score based on letter frequencies
        String bestWord = null;
        int maxScore = -1;

        for (String word : possibleWords) {
            int score = calculateWordScore(word, letterFreq, positionFreq);
            if (score > maxScore) {
                maxScore = score;
                bestWord = word;
            }
        }

        return bestWord != null ? bestWord : possibleWords.iterator().next();
    }

    private int calculateWordScore(String word, Map<Character, Integer> letterFreq,
                                   Map<Integer, Integer> positionFreq) {
        Set<Character> usedChars = new HashSet<>();
        int score = 0;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (usedChars.add(c)) {
                score += letterFreq.getOrDefault(c, 0);
            }
            score += positionFreq.getOrDefault(i * 26 + (c - 'a'), 0);
        }

        return score;
    }
}