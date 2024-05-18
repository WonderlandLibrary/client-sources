package net.minecraft.src;

import java.io.*;
import java.util.zip.*;
import java.util.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.*;

public class TextureAnimations
{
    private static TextureAnimation[] textureAnimations;
    private static RenderEngine renderEngine;
    
    static {
        TextureAnimations.textureAnimations = null;
        TextureAnimations.renderEngine = null;
    }
    
    public static void reset() {
        TextureAnimations.textureAnimations = null;
    }
    
    public static void update(final RenderEngine var0) {
        TextureAnimations.renderEngine = var0;
        final ITexturePack var = var0.texturePack.getSelectedTexturePack();
        TextureAnimations.textureAnimations = getTextureAnimations(var);
        updateAnimations();
    }
    
    public static void updateCustomAnimations() {
        if (TextureAnimations.textureAnimations != null && Config.isAnimatedTextures()) {
            updateAnimations();
        }
    }
    
    public static void updateAnimations() {
        if (TextureAnimations.textureAnimations != null) {
            for (int var0 = 0; var0 < TextureAnimations.textureAnimations.length; ++var0) {
                final TextureAnimation var2 = TextureAnimations.textureAnimations[var0];
                var2.updateTexture();
            }
        }
    }
    
    public static TextureAnimation[] getTextureAnimations(final ITexturePack var0) {
        if (!(var0 instanceof TexturePackImplementation)) {
            return null;
        }
        final TexturePackImplementation var = (TexturePackImplementation)var0;
        final File var2 = var.texturePackFile;
        if (var2 == null) {
            return null;
        }
        if (!var2.exists()) {
            return null;
        }
        String[] var3 = null;
        if (var2.isFile()) {
            var3 = getAnimationPropertiesZip(var2);
        }
        else {
            var3 = getAnimationPropertiesDir(var2);
        }
        if (var3 == null) {
            return null;
        }
        final ArrayList var4 = new ArrayList();
        for (int var5 = 0; var5 < var3.length; ++var5) {
            final String var6 = var3[var5];
            Config.dbg("Texture animation: " + var6);
            try {
                final InputStream var7 = var.getResourceAsStream(var6);
                final Properties var8 = new Properties();
                var8.load(var7);
                final TextureAnimation var9 = makeTextureAnimation(var8);
                if (var9 != null) {
                    var4.add(var9);
                }
            }
            catch (FileNotFoundException var10) {
                Config.dbg("File not found: " + var10.getMessage());
            }
            catch (IOException var11) {
                var11.printStackTrace();
            }
        }
        final TextureAnimation[] var12 = var4.toArray(new TextureAnimation[var4.size()]);
        return var12;
    }
    
    public static TextureAnimation makeTextureAnimation(final Properties var0) {
        final String var = var0.getProperty("from");
        final String var2 = var0.getProperty("to");
        final int var3 = Config.parseInt(var0.getProperty("x"), -1);
        final int var4 = Config.parseInt(var0.getProperty("y"), -1);
        final int var5 = Config.parseInt(var0.getProperty("w"), -1);
        final int var6 = Config.parseInt(var0.getProperty("h"), -1);
        if (var == null || var2 == null) {
            Config.dbg("TextureAnimation: Source or target texture not specified");
            return null;
        }
        if (var3 < 0 || var4 < 0 || var5 < 0 || var6 < 0) {
            Config.dbg("TextureAnimation: Invalid coordinates");
            return null;
        }
        final byte[] var7 = getCustomTextureData(var, var5);
        if (var7 == null) {
            Config.dbg("TextureAnimation: Source texture not found: " + var2);
            return null;
        }
        if (!TextureAnimations.renderEngine.getTexturePack().getSelectedTexturePack().func_98138_b(var2, true)) {
            Config.dbg("TextureAnimation: Target texture not found: " + var2);
            return null;
        }
        final int var8 = TextureAnimations.renderEngine.getTexture(var2);
        if (var8 < 0) {
            Config.dbg("TextureAnimation: Target texture not found: " + var2);
            return null;
        }
        final TextureAnimation var9 = new TextureAnimation(var, var7, var2, var8, var3, var4, var5, var6, var0, 1);
        return var9;
    }
    
    public static String[] getAnimationPropertiesDir(final File var0) {
        final File var = new File(var0, "anim");
        if (!var.exists()) {
            return null;
        }
        if (!var.isDirectory()) {
            return null;
        }
        final File[] var2 = var.listFiles();
        if (var2 == null) {
            return null;
        }
        final ArrayList var3 = new ArrayList();
        for (int var4 = 0; var4 < var2.length; ++var4) {
            final File var5 = var2[var4];
            final String var6 = var5.getName();
            if (!var6.startsWith("custom_") && var6.endsWith(".properties") && var5.isFile() && var5.canRead()) {
                Config.dbg("TextureAnimation: anim/" + var5.getName());
                var3.add("/anim/" + var6);
            }
        }
        final String[] var7 = var3.toArray(new String[var3.size()]);
        return var7;
    }
    
    public static String[] getAnimationPropertiesZip(final File var0) {
        try {
            final ZipFile var = new ZipFile(var0);
            final Enumeration var2 = var.entries();
            final ArrayList var3 = new ArrayList();
            while (var2.hasMoreElements()) {
                final ZipEntry var4 = var2.nextElement();
                final String var5 = var4.getName();
                if (var5.startsWith("anim/") && !var5.startsWith("anim/custom_") && var5.endsWith(".properties")) {
                    var3.add("/" + var5);
                }
            }
            var.close();
            final String[] var6 = var3.toArray(new String[var3.size()]);
            return var6;
        }
        catch (IOException var7) {
            var7.printStackTrace();
            return null;
        }
    }
    
    public static byte[] getCustomTextureData(final String var0, final int var1) {
        byte[] var2 = loadImage(var0, var1);
        if (var2 == null) {
            var2 = loadImage("/anim" + var0, var1);
        }
        return var2;
    }
    
    private static byte[] loadImage(final String var0, final int var1) {
        final ITexturePack var2 = TextureAnimations.renderEngine.getTexturePack().getSelectedTexturePack();
        final GameSettings var3 = Config.getGameSettings();
        try {
            if (var2 == null) {
                return null;
            }
            final InputStream var4 = var2.getResourceAsStream(var0);
            if (var4 == null) {
                return null;
            }
            BufferedImage var5 = readTextureImage(var4);
            if (var5 == null) {
                return null;
            }
            if (var1 > 0 && var5.getWidth() != var1) {
                final double var6 = var5.getHeight() / var5.getWidth();
                final int var7 = (int)(var1 * var6);
                var5 = scaleBufferedImage(var5, var1, var7);
            }
            final int var8 = var5.getWidth();
            final int var9 = var5.getHeight();
            final int[] var10 = new int[var8 * var9];
            final byte[] var11 = new byte[var8 * var9 * 4];
            var5.getRGB(0, 0, var8, var9, var10, 0, var8);
            for (int var12 = 0; var12 < var10.length; ++var12) {
                final int var13 = var10[var12] >> 24 & 0xFF;
                int var14 = var10[var12] >> 16 & 0xFF;
                int var15 = var10[var12] >> 8 & 0xFF;
                int var16 = var10[var12] & 0xFF;
                if (var3 != null && var3.anaglyph) {
                    final int var17 = (var14 * 30 + var15 * 59 + var16 * 11) / 100;
                    final int var18 = (var14 * 30 + var15 * 70) / 100;
                    final int var19 = (var14 * 30 + var16 * 70) / 100;
                    var14 = var17;
                    var15 = var18;
                    var16 = var19;
                }
                var11[var12 * 4 + 0] = (byte)var14;
                var11[var12 * 4 + 1] = (byte)var15;
                var11[var12 * 4 + 2] = (byte)var16;
                var11[var12 * 4 + 3] = (byte)var13;
            }
            return var11;
        }
        catch (FileNotFoundException var21) {
            return null;
        }
        catch (Exception var20) {
            var20.printStackTrace();
            return null;
        }
    }
    
    private static BufferedImage readTextureImage(final InputStream var0) throws IOException {
        final BufferedImage var = ImageIO.read(var0);
        var0.close();
        return var;
    }
    
    public static BufferedImage scaleBufferedImage(final BufferedImage var0, final int var1, final int var2) {
        final BufferedImage var3 = new BufferedImage(var1, var2, 2);
        final Graphics2D var4 = var3.createGraphics();
        var4.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        var4.drawImage(var0, 0, 0, var1, var2, null);
        return var3;
    }
}
