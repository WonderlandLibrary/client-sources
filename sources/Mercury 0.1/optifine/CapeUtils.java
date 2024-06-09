/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FilenameUtils;

public class CapeUtils {
    public static void downloadCape(final AbstractClientPlayer player) {
        String username = player.getNameClear();
        if (username != null && !username.isEmpty()) {
            String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
            String mptHash = FilenameUtils.getBaseName(ofCapeUrl);
            final ResourceLocation rl2 = new ResourceLocation("capeof/" + mptHash);
            TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            ITextureObject tex = textureManager.getTexture(rl2);
            if (tex != null && tex instanceof ThreadDownloadImageData) {
                ThreadDownloadImageData thePlayer = (ThreadDownloadImageData)tex;
                if (thePlayer.imageFound != null) {
                    if (thePlayer.imageFound.booleanValue()) {
                        player.setLocationOfCape(rl2);
                    }
                    return;
                }
            }
            IImageBuffer iib = new IImageBuffer(){
                ImageBufferDownload ibd = new ImageBufferDownload();

                @Override
                public BufferedImage parseUserSkin(BufferedImage var1) {
                    return CapeUtils.parseCape(var1);
                }

                @Override
                public void func_152634_a() {
                    player.setLocationOfCape(rl2);
                }
            };
            ThreadDownloadImageData textureCape = new ThreadDownloadImageData(null, ofCapeUrl, null, iib);
            textureCape.pipeline = true;
            textureManager.loadTexture(rl2, textureCape);
        }
    }

    public static BufferedImage parseCape(BufferedImage img) {
        int imageHeight;
        int imageWidth = 64;
        int srcWidth = img.getWidth();
        int srcHeight = img.getHeight();
        for (imageHeight = 32; imageWidth < srcWidth || imageHeight < srcHeight; imageWidth *= 2, imageHeight *= 2) {
        }
        BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
        Graphics g2 = imgNew.getGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        return imgNew;
    }

}

