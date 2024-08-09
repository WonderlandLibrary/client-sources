/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.optifine.Config;
import net.optifine.http.HttpPipeline;
import net.optifine.http.HttpRequest;
import net.optifine.http.HttpResponse;
import net.optifine.player.CapeImageBuffer;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DownloadingTexture
extends SimpleTexture {
    private static final Logger LOGGER = LogManager.getLogger();
    @Nullable
    private final File cacheFile;
    private final String imageUrl;
    private final boolean legacySkin;
    @Nullable
    private final Runnable processTask;
    @Nullable
    private CompletableFuture<?> future;
    private boolean textureUploaded;
    public Boolean imageFound = null;
    public boolean pipeline = false;
    private boolean uploadPending = false;

    public DownloadingTexture(@Nullable File file, String string, ResourceLocation resourceLocation, boolean bl, @Nullable Runnable runnable) {
        super(resourceLocation);
        this.cacheFile = file;
        this.imageUrl = string;
        this.legacySkin = bl;
        this.processTask = runnable;
    }

    private void setImage(NativeImage nativeImage) {
        if (this.processTask instanceof CapeImageBuffer) {
            CapeImageBuffer capeImageBuffer = (CapeImageBuffer)this.processTask;
            nativeImage = capeImageBuffer.parseUserSkin(nativeImage);
            capeImageBuffer.skinAvailable();
        }
        this.setImageImpl(nativeImage);
    }

    private void setImageImpl(NativeImage nativeImage) {
        if (this.processTask != null) {
            this.processTask.run();
        }
        Minecraft.getInstance().execute(() -> this.lambda$setImageImpl$1(nativeImage));
    }

    private void upload(NativeImage nativeImage) {
        TextureUtil.prepareImage(this.getGlTextureId(), nativeImage.getWidth(), nativeImage.getHeight());
        nativeImage.uploadTextureSub(0, 0, 0, false);
        this.imageFound = nativeImage != null;
    }

    @Override
    public void loadTexture(IResourceManager iResourceManager) throws IOException {
        Minecraft.getInstance().execute(() -> this.lambda$loadTexture$2(iResourceManager));
        if (this.future == null) {
            NativeImage nativeImage;
            if (this.cacheFile != null && this.cacheFile.isFile()) {
                LOGGER.debug("Loading http texture from local cache ({})", (Object)this.cacheFile);
                FileInputStream fileInputStream = new FileInputStream(this.cacheFile);
                nativeImage = this.loadTexture(fileInputStream);
            } else {
                nativeImage = null;
            }
            if (nativeImage != null) {
                this.setImage(nativeImage);
                this.loadingFinished();
            } else {
                this.future = CompletableFuture.runAsync(this::lambda$loadTexture$4, this.getExecutor());
            }
        }
    }

    @Nullable
    private NativeImage loadTexture(InputStream inputStream) {
        NativeImage nativeImage = null;
        try {
            nativeImage = NativeImage.read(inputStream);
            if (this.legacySkin) {
                nativeImage = DownloadingTexture.processLegacySkin(nativeImage);
            }
        } catch (IOException iOException) {
            LOGGER.warn("Error while loading the skin texture", (Throwable)iOException);
        }
        return nativeImage;
    }

    private boolean shouldPipeline() {
        if (!this.pipeline) {
            return true;
        }
        Proxy proxy = Minecraft.getInstance().getProxy();
        if (proxy.type() != Proxy.Type.DIRECT && proxy.type() != Proxy.Type.SOCKS) {
            return true;
        }
        return this.imageUrl.startsWith("http://");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadPipelined() {
        try {
            NativeImage nativeImage;
            HttpRequest httpRequest = HttpPipeline.makeRequest(this.imageUrl, Minecraft.getInstance().getProxy());
            HttpResponse httpResponse = HttpPipeline.executeRequest(httpRequest);
            if (httpResponse.getStatus() / 100 != 2) {
                return;
            }
            byte[] byArray = httpResponse.getBody();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
            if (this.cacheFile != null) {
                FileUtils.copyInputStreamToFile(byteArrayInputStream, this.cacheFile);
                nativeImage = NativeImage.read(new FileInputStream(this.cacheFile));
            } else {
                nativeImage = NativeImage.read(byteArrayInputStream);
            }
            this.setImage(nativeImage);
            this.uploadPending = true;
        } catch (Exception exception) {
            LOGGER.error("Couldn't download http texture: " + exception.getClass().getName() + ": " + exception.getMessage());
            return;
        } finally {
            this.loadingFinished();
        }
    }

    private void loadingFinished() {
        if (!this.uploadPending && this.processTask instanceof CapeImageBuffer) {
            CapeImageBuffer capeImageBuffer = (CapeImageBuffer)this.processTask;
            capeImageBuffer.cleanup();
        }
    }

    public Runnable getProcessTask() {
        return this.processTask;
    }

    private Executor getExecutor() {
        return this.imageUrl.startsWith("http://s.optifine.net") ? Util.getCapeExecutor() : Util.getServerExecutor();
    }

    private static NativeImage processLegacySkin(NativeImage nativeImage) {
        boolean bl;
        boolean bl2 = bl = nativeImage.getHeight() == 32;
        if (bl) {
            NativeImage nativeImage2 = new NativeImage(64, 64, true);
            nativeImage2.copyImageData(nativeImage);
            nativeImage.close();
            nativeImage = nativeImage2;
            nativeImage2.fillAreaRGBA(0, 32, 64, 32, 0);
            nativeImage2.copyAreaRGBA(4, 16, 16, 32, 4, 4, true, true);
            nativeImage2.copyAreaRGBA(8, 16, 16, 32, 4, 4, true, true);
            nativeImage2.copyAreaRGBA(0, 20, 24, 32, 4, 12, true, true);
            nativeImage2.copyAreaRGBA(4, 20, 16, 32, 4, 12, true, true);
            nativeImage2.copyAreaRGBA(8, 20, 8, 32, 4, 12, true, true);
            nativeImage2.copyAreaRGBA(12, 20, 16, 32, 4, 12, true, true);
            nativeImage2.copyAreaRGBA(44, 16, -8, 32, 4, 4, true, true);
            nativeImage2.copyAreaRGBA(48, 16, -8, 32, 4, 4, true, true);
            nativeImage2.copyAreaRGBA(40, 20, 0, 32, 4, 12, true, true);
            nativeImage2.copyAreaRGBA(44, 20, -8, 32, 4, 12, true, true);
            nativeImage2.copyAreaRGBA(48, 20, -16, 32, 4, 12, true, true);
            nativeImage2.copyAreaRGBA(52, 20, -8, 32, 4, 12, true, true);
        }
        DownloadingTexture.setAreaOpaque(nativeImage, 0, 0, 32, 16);
        if (bl) {
            DownloadingTexture.setAreaTransparent(nativeImage, 32, 0, 64, 32);
        }
        DownloadingTexture.setAreaOpaque(nativeImage, 0, 16, 64, 32);
        DownloadingTexture.setAreaOpaque(nativeImage, 16, 48, 48, 64);
        return nativeImage;
    }

    private static void setAreaTransparent(NativeImage nativeImage, int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        for (n6 = n; n6 < n3; ++n6) {
            for (n5 = n2; n5 < n4; ++n5) {
                int n7 = nativeImage.getPixelRGBA(n6, n5);
                if ((n7 >> 24 & 0xFF) >= 128) continue;
                return;
            }
        }
        for (n6 = n; n6 < n3; ++n6) {
            for (n5 = n2; n5 < n4; ++n5) {
                nativeImage.setPixelRGBA(n6, n5, nativeImage.getPixelRGBA(n6, n5) & 0xFFFFFF);
            }
        }
    }

    private static void setAreaOpaque(NativeImage nativeImage, int n, int n2, int n3, int n4) {
        for (int i = n; i < n3; ++i) {
            for (int j = n2; j < n4; ++j) {
                nativeImage.setPixelRGBA(i, j, nativeImage.getPixelRGBA(i, j) | 0xFF000000);
            }
        }
    }

    private void lambda$loadTexture$4() {
        HttpURLConnection httpURLConnection = null;
        LOGGER.debug("Downloading http texture from {} to {}", (Object)this.imageUrl, (Object)this.cacheFile);
        if (this.shouldPipeline()) {
            this.loadPipelined();
        } else {
            try {
                InputStream inputStream;
                httpURLConnection = (HttpURLConnection)new URL(this.imageUrl).openConnection(Minecraft.getInstance().getProxy());
                httpURLConnection.setDoInput(false);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() / 100 != 2) {
                    if (httpURLConnection.getErrorStream() != null) {
                        Config.readAll(httpURLConnection.getErrorStream());
                    }
                    return;
                }
                if (this.cacheFile != null) {
                    FileUtils.copyInputStreamToFile(httpURLConnection.getInputStream(), this.cacheFile);
                    inputStream = new FileInputStream(this.cacheFile);
                } else {
                    inputStream = httpURLConnection.getInputStream();
                }
                Minecraft.getInstance().execute(() -> this.lambda$loadTexture$3(inputStream));
                this.uploadPending = true;
            } catch (Exception exception) {
                LOGGER.error("Couldn't download http texture", (Throwable)exception);
                return;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                this.loadingFinished();
            }
        }
    }

    private void lambda$loadTexture$3(InputStream inputStream) {
        NativeImage nativeImage = this.loadTexture(inputStream);
        if (nativeImage != null) {
            this.setImage(nativeImage);
            this.loadingFinished();
        }
    }

    private void lambda$loadTexture$2(IResourceManager iResourceManager) {
        if (!this.textureUploaded) {
            try {
                super.loadTexture(iResourceManager);
            } catch (IOException iOException) {
                LOGGER.warn("Failed to load texture: {}", (Object)this.textureLocation, (Object)iOException);
            }
            this.textureUploaded = true;
        }
    }

    private void lambda$setImageImpl$1(NativeImage nativeImage) {
        this.textureUploaded = true;
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> this.lambda$setImageImpl$0(nativeImage));
        } else {
            this.upload(nativeImage);
        }
    }

    private void lambda$setImageImpl$0(NativeImage nativeImage) {
        this.upload(nativeImage);
    }
}

