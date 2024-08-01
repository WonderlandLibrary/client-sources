package wtf.diablo.client.spotify;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import wtf.diablo.client.core.impl.Diablo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;

public class SpotifyImageLocation {
    private final IPlaylistItem item;
    private ResourceLocation location;
    private final File directory;
    private final SpotifyApi api;
    private final File file;

    public SpotifyImageLocation(IPlaylistItem item, SpotifyApi api) {
        this.item = item;
        this.directory = new File(Diablo.getInstance().getMainDirectory(), "spotify");
        this.file = new File(directory, item.getId() + ".png");
        this.api = api;
    }

    public ResourceLocation getLocation() {
        if (location == null) {
            if (!directory.exists())
                directory.mkdirs();

            if (!file.exists()) {
                try {
                    URL url = new URL(api.getTrack(item.getId()).build().executeAsync().get().getAlbum().getImages()[2].getUrl());
                    InputStream is = url.openStream();
                    OutputStream os = Files.newOutputStream(file.toPath());

                    byte[] b = new byte[2048];
                    int length;

                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }

                    is.close();
                    os.close();

                    location = new ResourceLocation(item.getId());
                    BufferedImage bufferedImage = ImageIO.read(file);
                    Minecraft.getMinecraft().getTextureManager().loadTexture(location, new DynamicTexture(bufferedImage));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return location;
    }
}
