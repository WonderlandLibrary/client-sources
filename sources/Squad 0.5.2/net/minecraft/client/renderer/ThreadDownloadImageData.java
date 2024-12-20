package net.minecraft.client.renderer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.Proxy.Type;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.src.Config;
import net.minecraft.src.HttpPipeline;
import net.minecraft.src.HttpRequest;
import net.minecraft.src.HttpResponse;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadDownloadImageData extends SimpleTexture
{
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
    private final File cacheFile;
    private final String imageUrl;
    private final IImageBuffer imageBuffer;
    private BufferedImage bufferedImage;
    private Thread imageThread;
    private boolean textureUploaded;
    public Boolean imageFound = null;
    public boolean pipeline = false;

    public ThreadDownloadImageData(File cacheFileIn, String imageUrlIn, ResourceLocation textureResourceLocation, IImageBuffer imageBufferIn)
    {
        super(textureResourceLocation);
        this.cacheFile = cacheFileIn;
        this.imageUrl = imageUrlIn;
        this.imageBuffer = imageBufferIn;
    }

    private void checkTextureUploaded()
    {
        if (!this.textureUploaded && this.bufferedImage != null)
        {
            this.textureUploaded = true;

            if (this.textureLocation != null)
            {
                this.deleteGlTexture();
            }

            TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
        }
    }

    public int getGlTextureId()
    {
        this.checkTextureUploaded();
        return super.getGlTextureId();
    }

    public void setBufferedImage(BufferedImage bufferedImageIn)
    {
        this.bufferedImage = bufferedImageIn;

        if (this.imageBuffer != null)
        {
            this.imageBuffer.skinAvailable();
        }

        this.imageFound = Boolean.valueOf(this.bufferedImage != null);
    }

    public void loadTexture(IResourceManager resourceManager) throws IOException
    {
        if (this.bufferedImage == null && this.textureLocation != null)
        {
            super.loadTexture(resourceManager);
        }

        if (this.imageThread == null)
        {
            if (this.cacheFile != null && this.cacheFile.isFile())
            {
                logger.debug("Loading http texture from local cache ({})", new Object[] {this.cacheFile});

                try
                {
                    this.bufferedImage = ImageIO.read(this.cacheFile);

                    if (this.imageBuffer != null)
                    {
                        this.setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
                    }

                    this.imageFound = Boolean.valueOf(this.bufferedImage != null);
                }
                catch (IOException var3)
                {
                    logger.error("Couldn\'t load skin " + this.cacheFile, var3);
                    this.loadTextureFromServer();
                }
            }
            else
            {
                this.loadTextureFromServer();
            }
        }
    }

    protected void loadTextureFromServer()
    {
        this.imageThread = new Thread("Texture Downloader #" + threadDownloadCounter.incrementAndGet())
        {
            public void run()
            {
                HttpURLConnection var1 = null;
                ThreadDownloadImageData.logger.debug("Downloading http texture from {} to {}", new Object[] {ThreadDownloadImageData.this.imageUrl, ThreadDownloadImageData.this.cacheFile});

                if (ThreadDownloadImageData.this.shouldPipeline())
                {
                    ThreadDownloadImageData.this.loadPipelined();
                }
                else
                {
                    try
                    {
                        var1 = (HttpURLConnection)(new URL(ThreadDownloadImageData.this.imageUrl)).openConnection(Minecraft.getMinecraft().getProxy());
                        var1.setDoInput(true);
                        var1.setDoOutput(false);
                        var1.connect();

                        if (var1.getResponseCode() / 100 != 2)
                        {
                            if (var1.getErrorStream() != null)
                            {
                                Config.readAll(var1.getErrorStream());
                            }

                            return;
                        }

                        BufferedImage var6;

                        if (ThreadDownloadImageData.this.cacheFile != null)
                        {
                            FileUtils.copyInputStreamToFile(var1.getInputStream(), ThreadDownloadImageData.this.cacheFile);
                            var6 = ImageIO.read(ThreadDownloadImageData.this.cacheFile);
                        }
                        else
                        {
                            var6 = TextureUtil.readBufferedImage(var1.getInputStream());
                        }

                        if (ThreadDownloadImageData.this.imageBuffer != null)
                        {
                            var6 = ThreadDownloadImageData.this.imageBuffer.parseUserSkin(var6);
                        }

                        ThreadDownloadImageData.this.setBufferedImage(var6);
                    }
                    catch (Exception var61)
                    {
                        ThreadDownloadImageData.logger.error("Couldn\'t download http texture: " + var61.getClass().getName() + ": " + var61.getMessage());
                        return;
                    }
                    finally
                    {
                        if (var1 != null)
                        {
                            var1.disconnect();
                        }

                        ThreadDownloadImageData.this.imageFound = Boolean.valueOf(ThreadDownloadImageData.this.bufferedImage != null);
                    }
                }
            }
        };
        this.imageThread.setDaemon(true);
        this.imageThread.start();
    }

    private boolean shouldPipeline()
    {
        if (!this.pipeline)
        {
            return false;
        }
        else
        {
            Proxy proxy = Minecraft.getMinecraft().getProxy();
            return proxy.type() != Type.DIRECT && proxy.type() != Type.SOCKS ? false : this.imageUrl.startsWith("http://");
        }
    }

    private void loadPipelined()
    {
        try
        {
            HttpRequest var6 = HttpPipeline.makeRequest(this.imageUrl, Minecraft.getMinecraft().getProxy());
            HttpResponse resp = HttpPipeline.executeRequest(var6);

            if (resp.getStatus() / 100 == 2)
            {
                byte[] body = resp.getBody();
                ByteArrayInputStream bais = new ByteArrayInputStream(body);
                BufferedImage var2;

                if (this.cacheFile != null)
                {
                    FileUtils.copyInputStreamToFile(bais, this.cacheFile);
                    var2 = ImageIO.read(this.cacheFile);
                }
                else
                {
                    var2 = TextureUtil.readBufferedImage(bais);
                }

                if (this.imageBuffer != null)
                {
                    var2 = this.imageBuffer.parseUserSkin(var2);
                }

                this.setBufferedImage(var2);
                return;
            }
        }
        catch (Exception var9)
        {
            logger.error("Couldn\'t download http texture: " + var9.getClass().getName() + ": " + var9.getMessage());
            return;
        }
        finally
        {
            this.imageFound = Boolean.valueOf(this.bufferedImage != null);
        }
    }
}
