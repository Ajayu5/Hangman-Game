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
