package wtf.diablo.client.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;

import java.util.HashMap;

public class SpotifyImageManager {
    private final HashMap<IPlaylistItem,SpotifyImageLocation> locations = new HashMap<>();
    private final SpotifyApi api;

    public SpotifyImageManager(SpotifyApi api) {
        this.api = api;
    }

    public SpotifyImageLocation getImage(IPlaylistItem item) {
        if (!locations.containsKey(item)) {
            locations.put(item, new SpotifyImageLocation(item,api));
        }
        return locations.get(item);
    }
}
