/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import javax.imageio.ImageIO;
import net.minecraft.client.GameSettings;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.TextureAnimation;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.TextureUtils;

public class TextureAnimations {
    private static TextureAnimation[] textureAnimations = null;
    private static int countAnimationsActive = 0;
    private static int frameCountAnimations = 0;

    public static void reset() {
        textureAnimations = null;
    }

    public static void update() {
        textureAnimations = null;
        countAnimationsActive = 0;
        IResourcePack[] iResourcePackArray = Config.getResourcePacks();
        textureAnimations = TextureAnimations.getTextureAnimations(iResourcePackArray);
        TextureAnimations.updateAnimations();
    }

    public static void updateAnimations() {
        if (textureAnimations != null && Config.isAnimatedTextures()) {
            int n;
            int n2 = 0;
            for (n = 0; n < textureAnimations.length; ++n) {
                TextureAnimation textureAnimation = textureAnimations[n];
                textureAnimation.updateTexture();
                if (!textureAnimation.isActive()) continue;
                ++n2;
            }
            n = Config.getMinecraft().worldRenderer.getFrameCount();
            if (n != frameCountAnimations) {
                countAnimationsActive = n2;
                frameCountAnimations = n;
            }
            if (SmartAnimations.isActive()) {
                SmartAnimations.resetTexturesRendered();
            }
        } else {
            countAnimationsActive = 0;
        }
    }

    private static TextureAnimation[] getTextureAnimations(IResourcePack[] iResourcePackArray) {
        ArrayList<TextureAnimation> arrayList = new ArrayList<TextureAnimation>();
        for (int i = 0; i < iResourcePackArray.length; ++i) {
            IResourcePack iResourcePack = iResourcePackArray[i];
            TextureAnimation[] textureAnimationArray = TextureAnimations.getTextureAnimations(iResourcePack);
            if (textureAnimationArray == null) continue;
            arrayList.addAll(Arrays.asList(textureAnimationArray));
        }
        return arrayList.toArray(new TextureAnimation[arrayList.size()]);
    }

    private static TextureAnimation[] getTextureAnimations(IResourcePack iResourcePack) {
        String[] stringArray = ResUtils.collectFiles(iResourcePack, "optifine/anim/", ".properties", (String[])null);
        if (stringArray.length <= 0) {
            return null;
        }
        ArrayList<TextureAnimation> arrayList = new ArrayList<TextureAnimation>();
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            Config.dbg("Texture animation: " + string);
            try {
                ResourceLocation resourceLocation = new ResourceLocation(string);
                InputStream inputStream = iResourcePack.getResourceStream(ResourcePackType.CLIENT_RESOURCES, resourceLocation);
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputStream);
                inputStream.close();
                TextureAnimation textureAnimation = TextureAnimations.makeTextureAnimation(propertiesOrdered, resourceLocation);
                if (textureAnimation == null) continue;
                ResourceLocation resourceLocation2 = new ResourceLocation(textureAnimation.getDstTex());
                if (!Config.hasResource(iResourcePack, resourceLocation2)) {
                    Config.dbg("Skipped: " + string + ", target texture not loaded from same resource pack");
                    continue;
                }
                arrayList.add(textureAnimation);
                continue;
            } catch (FileNotFoundException fileNotFoundException) {
                Config.warn("File not found: " + fileNotFoundException.getMessage());
                continue;
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        return arrayList.toArray(new TextureAnimation[arrayList.size()]);
    }

    private static TextureAnimation makeTextureAnimation(Properties properties, ResourceLocation resourceLocation) {
        String string = properties.getProperty("from");
        String string2 = properties.getProperty("to");
        int n = Config.parseInt(properties.getProperty("x"), -1);
        int n2 = Config.parseInt(properties.getProperty("y"), -1);
        int n3 = Config.parseInt(properties.getProperty("w"), -1);
        int n4 = Config.parseInt(properties.getProperty("h"), -1);
        if (string != null && string2 != null) {
            if (n >= 0 && n2 >= 0 && n3 >= 0 && n4 >= 0) {
                string = string.trim();
                string2 = string2.trim();
                String string3 = TextureUtils.getBasePath(resourceLocation.getPath());
                string = TextureUtils.fixResourcePath(string, string3);
                string2 = TextureUtils.fixResourcePath(string2, string3);
                byte[] byArray = TextureAnimations.getCustomTextureData(string, n3);
                if (byArray == null) {
                    Config.warn("TextureAnimation: Source texture not found: " + string2);
                    return null;
                }
                int n5 = byArray.length / 4;
                int n6 = n5 / (n3 * n4);
                int n7 = n6 * n3 * n4;
                if (n5 != n7) {
                    Config.warn("TextureAnimation: Source texture has invalid number of frames: " + string + ", frames: " + (float)n5 / (float)(n3 * n4));
                    return null;
                }
                ResourceLocation resourceLocation2 = new ResourceLocation(string2);
                try {
                    InputStream inputStream = Config.getResourceStream(resourceLocation2);
                    if (inputStream == null) {
                        Config.warn("TextureAnimation: Target texture not found: " + string2);
                        return null;
                    }
                    BufferedImage bufferedImage = TextureAnimations.readTextureImage(inputStream);
                    if (n + n3 <= bufferedImage.getWidth() && n2 + n4 <= bufferedImage.getHeight()) {
                        return new TextureAnimation(string, byArray, string2, resourceLocation2, n, n2, n3, n4, properties);
                    }
                    Config.warn("TextureAnimation: Animation coordinates are outside the target texture: " + string2);
                    return null;
                } catch (IOException iOException) {
                    Config.warn("TextureAnimation: Target texture not found: " + string2);
                    return null;
                }
            }
            Config.warn("TextureAnimation: Invalid coordinates");
            return null;
        }
        Config.warn("TextureAnimation: Source or target texture not specified");
        return null;
    }

    private static byte[] getCustomTextureData(String string, int n) {
        byte[] byArray = TextureAnimations.loadImage(string, n);
        if (byArray == null) {
            byArray = TextureAnimations.loadImage("/anim" + string, n);
        }
        return byArray;
    }

    private static byte[] loadImage(String string, int n) {
        GameSettings gameSettings = Config.getGameSettings();
        try {
            ResourceLocation resourceLocation = new ResourceLocation(string);
            InputStream inputStream = Config.getResourceStream(resourceLocation);
            if (inputStream == null) {
                return null;
            }
            BufferedImage bufferedImage = TextureAnimations.readTextureImage(inputStream);
            inputStream.close();
            if (bufferedImage == null) {
                return null;
            }
            if (n > 0 && bufferedImage.getWidth() != n) {
                double d = bufferedImage.getHeight() / bufferedImage.getWidth();
                int n2 = (int)((double)n * d);
                bufferedImage = TextureAnimations.scaleBufferedImage(bufferedImage, n, n2);
            }
            int n3 = bufferedImage.getWidth();
            int n4 = bufferedImage.getHeight();
            int[] nArray = new int[n3 * n4];
            byte[] byArray = new byte[n3 * n4 * 4];
            bufferedImage.getRGB(0, 0, n3, n4, nArray, 0, n3);
            for (int i = 0; i < nArray.length; ++i) {
                int n5 = nArray[i] >> 24 & 0xFF;
                int n6 = nArray[i] >> 16 & 0xFF;
                int n7 = nArray[i] >> 8 & 0xFF;
                int n8 = nArray[i] & 0xFF;
                byArray[i * 4 + 0] = (byte)n6;
                byArray[i * 4 + 1] = (byte)n7;
                byArray[i * 4 + 2] = (byte)n8;
                byArray[i * 4 + 3] = (byte)n5;
            }
            return byArray;
        } catch (FileNotFoundException fileNotFoundException) {
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static BufferedImage readTextureImage(InputStream inputStream) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        inputStream.close();
        return bufferedImage;
    }

    private static BufferedImage scaleBufferedImage(BufferedImage bufferedImage, int n, int n2) {
        BufferedImage bufferedImage2 = new BufferedImage(n, n2, 2);
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(bufferedImage, 0, 0, n, n2, null);
        return bufferedImage2;
    }

    public static int getCountAnimations() {
        return textureAnimations == null ? 0 : textureAnimations.length;
    }

    public static int getCountAnimationsActive() {
        return countAnimationsActive;
    }
}

