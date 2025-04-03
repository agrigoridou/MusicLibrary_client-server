//3212020048 Grigoridou Athanasia
package shared;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    private String action;
    private String title;
    private Album album;

    public ClientRequest(String action, Album album) {
        this.action = action;
        this.album = album;
    }

    public ClientRequest(String action, String title) {
        this.action = action;
        this.title = title;
    }

    public String getAction() {
        return action;
    }

    public String getTitle() {
        return title;
    }

    public Album getAlbum() {
        return album;
    }
}