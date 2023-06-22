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

    
}
