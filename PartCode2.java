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