//3212020048 Grigoridou Athanasia
package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable {
    private String title;
    private String description;
    private String genre;
    private int year;
    private List<Song> songs;

    public Album(String title, String description, String genre, int year, List<Song> songs) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public List<Song> getSongs() {
        return songs;
    }

    @Override
    public String toString() {
        return title + " (" + year + "), Genre: " + genre + ", Songs: " + songs;
    }

    public String getSongsAsString() {
        StringBuilder sb = new StringBuilder();
        for (Song song : songs) {
            sb.append(song.toString()).append("\n");
        }
        return sb.toString();
    }
}