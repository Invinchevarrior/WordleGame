package Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Wordle extends JFrame {
    private WordleGame game;
    private JTextField guessField;
    private JPanel wordGrid;
    private JLabel messageLabel;
    private final int ROWS = 6;
    private final int COLS = 5;
    private JLabel[][] letterLabels;
    private Solution solution;
    private JButton hintButton;
    public Wordle(Set<String> dictionary) {
        setTitle("Wordle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create word grid
        wordGrid = new JPanel(new GridLayout(ROWS, COLS, 5, 5));
        letterLabels = new JLabel[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                letterLabels[i][j] = new JLabel();
                letterLabels[i][j].setOpaque(true);
                letterLabels[i][j].setBackground(Color.WHITE);
                letterLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                letterLabels[i][j].setHorizontalAlignment(JLabel.CENTER);
                letterLabels[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                wordGrid.add(letterLabels[i][j]);
            }
        }

        // Create input panel
        JPanel inputPanel = new JPanel();
        guessField = new JTextField(10);
        JButton guessButton = new JButton("Guess");
        inputPanel.add(guessField);
        inputPanel.add(guessButton);

        // Create message label
        messageLabel = new JLabel("Enter your guess");
        messageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add components to frame
        add(wordGrid, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);

        // Initialize game
        game = new WordleGame(dictionary);
        solution = new Solution(dictionary);
        // Add action listeners
        guessButton.addActionListener(e -> makeGuess());
        guessField.addActionListener(e -> makeGuess());

        hintButton = new JButton("Hint");
        inputPanel.add(hintButton);
        hintButton.addActionListener(e -> getHint());

        // Set frame properties
        setSize(400, 500);
        setLocationRelativeTo(null);
    }

    private void getHint() {
        if (!game.gameOver()) {
            String hint = solution.getHint(game.getGuesses(), game.getResults());
            guessField.setText(hint);
        }
    }

    private void makeGuess() {
        String guess = guessField.getText().toLowerCase().trim();
        try {
            game.guess(guess);
            updateGrid();
            guessField.setText("");

            if (game.gameOver()) {
                guessField.setEnabled(false);
                messageLabel.setText(game.finalScore());
                messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
                if (game.getGuesses().peek().equals(game.getSecretWord())) {
                    messageLabel.setForeground(new Color(106, 170, 100)); // Green for success
                } else {
                    messageLabel.setForeground(new Color(220, 20, 60)); // Red for failure
                }
            }
        } catch (GuessException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    private void updateGrid() {
        int currentRow = game.getTurn() - 2; // -2 because getTurn() returns next turn
        String guess = game.getGuesses().peek();
        Status[] result = game.getResult();

        for (int i = 0; i < COLS; i++) {
            letterLabels[currentRow][i].setText(String.valueOf(guess.charAt(i)).toUpperCase());

            switch (result[i]) {
                case CORRECT:
                    letterLabels[currentRow][i].setBackground(new Color(106, 170, 100));
                    letterLabels[currentRow][i].setForeground(Color.WHITE);
                    break;
                case PARTIAL:
                    letterLabels[currentRow][i].setBackground(new Color(201, 180, 88));
                    letterLabels[currentRow][i].setForeground(Color.WHITE);
                    break;
                case INCORRECT:
                    letterLabels[currentRow][i].setBackground(new Color(120, 124, 126));
                    letterLabels[currentRow][i].setForeground(Color.WHITE);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        // Load dictionary
        Set<String> dictionary = new HashSet<>();
        try {
            Scanner scanner = new Scanner(new File("./words.txt"));
            while(scanner.hasNextLine()) {
                dictionary.add(scanner.nextLine().trim().toLowerCase());
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error loading dictionary: " + e.getMessage());
            return;
        }

        // Create and show GUI
        SwingUtilities.invokeLater(() -> {
            Wordle gui = new Wordle(dictionary);
            gui.setVisible(true);
        });
    }
}