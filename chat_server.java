import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class chat_server {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection; // for client using TCP communication
    private ServerSocket server; // listen for clients
    private int totalClients = 50;
    private int port = 3287;

    private static final String[] words = {
            "SENTENCE","LINE","MAN","BOY","FARM","LETTER","POINT","MOTHER","AMERICA","FATHER","TREE",
            "CITY","EXAMPLE","PAPER","SECOND","DIFFERENT","HARD","IMPORTANT","WHITE",
            "TOGETHER","ALWAYS","BELOW","NEVER",
            "STRUCTURE","HONEST","CURTAIN","CLAY","FACTORY","INVASION","FREE","COLOURFUL","LEADERSHIP",
            "PROPOSAL","COMPUTER","MONITOR","COW","RAIN","BOOK","BOTH","YOUNG","BOLD",
            "ABOVE","BELOW","SCHOOL","COLLEGE","OFFICE","INTERNSHIP","NOTEBOOK",
            "EXAM","SEMESTER","SUMMER","WINTER","HOLIDAYS","VACATION"
            ,"SOPHOMORE","FRESHER","TECHNOLOGY","MECHANICS","PHYSICS",
            "BIOLOGY","ENGLISH","SCIENCE","MATHEMATICS","ENGINEERING",
            "MEDICINE","LAW","RESEARCH","FEST","CULTURE","ORGANISATION",
            "INTERNAL","EXTERNAL","PRATICALS","LABS","RECORD","MUSIC",
            "ENTERTAINMENT","ICE CREAM","HARRY POTTER","CORAL REEF",
            "ELON MUSK","ABDUL KALAM","COMPUTER NETWORK","ROUTER","CLASS","FREE HOUR","LUNCH","HOSTEL"
    };

    public void startRunning() {
        try {
            server = new ServerSocket(port, totalClients);
            while (true) {
                try {
                    connection = server.accept(); // establishes connection and waits for the client
                    output = new ObjectOutputStream(connection.getOutputStream());
                    output.flush();
                    input = new ObjectInputStream(connection.getInputStream());

                    whileChatting();
                } catch (EOFException eofException) {
                }

            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void whileChatting() throws IOException {
        String message_r = "", message_s = "";
        String request1 = "new";

        do {
            try {
                message_r = (String) input.readObject();
                if (message_r.equals(request1)) {
                    message_s = words[(int) (Math.random() * words.length)];
                    StringBuffer str = new StringBuffer(message_s);
                    for (int i = 0; i < message_s.length(); i++) {
                        if (Character.isUpperCase(message_s.charAt(i))) {
                            char ch = (char) (((int) message_s.charAt(i) +  4 - 65) % 26 + 65);
                            str.append(ch);
                        } else {
                            char ch = (char) (((int) message_s.charAt(i) + 4 - 97) % 26 + 97);
                            str.append(ch);
                        }
                    }
                    // str.setCharAt(i, (char) (message_s.charAt(i) + key[(j++) % 4]));
                    message_s = str.toString();
			//String m = "";
			//for(int i=0;i<message_s.length()/4;i++){
			//	m = m + message_s.charAt(i);
			//}
			//message_s = m;
                    output.writeObject(message_s);
                }
            } catch (ClassNotFoundException classNotFoundException) {
            }
        } while (true);

    }
}
