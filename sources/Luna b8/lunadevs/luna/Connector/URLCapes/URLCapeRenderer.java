package lunadevs.luna.Connector.URLCapes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * 
 * @author Timothy on 2017-06-21 [TIME: 19:46]
 *
 */
public class URLCapeRenderer {

    private static Map <UUID, ResourceLocation> capes = new HashMap();

    public static void loadCape(final UUID uuid /** Using UUID's except for Usernames, Because if you change Username it will be gone, but with your UUID it wont. */) {
        String url = "https://lunaclient.pw/api/cape/" + uuid.toString() + ".png"; /** Connect to the URL where all the capes are stored. */

        final ResourceLocation resourceLocation = new ResourceLocation("capes/" + uuid.toString() + ".png"); /** Connecting to /capes/<UUID>.png. Only supports .PNG at the moment */
        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();

        IImageBuffer iImageBuffer = new IImageBuffer() {
            @Override
            public BufferedImage parseUserSkin(BufferedImage var1) { /** Apply the UUID's [Players] Cape */
                return var1;
            }

            @Override
            public void func_152634_a() {
                capes.put(uuid, resourceLocation);
            }
        };

        ThreadDownloadImageData threadDownloadImageData = new ThreadDownloadImageData((File) null, url, (ResourceLocation) null, iImageBuffer);
        textureManager.loadTexture(resourceLocation, threadDownloadImageData);
    }

    public static void deleteCape(UUID uuid) {
        capes.remove(uuid);
    }

    public static ResourceLocation getCape(UUID uuid) {
        return capes.containsKey(uuid) ? capes.get(uuid) : null;
    }

    public static boolean hasCape(UUID uuid) {
        return capes.containsKey(uuid);
    }
}