//3212020048 Grigoridou Athanasia

import shared.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 12345;
    private static MusicLibrary musicLibrary = new MusicLibrary(); // Δημιουργία αντικειμένου MusicLibrary

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is waiting for client requests...");

            // Εξυπηρέτηση ενός πελάτη κάθε φορά
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected!");

                    // Ροή για λήψη και αποστολή αντικειμένων
                    ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());

                    // Διαβάζει το αίτημα από τον πελάτη
                    ClientRequest request = (ClientRequest) input.readObject();
                    System.out.println("Received request: " + request.getAction());

                    // Επεξεργασία του αιτήματος και δημιουργία της απάντησης
                    ServerResponse response = processRequest(request);

                    // Αποστολή της απάντησης στον πελάτη
                    output.writeObject(response);
                    output.flush();
                    System.out.println("Response sent to client.");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ServerResponse processRequest(ClientRequest request) {
        switch (request.getAction()) {
            case "ADD":
                // Προσθήκη άλμπουμ στη βιβλιοθήκη
                Album album = request.getAlbum();
                musicLibrary.addAlbum(album);
                return new ServerResponse("Album added successfully!");
            case "GET":
                // Αναζήτηση άλμπουμ
                album = musicLibrary.getAlbum(request.getTitle());
                if (album != null) {
                    return new ServerResponse(album);
                } else {
                    return new ServerResponse("Album not found.");
                }
            case "DELETE":
                // Διαγραφή άλμπουμ
                boolean deleted = musicLibrary.removeAlbum(request.getTitle());
                return new ServerResponse(deleted ? "Album deleted successfully!" : "Album not found.");
            case "UPDATE":
                // Ενημέρωση άλμπουμ
                boolean updated = musicLibrary.updateAlbum(request.getAlbum());
                return new ServerResponse(updated ? "Album updated successfully!" : "Album not found.");
            default:
                return new ServerResponse("Invalid action.");
        }
    }

}