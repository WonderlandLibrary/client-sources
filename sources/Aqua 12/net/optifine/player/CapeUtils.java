// 
// Decompiled by Procyon v0.5.36
// 

package net.optifine.player;

import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.src.Config;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import java.io.File;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import intent.AquaDev.aqua.gui.novolineOld.themesScreen.ThemeScreen;
import net.minecraft.client.entity.AbstractClientPlayer;
import java.util.regex.Pattern;

public class CapeUtils
{
    private static final Pattern PATTERN_USERNAME;
    
    public static void downloadCape(final AbstractClientPlayer player) {
        final String s = player.getNameClear();
        if (s != null && !s.isEmpty()) {
            final String s2 = ThemeScreen.themeHero ? "https://i.imgur.com/qGpDLqJ.png" : "";
            final String s3 = ThemeScreen.themeHero ? "https://i.imgur.com/qGpDLqJ.png" : "";
            final ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s3);
            final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            final ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
            if (itextureobject != null && itextureobject instanceof ThreadDownloadImageData) {
                final ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)itextureobject;
                if (threaddownloadimagedata.imageFound != null) {
                    if (threaddownloadimagedata.imageFound) {
                        player.setLocationOfCape(resourcelocation);
                    }
                    return;
                }
            }
            final IImageBuffer iimagebuffer = new IImageBuffer() {
                ImageBufferDownload ibd = new ImageBufferDownload();
                
                @Override
                public BufferedImage parseUserSkin(final BufferedImage image) {
                    return CapeUtils.parseCape(image);
                }
                
                @Override
                public void skinAvailable() {
                    player.setLocationOfCape(resourcelocation);
                }
            };
            final ThreadDownloadImageData threaddownloadimagedata2 = new ThreadDownloadImageData(null, s2, null, iimagebuffer);
            threaddownloadimagedata2.pipeline = true;
            texturemanager.loadTexture(resourcelocation, threaddownloadimagedata2);
        }
    }
    
    public static BufferedImage parseCape(final BufferedImage img) {
        int i = 64;
        int j = 32;
        for (int k = img.getWidth(), l = img.getHeight(); i < k || j < l; i *= 2, j *= 2) {}
        final BufferedImage bufferedimage = new BufferedImage(i, j, 2);
        final Graphics graphics = bufferedimage.getGraphics();
        graphics.drawImage(img, 0, 0, null);
        graphics.dispose();
        return bufferedimage;
    }
    
    public static boolean isElytraCape(final BufferedImage imageRaw, final BufferedImage imageFixed) {
        return imageRaw.getWidth() > imageFixed.getHeight();
    }
    
    public static void reloadCape(final AbstractClientPlayer player) {
        final String s = player.getNameClear();
        final ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s);
        final TextureManager texturemanager = Config.getTextureManager();
        final ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
        if (itextureobject instanceof SimpleTexture) {
            final SimpleTexture simpletexture = (SimpleTexture)itextureobject;
            simpletexture.deleteGlTexture();
            texturemanager.deleteTexture(resourcelocation);
        }
        player.setLocationOfCape(null);
        player.setElytraOfCape(false);
        downloadCape(player);
    }
    
    static {
        PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");
    }
}
