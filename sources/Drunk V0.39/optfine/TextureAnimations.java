/*
 * Decompiled with CFR 0.152.
 */
package optfine;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import net.minecraft.client.resources.AbstractResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import optfine.Config;
import optfine.ResourceUtils;
import optfine.TextureAnimation;
import optfine.TextureUtils;

public class TextureAnimations {
    private static TextureAnimation[] textureAnimations = null;

    public static void reset() {
        textureAnimations = null;
    }

    public static void update() {
        textureAnimations = null;
        IResourcePack[] airesourcepack = Config.getResourcePacks();
        textureAnimations = TextureAnimations.getTextureAnimations(airesourcepack);
        if (!Config.isAnimatedTextures()) return;
        TextureAnimations.updateAnimations();
    }

    public static void updateCustomAnimations() {
        if (textureAnimations == null) return;
        if (!Config.isAnimatedTextures()) return;
        TextureAnimations.updateAnimations();
    }

    public static void updateAnimations() {
        if (textureAnimations == null) return;
        int i = 0;
        while (i < textureAnimations.length) {
            TextureAnimation textureanimation = textureAnimations[i];
            textureanimation.updateTexture();
            ++i;
        }
    }

    public static TextureAnimation[] getTextureAnimations(IResourcePack[] p_getTextureAnimations_0_) {
        ArrayList<TextureAnimation> list = new ArrayList<TextureAnimation>();
        int i = 0;
        while (i < p_getTextureAnimations_0_.length) {
            IResourcePack iresourcepack = p_getTextureAnimations_0_[i];
            TextureAnimation[] atextureanimation = TextureAnimations.getTextureAnimations(iresourcepack);
            if (atextureanimation != null) {
                list.addAll(Arrays.asList(atextureanimation));
            }
            ++i;
        }
        return list.toArray(new TextureAnimation[list.size()]);
    }

    public static TextureAnimation[] getTextureAnimations(IResourcePack p_getTextureAnimations_0_) {
        if (!(p_getTextureAnimations_0_ instanceof AbstractResourcePack)) {
            return null;
        }
        AbstractResourcePack abstractresourcepack = (AbstractResourcePack)p_getTextureAnimations_0_;
        File file1 = ResourceUtils.getResourcePackFile(abstractresourcepack);
        if (file1 == null) {
            return null;
        }
        if (!file1.exists()) {
            return null;
        }
        String[] astring = null;
        astring = file1.isFile() ? TextureAnimations.getAnimationPropertiesZip(file1) : TextureAnimations.getAnimationPropertiesDir(file1);
        if (astring == null) {
            return null;
        }
        ArrayList<TextureAnimation> list = new ArrayList<TextureAnimation>();
        int i = 0;
        while (i < astring.length) {
            String s = astring[i];
            Config.dbg("Texture animation: " + s);
            try {
                ResourceLocation resourcelocation = new ResourceLocation(s);
                InputStream inputstream = p_getTextureAnimations_0_.getInputStream(resourcelocation);
                Properties properties = new Properties();
                properties.load(inputstream);
                TextureAnimation textureanimation = TextureAnimations.makeTextureAnimation(properties, resourcelocation);
                if (textureanimation != null) {
                    ResourceLocation resourcelocation1 = new ResourceLocation(textureanimation.getDstTex());
                    if (Config.getDefiningResourcePack(resourcelocation1) != p_getTextureAnimations_0_) {
                        Config.dbg("Skipped: " + s + ", target texture not loaded from same resource pack");
                    } else {
                        list.add(textureanimation);
                    }
                }
            }
            catch (FileNotFoundException filenotfoundexception) {
                Config.warn("File not found: " + filenotfoundexception.getMessage());
            }
            catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
            ++i;
        }
        return list.toArray(new TextureAnimation[list.size()]);
    }

    public static TextureAnimation makeTextureAnimation(Properties p_makeTextureAnimation_0_, ResourceLocation p_makeTextureAnimation_1_) {
        String s = p_makeTextureAnimation_0_.getProperty("from");
        String s1 = p_makeTextureAnimation_0_.getProperty("to");
        int i = Config.parseInt(p_makeTextureAnimation_0_.getProperty("x"), -1);
        int j = Config.parseInt(p_makeTextureAnimation_0_.getProperty("y"), -1);
        int k = Config.parseInt(p_makeTextureAnimation_0_.getProperty("w"), -1);
        int l = Config.parseInt(p_makeTextureAnimation_0_.getProperty("h"), -1);
        if (s != null && s1 != null) {
            if (i >= 0 && j >= 0 && k >= 0 && l >= 0) {
                String s2 = TextureUtils.getBasePath(p_makeTextureAnimation_1_.getResourcePath());
                s = TextureUtils.fixResourcePath(s, s2);
                s1 = TextureUtils.fixResourcePath(s1, s2);
                byte[] abyte = TextureAnimations.getCustomTextureData(s, k);
                if (abyte == null) {
                    Config.warn("TextureAnimation: Source texture not found: " + s1);
                    return null;
                }
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                if (Config.hasResource(resourcelocation)) return new TextureAnimation(s, abyte, s1, resourcelocation, i, j, k, l, p_makeTextureAnimation_0_, 1);
                Config.warn("TextureAnimation: Target texture not found: " + s1);
                return null;
            }
            Config.warn("TextureAnimation: Invalid coordinates");
            return null;
        }
        Config.warn("TextureAnimation: Source or target texture not specified");
        return null;
    }

    public static String[] getAnimationPropertiesDir(File p_getAnimationPropertiesDir_0_) {
        File file1 = new File(p_getAnimationPropertiesDir_0_, "anim");
        if (!file1.exists()) {
            return null;
        }
        if (!file1.isDirectory()) {
            return null;
        }
        File[] afile = file1.listFiles();
        if (afile == null) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>();
        int i = 0;
        while (i < afile.length) {
            File file2 = afile[i];
            String s = file2.getName();
            if (!s.startsWith("custom_") && s.endsWith(".properties") && file2.isFile() && file2.canRead()) {
                Config.dbg("TextureAnimation: anim/" + file2.getName());
                list.add("/anim/" + s);
            }
            ++i;
        }
        return list.toArray(new String[list.size()]);
    }

    public static String[] getAnimationPropertiesZip(File p_getAnimationPropertiesZip_0_) {
        try {
            ZipFile zipfile = new ZipFile(p_getAnimationPropertiesZip_0_);
            Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
            ArrayList<String> list = new ArrayList<String>();
            while (true) {
                if (!enumeration.hasMoreElements()) {
                    return list.toArray(new String[list.size()]);
                }
                ZipEntry zipentry = enumeration.nextElement();
                String s = zipentry.getName();
                if (!s.startsWith("assets/minecraft/mcpatcher/anim/") || s.startsWith("assets/minecraft/mcpatcher/anim/custom_") || !s.endsWith(".properties")) continue;
                String s1 = "assets/minecraft/";
                s = s.substring(s1.length());
                list.add(s);
            }
        }
        catch (IOException ioexception) {
            ioexception.printStackTrace();
            return null;
        }
    }

    public static byte[] getCustomTextureData(String p_getCustomTextureData_0_, int p_getCustomTextureData_1_) {
        byte[] abyte = TextureAnimations.loadImage(p_getCustomTextureData_0_, p_getCustomTextureData_1_);
        if (abyte != null) return abyte;
        return TextureAnimations.loadImage("/anim" + p_getCustomTextureData_0_, p_getCustomTextureData_1_);
    }

    private static byte[] loadImage(String p_loadImage_0_, int p_loadImage_1_) {
        GameSettings gamesettings = Config.getGameSettings();
        try {
            ResourceLocation resourcelocation = new ResourceLocation(p_loadImage_0_);
            InputStream inputstream = Config.getResourceStream(resourcelocation);
            if (inputstream == null) {
                return null;
            }
            BufferedImage bufferedimage = TextureAnimations.readTextureImage(inputstream);
            if (bufferedimage == null) {
                return null;
            }
            if (p_loadImage_1_ > 0 && bufferedimage.getWidth() != p_loadImage_1_) {
                double d0 = bufferedimage.getHeight() / bufferedimage.getWidth();
                int j = (int)((double)p_loadImage_1_ * d0);
                bufferedimage = TextureAnimations.scaleBufferedImage(bufferedimage, p_loadImage_1_, j);
            }
            int k2 = bufferedimage.getWidth();
            int i = bufferedimage.getHeight();
            int[] aint = new int[k2 * i];
            byte[] abyte = new byte[k2 * i * 4];
            bufferedimage.getRGB(0, 0, k2, i, aint, 0, k2);
            int k = 0;
            while (k < aint.length) {
                int l = aint[k] >> 24 & 0xFF;
                int i1 = aint[k] >> 16 & 0xFF;
                int j1 = aint[k] >> 8 & 0xFF;
                int k1 = aint[k] & 0xFF;
                if (gamesettings != null && gamesettings.anaglyph) {
                    int l1 = (i1 * 30 + j1 * 59 + k1 * 11) / 100;
                    int i2 = (i1 * 30 + j1 * 70) / 100;
                    int j2 = (i1 * 30 + k1 * 70) / 100;
                    i1 = l1;
                    j1 = i2;
                    k1 = j2;
                }
                abyte[k * 4 + 0] = (byte)i1;
                abyte[k * 4 + 1] = (byte)j1;
                abyte[k * 4 + 2] = (byte)k1;
                abyte[k * 4 + 3] = (byte)l;
                ++k;
            }
            return abyte;
        }
        catch (FileNotFoundException var18) {
            return null;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static BufferedImage readTextureImage(InputStream p_readTextureImage_0_) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(p_readTextureImage_0_);
        p_readTextureImage_0_.close();
        return bufferedimage;
    }

    public static BufferedImage scaleBufferedImage(BufferedImage p_scaleBufferedImage_0_, int p_scaleBufferedImage_1_, int p_scaleBufferedImage_2_) {
        BufferedImage bufferedimage = new BufferedImage(p_scaleBufferedImage_1_, p_scaleBufferedImage_2_, 2);
        Graphics2D graphics2d = bufferedimage.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.drawImage(p_scaleBufferedImage_0_, 0, 0, p_scaleBufferedImage_1_, p_scaleBufferedImage_2_, null);
        return bufferedimage;
    }
}

