/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import javax.imageio.ImageIO;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.ResUtils;
import optifine.TextureAnimation;
import optifine.TextureUtils;

public class TextureAnimations {
    private static TextureAnimation[] textureAnimations = null;

    public static void reset() {
        textureAnimations = null;
    }

    public static void update() {
        textureAnimations = null;
        IResourcePack[] rps = Config.getResourcePacks();
        textureAnimations = TextureAnimations.getTextureAnimations(rps);
        if (Config.isAnimatedTextures()) {
            TextureAnimations.updateAnimations();
        }
    }

    public static void updateCustomAnimations() {
        if (textureAnimations != null && Config.isAnimatedTextures()) {
            TextureAnimations.updateAnimations();
        }
    }

    public static void updateAnimations() {
        if (textureAnimations != null) {
            for (int i2 = 0; i2 < textureAnimations.length; ++i2) {
                TextureAnimation anim = textureAnimations[i2];
                anim.updateTexture();
            }
        }
    }

    public static TextureAnimation[] getTextureAnimations(IResourcePack[] rps) {
        ArrayList<TextureAnimation> list = new ArrayList<TextureAnimation>();
        for (int anims = 0; anims < rps.length; ++anims) {
            IResourcePack rp2 = rps[anims];
            TextureAnimation[] tas = TextureAnimations.getTextureAnimations(rp2);
            if (tas == null) continue;
            list.addAll(Arrays.asList(tas));
        }
        TextureAnimation[] var5 = list.toArray(new TextureAnimation[list.size()]);
        return var5;
    }

    public static TextureAnimation[] getTextureAnimations(IResourcePack rp2) {
        String[] animPropNames = ResUtils.collectFiles(rp2, "mcpatcher/anim", ".properties", null);
        if (animPropNames.length <= 0) {
            return null;
        }
        ArrayList<TextureAnimation> list = new ArrayList<TextureAnimation>();
        for (int anims = 0; anims < animPropNames.length; ++anims) {
            String propName = animPropNames[anims];
            Config.dbg("Texture animation: " + propName);
            try {
                ResourceLocation e2 = new ResourceLocation(propName);
                InputStream in2 = rp2.getInputStream(e2);
                Properties props = new Properties();
                props.load(in2);
                TextureAnimation anim = TextureAnimations.makeTextureAnimation(props, e2);
                if (anim == null) continue;
                ResourceLocation locDstTex = new ResourceLocation(anim.getDstTex());
                if (Config.getDefiningResourcePack(locDstTex) != rp2) {
                    Config.dbg("Skipped: " + propName + ", target texture not loaded from same resource pack");
                    continue;
                }
                list.add(anim);
                continue;
            }
            catch (FileNotFoundException var10) {
                Config.warn("File not found: " + var10.getMessage());
                continue;
            }
            catch (IOException var11) {
                var11.printStackTrace();
            }
        }
        TextureAnimation[] var12 = list.toArray(new TextureAnimation[list.size()]);
        return var12;
    }

    public static TextureAnimation makeTextureAnimation(Properties props, ResourceLocation propLoc) {
        String texFrom = props.getProperty("from");
        String texTo = props.getProperty("to");
        int x2 = Config.parseInt(props.getProperty("x"), -1);
        int y2 = Config.parseInt(props.getProperty("y"), -1);
        int width = Config.parseInt(props.getProperty("w"), -1);
        int height = Config.parseInt(props.getProperty("h"), -1);
        if (texFrom != null && texTo != null) {
            if (x2 >= 0 && y2 >= 0 && width >= 0 && height >= 0) {
                texFrom = texFrom.trim();
                texTo = texTo.trim();
                String basePath = TextureUtils.getBasePath(propLoc.getResourcePath());
                texFrom = TextureUtils.fixResourcePath(texFrom, basePath);
                texTo = TextureUtils.fixResourcePath(texTo, basePath);
                byte[] imageBytes = TextureAnimations.getCustomTextureData(texFrom, width);
                if (imageBytes == null) {
                    Config.warn("TextureAnimation: Source texture not found: " + texTo);
                    return null;
                }
                ResourceLocation locTexTo = new ResourceLocation(texTo);
                if (!Config.hasResource(locTexTo)) {
                    Config.warn("TextureAnimation: Target texture not found: " + texTo);
                    return null;
                }
                TextureAnimation anim = new TextureAnimation(texFrom, imageBytes, texTo, locTexTo, x2, y2, width, height, props, 1);
                return anim;
            }
            Config.warn("TextureAnimation: Invalid coordinates");
            return null;
        }
        Config.warn("TextureAnimation: Source or target texture not specified");
        return null;
    }

    public static byte[] getCustomTextureData(String imagePath, int tileWidth) {
        byte[] imageBytes = TextureAnimations.loadImage(imagePath, tileWidth);
        if (imageBytes == null) {
            imageBytes = TextureAnimations.loadImage("/anim" + imagePath, tileWidth);
        }
        return imageBytes;
    }

    private static byte[] loadImage(String name, int targetWidth) {
        BufferedImage image;
        GameSettings options;
        block9 : {
            InputStream in2;
            block8 : {
                options = Config.getGameSettings();
                ResourceLocation e2 = new ResourceLocation(name);
                in2 = Config.getResourceStream(e2);
                if (in2 != null) break block8;
                return null;
            }
            image = TextureAnimations.readTextureImage(in2);
            in2.close();
            if (image != null) break block9;
            return null;
        }
        try {
            if (targetWidth > 0 && image.getWidth() != targetWidth) {
                double width = image.getHeight() / image.getWidth();
                int ai2 = (int)((double)targetWidth * width);
                image = TextureAnimations.scaleBufferedImage(image, targetWidth, ai2);
            }
            int var20 = image.getWidth();
            int height = image.getHeight();
            int[] var21 = new int[var20 * height];
            byte[] byteBuf = new byte[var20 * height * 4];
            image.getRGB(0, 0, var20, height, var21, 0, var20);
            for (int l2 = 0; l2 < var21.length; ++l2) {
                int alpha = var21[l2] >> 24 & 255;
                int red = var21[l2] >> 16 & 255;
                int green = var21[l2] >> 8 & 255;
                int blue = var21[l2] & 255;
                if (options != null && options.anaglyph) {
                    int j3 = (red * 30 + green * 59 + blue * 11) / 100;
                    int l3 = (red * 30 + green * 70) / 100;
                    int j4 = (red * 30 + blue * 70) / 100;
                    red = j3;
                    green = l3;
                    blue = j4;
                }
                byteBuf[l2 * 4 + 0] = (byte)red;
                byteBuf[l2 * 4 + 1] = (byte)green;
                byteBuf[l2 * 4 + 2] = (byte)blue;
                byteBuf[l2 * 4 + 3] = (byte)alpha;
            }
            return byteBuf;
        }
        catch (FileNotFoundException var18) {
            return null;
        }
        catch (Exception var19) {
            var19.printStackTrace();
            return null;
        }
    }

    private static BufferedImage readTextureImage(InputStream par1InputStream) throws IOException {
        BufferedImage var2 = ImageIO.read(par1InputStream);
        par1InputStream.close();
        return var2;
    }

    public static BufferedImage scaleBufferedImage(BufferedImage image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, 2);
        Graphics2D gr2 = scaledImage.createGraphics();
        gr2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        gr2.drawImage(image, 0, 0, width, height, null);
        return scaledImage;
    }
}

