import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuessPanel extends JPanel implements ActionListener {
    private static final JButton[] letters = new JButton[26];

    GuessPanel() {
        int j = 0;
        for (char i = 'A'; i <= 'Z'; i++) {
            letters[j] = new JButton(String.valueOf(i));
            letters[j].setFocusable(false);
            letters[j].setFont(new Font("Comic Sans",Font.BOLD, 20));
            letters[j].addActionListener(this);
            this.add(letters[j]);
            j++;
        }
        this.setBackground(new Color(0, 84, 209));
        this.setOpaque(true);
    }

    public static void replaceAsterisks(char c) {
        char[] temp = HangmanPanel.getWordWithHiddenLetters().toCharArray();
        for (int i = 0; i < Main.getWord().length(); i++) {
            if (Main.getWord().charAt(i) == c) {
                temp[i] = c;
            }
        }
        HangmanPanel.setWordWithHiddenLetters(String.valueOf(temp));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!HangmanPanel.isGameOver()) {
            JButton pressedButton = (JButton) e.getSource();
            if (Main.getWord().contains(pressedButton.getText())) {
                replaceAsterisks(pressedButton.getText().charAt(0));
                pressedButton.setBackground(Color.GREEN);
                pressedButton.setEnabled(false);
                if (HangmanPanel.getWordWithHiddenLetters().equals(Main.getWord())) {
                    HangmanPanel.setGameOver(true);
                    HangmanPanel.victory();
                    HangmanPanel.resetSetup();
                }
            } else {
                pressedButton.setBackground(Color.RED);
                pressedButton.setEnabled(false);
                HangmanPanel.increaseMistakesCount();
                if (HangmanPanel.getMistakesCount() == 7) {
                    HangmanPanel.guessfail();
                    HangmanPanel.setWordWithHiddenLetters(Main.getWord());
                    HangmanPanel.setGameOver(true);
                    HangmanPanel.resetSetup();
                }
            }
        }
    }

    public static void reset() {
        for (int i = 0; i < letters.length; i++) {
            letters[i].setEnabled(true);
            letters[i].setBackground(new JButton().getBackground());
        }
    }
}
