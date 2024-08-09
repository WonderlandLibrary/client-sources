/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.util.RealmsUtil;
import com.mojang.realmsclient.util.SkinProcessor;
import com.mojang.util.UUIDTypeAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsTextureManager {
    private static final Map<String, RealmsTexture> field_225209_a = Maps.newHashMap();
    private static final Map<String, Boolean> field_225210_b = Maps.newHashMap();
    private static final Map<String, String> field_225211_c = Maps.newHashMap();
    private static final Logger field_225212_d = LogManager.getLogger();
    private static final ResourceLocation field_238097_e_ = new ResourceLocation("textures/gui/presets/isles.png");

    public static void func_225202_a(String string, @Nullable String string2) {
        if (string2 == null) {
            Minecraft.getInstance().getTextureManager().bindTexture(field_238097_e_);
        } else {
            int n = RealmsTextureManager.func_225203_b(string, string2);
            RenderSystem.bindTexture(n);
        }
    }

    public static void func_225205_a(String string, Runnable runnable) {
        RenderSystem.pushTextureAttributes();
        try {
            RealmsTextureManager.func_225200_a(string);
            runnable.run();
        } finally {
            RenderSystem.popAttributes();
        }
    }

    private static void func_225204_a(UUID uUID) {
        Minecraft.getInstance().getTextureManager().bindTexture(DefaultPlayerSkin.getDefaultSkin(uUID));
    }

    private static void func_225200_a(String string) {
        UUID uUID = UUIDTypeAdapter.fromString(string);
        if (field_225209_a.containsKey(string)) {
            RenderSystem.bindTexture(RealmsTextureManager.field_225209_a.get((Object)string).field_225198_b);
        } else if (field_225210_b.containsKey(string)) {
            if (!field_225210_b.get(string).booleanValue()) {
                RealmsTextureManager.func_225204_a(uUID);
            } else if (field_225211_c.containsKey(string)) {
                int n = RealmsTextureManager.func_225203_b(string, field_225211_c.get(string));
                RenderSystem.bindTexture(n);
            } else {
                RealmsTextureManager.func_225204_a(uUID);
            }
        } else {
            field_225210_b.put(string, false);
            RealmsTextureManager.func_225204_a(uUID);
            Thread thread2 = new Thread("Realms Texture Downloader", string){
                final String val$p_225200_0_;
                {
                    this.val$p_225200_0_ = string2;
                    super(string);
                }

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    ByteArrayOutputStream byteArrayOutputStream;
                    BufferedImage bufferedImage;
                    Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = RealmsUtil.func_225191_b(this.val$p_225200_0_);
                    if (!map.containsKey((Object)MinecraftProfileTexture.Type.SKIN)) {
                        field_225210_b.put(this.val$p_225200_0_, true);
                        return;
                    }
                    MinecraftProfileTexture minecraftProfileTexture = map.get((Object)MinecraftProfileTexture.Type.SKIN);
                    String string = minecraftProfileTexture.getUrl();
                    HttpURLConnection httpURLConnection = null;
                    field_225212_d.debug("Downloading http texture from {}", (Object)string);
                    try {
                        httpURLConnection = (HttpURLConnection)new URL(string).openConnection(Minecraft.getInstance().getProxy());
                        httpURLConnection.setDoInput(false);
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.connect();
                        if (httpURLConnection.getResponseCode() / 100 == 2) {
                            try {
                                bufferedImage = ImageIO.read(httpURLConnection.getInputStream());
                            } catch (Exception exception) {
                                field_225210_b.remove(this.val$p_225200_0_);
                                if (httpURLConnection == null) return;
                                httpURLConnection.disconnect();
                                return;
                            } finally {
                                IOUtils.closeQuietly(httpURLConnection.getInputStream());
                            }
                        } else {
                            field_225210_b.remove(this.val$p_225200_0_);
                            return;
                        }
                        bufferedImage = new SkinProcessor().func_225228_a(bufferedImage);
                        byteArrayOutputStream = new ByteArrayOutputStream();
                    } catch (Exception exception) {
                        field_225212_d.error("Couldn't download http texture", (Throwable)exception);
                        field_225210_b.remove(this.val$p_225200_0_);
                        return;
                    }
                    ImageIO.write((RenderedImage)bufferedImage, "png", byteArrayOutputStream);
                    field_225211_c.put(this.val$p_225200_0_, new Base64().encodeToString(byteArrayOutputStream.toByteArray()));
                    field_225210_b.put(this.val$p_225200_0_, true);
                    return;
                    finally {
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                    }
                }
            };
            thread2.setDaemon(false);
            thread2.start();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static int func_225203_b(String string, String string2) {
        int n;
        Object object;
        if (field_225209_a.containsKey(string)) {
            object = field_225209_a.get(string);
            if (((RealmsTexture)object).field_225197_a.equals(string2)) {
                return ((RealmsTexture)object).field_225198_b;
            }
            RenderSystem.deleteTexture(((RealmsTexture)object).field_225198_b);
            n = ((RealmsTexture)object).field_225198_b;
        } else {
            n = GlStateManager.genTexture();
        }
        object = null;
        int n2 = 0;
        int n3 = 0;
        try {
            BufferedImage bufferedImage;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new Base64().decode(string2));
            try {
                bufferedImage = ImageIO.read(byteArrayInputStream);
            } finally {
                IOUtils.closeQuietly(byteArrayInputStream);
            }
            n2 = bufferedImage.getWidth();
            n3 = bufferedImage.getHeight();
            int[] nArray = new int[n2 * n3];
            bufferedImage.getRGB(0, 0, n2, n3, nArray, 0, n2);
            object = ByteBuffer.allocateDirect(4 * n2 * n3).order(ByteOrder.nativeOrder()).asIntBuffer();
            ((IntBuffer)object).put(nArray);
            ((Buffer)object).flip();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        RenderSystem.activeTexture(33984);
        RenderSystem.bindTexture(n);
        TextureUtil.initTexture((IntBuffer)object, n2, n3);
        field_225209_a.put(string, new RealmsTexture(string2, n));
        return n;
    }

    public static class RealmsTexture {
        private final String field_225197_a;
        private final int field_225198_b;

        public RealmsTexture(String string, int n) {
            this.field_225197_a = string;
            this.field_225198_b = n;
        }
    }
}

