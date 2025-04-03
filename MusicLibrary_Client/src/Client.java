//3212020048 Grigoridou Athanasia
import shared.*;
import java.io.*;
import java.net.Socket;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public ServerResponse sendRequest(ClientRequest request) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            // Αποστολή αιτήματος στον εξυπηρετητή
            output.writeObject(request);
            output.flush();

            // Λήψη της απάντησης από τον εξυπηρετητή
            return (ServerResponse) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ServerResponse("Error communicating with server");
        }
    }

    public static void main(String[] args) {
        // Ξεκινάει το γραφικό περιβάλλον
        new MusicLibraryGUI();
    }
}