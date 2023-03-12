import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import javax.swing.JOptionPane;

public class Main {
    private static String word;
    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    // private String message = "";
    private static String serverIP = "127.0.0.1";
    private static Socket connection; // for client using TCP communication
    private static int port = 3287;

    public static void main(String[] args) {
        {
            try {
                connection = new Socket(InetAddress.getByName(serverIP), port);
                output = new ObjectOutputStream(connection.getOutputStream());
                output.flush();
                input = new ObjectInputStream(connection.getInputStream());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to pick a random server generated word?", "Word Guessing application",
                dialogButton);
        if (dialogResult == 0) {
            word = getRandomWord();
        } else {
            JPasswordField wordField = new JPasswordField();
            int okOrCancel = JOptionPane.showConfirmDialog(null, wordField, "Word",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (okOrCancel == JOptionPane.OK_OPTION) {
                word = String.valueOf(wordField.getPassword()).trim().toUpperCase();
            }
        }
        System.err.println("Your word is " + word);
        new HangmanFrame();
    }

    public static String getWord() {
        return word;
    }

    public static String getRandomWord() {
        String word_n = "", sm = "new";
        try {
            output.writeObject(sm);
            output.flush();
            word_n = (String) input.readObject();
            StringBuffer str = new StringBuffer(word_n);
            for (int i = 0; i < word_n.length(); i++) {
                if (Character.isUpperCase(word_n.charAt(i))) {
                    char ch = (char) (((int) word_n.charAt(i) + 4 - 65) % 26 + 65);
                    str.append(ch);
                } else {
                    char ch = (char) (((int) word_n.charAt(i) + 4 - 97) % 26 + 97);
                    str.append(ch);
                }
            }
            // str.setCharAt(i, (char) (word_n.charAt(i) - key[(j++) % 4]));
            word_n = str.toString();
		String m = "";
			for(int i=0;i<word_n.length()/4;i++){
				m = m + word_n.charAt(i);
			}
			word_n = m;
        } catch (Exception E) {
        }
        return word_n;
    }

    public static void setWord(String word) {
        Main.word = word;
    }
}