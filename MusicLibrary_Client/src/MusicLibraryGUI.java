//3212020048 Grigoridou Athanasia
import shared.*;
import javax.swing.*;
import java.awt.*;

public class MusicLibraryGUI {
    private final Client client = new Client();

    private final JTextField titleField = new JTextField("Enter album title", 20);
    private final JTextField descriptionField = new JTextField("Enter album description", 20);
    private final JTextField genreField = new JTextField("Enter genre (e.g., Rock, Pop)", 20);
    private final JTextField yearField = new JTextField("Enter release year (e.g., 2020)", 20);
    private final JTextArea songsArea = new JTextArea("Title, Artist, Duration (e.g., Song1, Artist1, 3.5)", 5, 20);
    private final JLabel responseLabel = new JLabel("Response: ");
    private final JTextField searchTitleField = new JTextField(20); // Πεδίο αναζήτησης τίτλου άλμπουμ

    public MusicLibraryGUI() {
        JFrame frame = new JFrame("Music Library Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Αναζήτηση άλμπουμ
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Album"));
        searchPanel.add(new JLabel("Enter album title to search:"));
        searchPanel.add(searchTitleField); // Προσθήκη πεδίου για τον τίτλο αναζήτησης
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> handleSearchAlbum());
        searchPanel.add(searchButton);
        searchPanel.add(responseLabel);
        panel.add(searchPanel);

        // Περιοχή προσθήκης άλμπουμ
        panel.add(createAlbumFormPanel());

        // Κουμπιά ενεργειών
        panel.add(createButtonPanel());

        frame.add(panel);
        frame.setVisible(true);
    }

    private JPanel createAlbumFormPanel() {
        JPanel albumPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        albumPanel.setBorder(BorderFactory.createTitledBorder("Album Details"));

        albumPanel.add(createLabeledField("Album Title:", titleField));
        albumPanel.add(createLabeledField("Description:", descriptionField));
        albumPanel.add(createLabeledField("Genre:", genreField));
        albumPanel.add(createLabeledField("Year:", yearField));

        JScrollPane scrollPane = new JScrollPane(songsArea);
        songsArea.setBorder(BorderFactory.createTitledBorder("Songs (Title, Artist, Duration)"));
        songsArea.setToolTipText("Enter songs in format: Title, Artist, Duration (e.g., Hello, Adele, 4.2)");
        albumPanel.add(scrollPane);

        return albumPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add Album");
        addButton.addActionListener(e -> handleAddAlbum());
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("Delete Album");
        deleteButton.addActionListener(e -> handleDeleteAlbum());
        buttonPanel.add(deleteButton);

        JButton updateButton = new JButton("Update Album");
        updateButton.addActionListener(e -> handleUpdateAlbum());
        buttonPanel.add(updateButton);

        return buttonPanel;
    }

    private JPanel createLabeledField(String label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(textField);
        return panel;
    }

    private void handleSearchAlbum() {
        String title = searchTitleField.getText().trim(); // Αναζήτηση μέσω του πεδίου searchTitleField
        if (title.isEmpty()) {
            responseLabel.setText("Response: Please enter an album title to search.");
            return;
        }

        // Αποστολή αιτήματος για αναζήτηση άλμπουμ
        ServerResponse response = client.sendRequest(new ClientRequest("GET", title));

        // Ελέγξτε αν το άλμπουμ βρέθηκε
        Album album = response.getAlbum();

        if (album != null) {
            System.out.println("Album found: " + album.getTitle());  // Εκτύπωση για έλεγχο
            populateFields(album);
            responseLabel.setText("Response: Album found!");
        } else {
            clearFields();
            responseLabel.setText("Response: Album not found.");
        }
    }


    private void handleAddAlbum() {
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        String genre = genreField.getText().trim();
        String yearText = yearField.getText().trim();
        String songsText = songsArea.getText().trim();

        if (title.isEmpty() || title.equals("Enter album title")) {
            responseLabel.setText("Response: Please enter an album title.");
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            responseLabel.setText("Response: Invalid year format.");
            return;
        }

        String[] songLines = songsText.split("\n");
        java.util.List<Song> songs = new java.util.ArrayList<>();
        for (String line : songLines) {
            String[] parts = line.split(", ");
            if (parts.length != 3) {
                responseLabel.setText("Response: Invalid song format.");
                return;
            }
            try {
                songs.add(new Song(parts[0], parts[1], Double.parseDouble(parts[2])));
            } catch (NumberFormatException e) {
                responseLabel.setText("Response: Invalid duration format.");
                return;
            }
        }

        Album album = new Album(title, description, genre, year, songs);
        ServerResponse response = client.sendRequest(new ClientRequest("POST", album));

        responseLabel.setText("Response: " + response.getMessage());
    }

    private void handleDeleteAlbum() {
        String title = titleField.getText().trim();
        if (title.isEmpty() || title.equals("Enter album title")) {
            responseLabel.setText("Response: Please enter an album title to delete.");
            return;
        }

        ServerResponse response = client.sendRequest(new ClientRequest("DELETE", title));
        responseLabel.setText("Response: " + response.getMessage());
        clearFields();
    }

    private void handleUpdateAlbum() {
        String title = titleField.getText().trim();
        if (title.isEmpty() || title.equals("Enter album title")) {
            responseLabel.setText("Response: Please enter an album title to update.");
            return;
        }

        handleAddAlbum();
    }

    private void clearFields() {
        descriptionField.setText("Enter album description");
        genreField.setText("Enter genre (e.g., Rock, Pop)");
        yearField.setText("Enter release year (e.g., 2020)");
        songsArea.setText("Title, Artist, Duration (e.g., Song1, Artist1, 3.5)");
        searchTitleField.setText(""); // Καθαρισμός πεδίου αναζήτησης
    }

    private void populateFields(Album album) {
        titleField.setText(album.getTitle());
        descriptionField.setText(album.getDescription());
        genreField.setText(album.getGenre());
        yearField.setText(String.valueOf(album.getYear()));

        StringBuilder songsText = new StringBuilder();
        for (Song song : album.getSongs()) {
            songsText.append(song.getTitle()).append(", ")
                    .append(song.getArtist()).append(", ")
                    .append(song.getDuration()).append("\n");
        }
        songsArea.setText(songsText.toString());
    }
}