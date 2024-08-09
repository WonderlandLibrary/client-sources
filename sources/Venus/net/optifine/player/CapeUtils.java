/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.player;

import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.texture.DownloadingTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.player.CapeImageBuffer;
import net.optifine.util.TextureUtils;

public class CapeUtils {
    private static final Pattern PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");

    public static void downloadCape(AbstractClientPlayerEntity abstractClientPlayerEntity) {
        String string = abstractClientPlayerEntity.getNameClear();
        if (string != null && !string.isEmpty() && !string.contains("\u0000") && PATTERN_USERNAME.matcher(string).matches()) {
            Object object;
            String string2 = "http://s.optifine.net/capes/" + string + ".png";
            ResourceLocation resourceLocation = new ResourceLocation("capeof/" + string);
            TextureManager textureManager = Minecraft.getInstance().getTextureManager();
            Texture texture = textureManager.getTexture(resourceLocation);
            if (texture != null && texture instanceof DownloadingTexture) {
                object = (DownloadingTexture)texture;
                if (((DownloadingTexture)object).imageFound != null) {
                    if (((DownloadingTexture)object).imageFound.booleanValue()) {
                        abstractClientPlayerEntity.setLocationOfCape(resourceLocation);
                        if (((DownloadingTexture)object).getProcessTask() instanceof CapeImageBuffer) {
                            CapeImageBuffer capeImageBuffer = (CapeImageBuffer)((DownloadingTexture)object).getProcessTask();
                            abstractClientPlayerEntity.setElytraOfCape(capeImageBuffer.isElytraOfCape());
                        }
                    }
                    return;
                }
            }
            object = new CapeImageBuffer(abstractClientPlayerEntity, resourceLocation);
            ResourceLocation resourceLocation2 = TextureUtils.LOCATION_TEXTURE_EMPTY;
            DownloadingTexture downloadingTexture = new DownloadingTexture(null, string2, resourceLocation2, false, (Runnable)object);
            downloadingTexture.pipeline = true;
            textureManager.loadTexture(resourceLocation, downloadingTexture);
        }
    }

    public static NativeImage parseCape(NativeImage nativeImage) {
        int n;
        int n2 = 64;
        int n3 = nativeImage.getWidth();
        int n4 = nativeImage.getHeight();
        for (n = 32; n2 < n3 || n < n4; n2 *= 2, n *= 2) {
        }
        NativeImage nativeImage2 = new NativeImage(n2, n, true);
        nativeImage2.copyImageData(nativeImage);
        nativeImage.close();
        return nativeImage2;
    }

    public static boolean isElytraCape(NativeImage nativeImage, NativeImage nativeImage2) {
        return nativeImage.getWidth() > nativeImage2.getHeight();
    }

    public static void reloadCape(AbstractClientPlayerEntity abstractClientPlayerEntity) {
        String string = abstractClientPlayerEntity.getNameClear();
        ResourceLocation resourceLocation = new ResourceLocation("capeof/" + string);
        TextureManager textureManager = Config.getTextureManager();
        Texture texture = textureManager.getTexture(resourceLocation);
        if (texture instanceof SimpleTexture) {
            SimpleTexture simpleTexture = (SimpleTexture)texture;
            simpleTexture.deleteGlTexture();
            textureManager.deleteTexture(resourceLocation);
        }
        abstractClientPlayerEntity.setLocationOfCape(null);
        abstractClientPlayerEntity.setElytraOfCape(true);
        CapeUtils.downloadCape(abstractClientPlayerEntity);
    }
}

