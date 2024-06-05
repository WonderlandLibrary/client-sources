package net.minecraft.client.renderer;

import java.util.concurrent.atomic.*;
import java.awt.image.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;
import net.minecraft.client.*;
import org.apache.commons.io.*;
import org.apache.logging.log4j.*;

public class ThreadDownloadImageData extends SimpleTexture
{
    private static final Logger logger;
    private static final AtomicInteger threadDownloadCounter;
    private final File cacheFile;
    private final String imageUrl;
    private final IImageBuffer imageBuffer;
    private BufferedImage bufferedImage;
    private Thread imageThread;
    private boolean textureUploaded;
    private static final String __OBFID = "CL_00001049";
    public Boolean imageFound;
    
    public ThreadDownloadImageData(final File cacheFileIn, final String imageUrlIn, final ResourceLocation textureResourceLocation, final IImageBuffer imageBufferIn) {
        super(textureResourceLocation);
        this.imageFound = null;
        this.cacheFile = cacheFileIn;
        this.imageUrl = imageUrlIn;
        this.imageBuffer = imageBufferIn;
    }
    
    private void checkTextureUploaded() {
        if (!this.textureUploaded && this.bufferedImage != null) {
            if (this.textureLocation != null) {
                this.deleteGlTexture();
            }
            TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
            this.textureUploaded = true;
        }
    }
    
    @Override
    public int getGlTextureId() {
        this.checkTextureUploaded();
        return super.getGlTextureId();
    }
    
    public void setBufferedImage(final BufferedImage bufferedImageIn) {
        this.bufferedImage = bufferedImageIn;
        if (this.imageBuffer != null) {
            this.imageBuffer.skinAvailable();
        }
        this.imageFound = (this.bufferedImage != null);
    }
    
    @Override
    public void loadTexture(final IResourceManager resourceManager) throws IOException {
        if (this.bufferedImage == null && this.textureLocation != null) {
            super.loadTexture(resourceManager);
        }
        if (this.imageThread == null) {
            if (this.cacheFile != null && this.cacheFile.isFile()) {
                ThreadDownloadImageData.logger.debug("Loading http texture from local cache ({})", this.cacheFile);
                try {
                    this.bufferedImage = ImageIO.read(this.cacheFile);
                    if (this.imageBuffer != null) {
                        this.setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
                    }
                    this.imageFound = (this.bufferedImage != null);
                }
                catch (IOException ioexception) {
                    ThreadDownloadImageData.logger.error("Couldn't load skin " + this.cacheFile, ioexception);
                    this.loadTextureFromServer();
                }
            }
            else {
                this.loadTextureFromServer();
            }
        }
    }
    
    protected void loadTextureFromServer() {
        (this.imageThread = new Thread("Texture Downloader #" + ThreadDownloadImageData.threadDownloadCounter.incrementAndGet()) {
            private static final String __OBFID = "CL_00001050";
            
            @Override
            public void run() {
                HttpURLConnection httpurlconnection = null;
                ThreadDownloadImageData.logger.debug("Downloading http texture from {} to {}", ThreadDownloadImageData.this.imageUrl, ThreadDownloadImageData.this.cacheFile);
                try {
                    httpurlconnection = (HttpURLConnection)new URL(ThreadDownloadImageData.this.imageUrl).openConnection(Minecraft.getMinecraft().getProxy());
                    httpurlconnection.setDoInput(true);
                    httpurlconnection.setDoOutput(false);
                    httpurlconnection.connect();
                    if (httpurlconnection.getResponseCode() / 100 != 2) {
                        return;
                    }
                    BufferedImage bufferedimage;
                    if (ThreadDownloadImageData.this.cacheFile != null) {
                        FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), ThreadDownloadImageData.this.cacheFile);
                        bufferedimage = ImageIO.read(ThreadDownloadImageData.this.cacheFile);
                    }
                    else {
                        bufferedimage = TextureUtil.readBufferedImage(httpurlconnection.getInputStream());
                    }
                    if (ThreadDownloadImageData.this.imageBuffer != null) {
                        bufferedimage = ThreadDownloadImageData.this.imageBuffer.parseUserSkin(bufferedimage);
                    }
                    ThreadDownloadImageData.this.setBufferedImage(bufferedimage);
                }
                catch (Exception exception) {
                    ThreadDownloadImageData.logger.error("Couldn't download http texture", exception);
                }
                finally {
                    if (httpurlconnection != null) {
                        httpurlconnection.disconnect();
                    }
                    ThreadDownloadImageData.this.imageFound = (ThreadDownloadImageData.this.bufferedImage != null);
                }
            }
        }).setDaemon(true);
        this.imageThread.start();
    }
    
    static {
        logger = LogManager.getLogger();
        threadDownloadCounter = new AtomicInteger(0);
    }
}
