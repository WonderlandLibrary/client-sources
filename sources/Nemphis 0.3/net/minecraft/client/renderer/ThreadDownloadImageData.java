/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.HttpPipeline;
import optifine.HttpRequest;
import optifine.HttpResponse;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadDownloadImageData
extends SimpleTexture {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
    private final File field_152434_e;
    private final String imageUrl;
    private final IImageBuffer imageBuffer;
    private BufferedImage bufferedImage;
    private Thread imageThread;
    private boolean textureUploaded;
    private static final String __OBFID = "CL_00001049";
    public Boolean imageFound = null;
    public boolean pipeline = false;

    public ThreadDownloadImageData(File p_i1049_1_, String p_i1049_2_, ResourceLocation p_i1049_3_, IImageBuffer p_i1049_4_) {
        super(p_i1049_3_);
        this.field_152434_e = p_i1049_1_;
        this.imageUrl = p_i1049_2_;
        this.imageBuffer = p_i1049_4_;
    }

    private void checkTextureUploaded() {
        if (!this.textureUploaded && this.bufferedImage != null) {
            this.textureUploaded = true;
            if (this.textureLocation != null) {
                this.deleteGlTexture();
            }
            TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
        }
    }

    @Override
    public int getGlTextureId() {
        this.checkTextureUploaded();
        return super.getGlTextureId();
    }

    public void setBufferedImage(BufferedImage p_147641_1_) {
        this.bufferedImage = p_147641_1_;
        if (this.imageBuffer != null) {
            this.imageBuffer.func_152634_a();
        }
        this.imageFound = this.bufferedImage != null;
    }

    @Override
    public void loadTexture(IResourceManager p_110551_1_) throws IOException {
        if (this.bufferedImage == null && this.textureLocation != null) {
            super.loadTexture(p_110551_1_);
        }
        if (this.imageThread == null) {
            if (this.field_152434_e != null && this.field_152434_e.isFile()) {
                logger.debug("Loading http texture from local cache ({})", new Object[]{this.field_152434_e});
                try {
                    this.bufferedImage = ImageIO.read(this.field_152434_e);
                    if (this.imageBuffer != null) {
                        this.setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
                    }
                    this.imageFound = this.bufferedImage != null;
                }
                catch (IOException var3) {
                    logger.error("Couldn't load skin " + this.field_152434_e, (Throwable)var3);
                    this.func_152433_a();
                }
            } else {
                this.func_152433_a();
            }
        }
    }

    protected void func_152433_a() {
        this.imageThread = new Thread("Texture Downloader #" + threadDownloadCounter.incrementAndGet()){

            @Override
            public void run() {
                HttpURLConnection var1 = null;
                logger.debug("Downloading http texture from {} to {}", new Object[]{ThreadDownloadImageData.this.imageUrl, ThreadDownloadImageData.this.field_152434_e});
                if (ThreadDownloadImageData.this.shouldPipeline()) {
                    ThreadDownloadImageData.this.loadPipelined();
                } else {
                    block14 : {
                        block15 : {
                            var1 = (HttpURLConnection)new URL(ThreadDownloadImageData.this.imageUrl).openConnection(Minecraft.getMinecraft().getProxy());
                            var1.setDoInput(true);
                            var1.setDoOutput(false);
                            var1.connect();
                            if (var1.getResponseCode() / 100 == 2) break block14;
                            if (var1.getErrorStream() != null) {
                                Config.readAll(var1.getErrorStream());
                            }
                            if (var1 == null) break block15;
                            var1.disconnect();
                        }
                        ThreadDownloadImageData.this.imageFound = ThreadDownloadImageData.this.bufferedImage != null;
                        return;
                    }
                    try {
                        try {
                            BufferedImage var6;
                            if (ThreadDownloadImageData.this.field_152434_e != null) {
                                FileUtils.copyInputStreamToFile((InputStream)var1.getInputStream(), (File)ThreadDownloadImageData.this.field_152434_e);
                                var6 = ImageIO.read(ThreadDownloadImageData.this.field_152434_e);
                            } else {
                                var6 = TextureUtil.func_177053_a(var1.getInputStream());
                            }
                            if (ThreadDownloadImageData.this.imageBuffer != null) {
                                var6 = ThreadDownloadImageData.this.imageBuffer.parseUserSkin(var6);
                            }
                            ThreadDownloadImageData.this.setBufferedImage(var6);
                        }
                        catch (Exception var61) {
                            logger.error("Couldn't download http texture: " + var61.getClass().getName() + ": " + var61.getMessage());
                            if (var1 != null) {
                                var1.disconnect();
                            }
                            ThreadDownloadImageData.this.imageFound = ThreadDownloadImageData.this.bufferedImage != null;
                            return;
                        }
                    }
                    catch (Throwable var3_4) {
                        if (var1 != null) {
                            var1.disconnect();
                        }
                        ThreadDownloadImageData.this.imageFound = ThreadDownloadImageData.this.bufferedImage != null;
                        throw var3_4;
                    }
                    if (var1 != null) {
                        var1.disconnect();
                    }
                    ThreadDownloadImageData.this.imageFound = ThreadDownloadImageData.this.bufferedImage != null;
                }
            }
        };
        this.imageThread.setDaemon(true);
        this.imageThread.start();
    }

    private boolean shouldPipeline() {
        if (!this.pipeline) {
            return false;
        }
        Proxy proxy = Minecraft.getMinecraft().getProxy();
        return proxy.type() != Proxy.Type.DIRECT && proxy.type() != Proxy.Type.SOCKS ? false : this.imageUrl.startsWith("http://");
    }

    private void loadPipelined() {
        HttpResponse resp;
        block8 : {
            HttpRequest var6 = HttpPipeline.makeRequest(this.imageUrl, Minecraft.getMinecraft().getProxy());
            resp = HttpPipeline.executeRequest(var6);
            if (resp.getStatus() / 100 == 2) break block8;
            this.imageFound = this.bufferedImage != null;
            return;
        }
        try {
            try {
                BufferedImage var2;
                byte[] body = resp.getBody();
                ByteArrayInputStream bais = new ByteArrayInputStream(body);
                if (this.field_152434_e != null) {
                    FileUtils.copyInputStreamToFile((InputStream)bais, (File)this.field_152434_e);
                    var2 = ImageIO.read(this.field_152434_e);
                } else {
                    var2 = TextureUtil.func_177053_a(bais);
                }
                if (this.imageBuffer != null) {
                    var2 = this.imageBuffer.parseUserSkin(var2);
                }
                this.setBufferedImage(var2);
            }
            catch (Exception var9) {
                logger.error("Couldn't download http texture: " + var9.getClass().getName() + ": " + var9.getMessage());
                this.imageFound = this.bufferedImage != null;
                return;
            }
        }
        catch (Throwable var6_7) {
            this.imageFound = this.bufferedImage != null;
            throw var6_7;
        }
        this.imageFound = this.bufferedImage != null;
    }

}

