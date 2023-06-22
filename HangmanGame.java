import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HangmanGame extends JFrame {
    private Hangman hangman;
    private JLabel wordLabel;
    private JLabel attemptsLabel;
    private JTextField inputField;
    private JButton guessButton;
    private JTextArea hangmanArea;

    public HangmanGame() {
        hangman = new Hangman();

        setTitle("Hangman Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1, 10, 10));

        wordLabel = new JLabel();
        attemptsLabel = new JLabel();

        inputField = new JTextField();
        guessButton = new JButton("Guess");

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String guess = inputField.getText();
                hangman.makeGuess(guess);
                updateUI();
                inputField.setText("");
                inputField.requestFocus();
            }
        });

        centerPanel.add(wordLabel);
        centerPanel.add(attemptsLabel);
        centerPanel.add(inputField);
        centerPanel.add(guessButton);

        hangmanArea = new JTextArea(10, 10);
        hangmanArea.setEditable(false);
        hangmanArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        add(centerPanel, BorderLayout.CENTER);
        add(hangmanArea, BorderLayout.EAST);

        updateUI();
    }

    private void updateUI() {
        wordLabel.setText(hangman.getMaskedWord());
        attemptsLabel.setText("Attempts left: " + hangman.getAttemptsLeft());

        if (hangman.isGameOver()) {
            endGame(hangman.getGameOverMessage());
        }

        hangmanArea.setText(hangman.getHangmanArt());
    }

    private void endGame(String message) {
        guessButton.setEnabled(false);
        inputField.setEnabled(false);
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HangmanGame hangmanGame = new HangmanGame();
            hangmanGame.setVisible(true);
        });
    }
}

class Hangman {
    private String[] wordList = {"hangman", "java", "programming", "openai", "computer"};
    private String wordToGuess;
    private Set<Character> guessedLetters;
    private int attemptsLeft;

    public Hangman() {
        guessedLetters = new HashSet<>();
        attemptsLeft = 6;
        wordToGuess = getRandomWord();
    }

    private String getRandomWord() {
        Random random = new Random();
        int index = random.nextInt(wordList.length);
        return wordList[index];
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getMaskedWord() {
        StringBuilder maskedWord = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            if (guessedLetters.contains(c)) {
                maskedWord.append(c).append(" ");
            } else {
                maskedWord.append("_ ");
            }
        }
        return maskedWord.toString();
    }

    public void makeGuess(String guess) {
        guess = guess.toLowerCase();
        if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
            JOptionPane.showMessageDialog(null, "Please enter a single letter.");
            return;
        }

        char letter = guess.charAt(0);
        if (guessedLetters.contains(letter)) {
            JOptionPane.showMessageDialog(null, "You already guessed that letter.");
            return;
        }

        guessedLetters.add(letter);
        if (!wordToGuess.contains(guess)) {
            attemptsLeft--;
        }
    }

    public boolean isGameOver() {
        return attemptsLeft <= 0 || !getMaskedWord().contains("_");
    }

    public String getGameOverMessage() {
        if (attemptsLeft <= 0) {
            return "You lose! The word was: " + wordToGuess;
        } else {
            return "Congratulations! You won!";
        }
    }

    public String getHangmanArt() {
        String[] hangmanArt = {
                "  +---+\n  |   |\n      |\n      |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n      |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n  |   |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|   |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n      |\n=========",
                "  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n      |\n========="
        };

        return hangmanArt[6 - attemptsLeft];
    }
}