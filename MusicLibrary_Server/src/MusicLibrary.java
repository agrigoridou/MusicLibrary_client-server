//3212020048 Grigoridou Athanasia
import shared.Album;
import java.util.HashMap;
import java.util.Map;

public class MusicLibrary {
    private Map<String, Album> albums;

    public MusicLibrary() {
        this.albums = new HashMap<>();
    }

    public void addAlbum(Album album) {
        albums.put(album.getTitle(), album);
    }

    public Album getAlbum(String title) {
        return albums.get(title);
    }

    public boolean removeAlbum(String title) {
        return albums.remove(title) != null;
    }

    public boolean updateAlbum(Album album) {
        if (albums.containsKey(album.getTitle())) {
            albums.put(album.getTitle(), album);
            return true;
        }
        return false;
    }
}