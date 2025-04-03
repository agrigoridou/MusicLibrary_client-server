//3212020048 Grigoridou Athanasia
package shared;

import java.io.Serializable;

public class ServerResponse implements Serializable {
    private String message;
    private Album album;

    public ServerResponse(String message) {
        this.message = message;
    }

    public ServerResponse(Album album) {
        this.album = album;
    }

    public ServerResponse(String message, Album album) {
        this.message = message;
        this.album = album;
    }


    public String getMessage() {
        return message;
    }

    public Album getAlbum() {
        return album;
    }
}