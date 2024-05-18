package de.labystudio.capes.downloader;

import de.labystudio.capes.CapeCallback;
import de.labystudio.utils.Debug;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;

public class ThreadDownloadCapeData extends SimpleTexture
{
    private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
    private final File cacheFile;
    private final String imageUrl;
    private final IImageBuffer imageBuffer;
    private BufferedImage bufferedImage;
    private Thread imageThread;
    private boolean textureUploaded;
    private static final String __OBFID = "CL_00001049";
    public Boolean imageFound = null;
    public boolean pipeline = false;
    private CapeCallback callBack;

    public ThreadDownloadCapeData(File cacheFileIn, String imageUrlIn, ResourceLocation textureResourceLocation, IImageBuffer imageBufferIn, CapeCallback callBack)
    {
        super(textureResourceLocation);
        this.cacheFile = cacheFileIn;
        this.imageUrl = imageUrlIn;
        this.imageBuffer = imageBufferIn;
        this.callBack = callBack;
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
                Debug.debug(Debug.EnumDebugMode.CAPES, "Loading http texture from local cache (" + this.cacheFile + ")");

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
                    Debug.debug(Debug.EnumDebugMode.CAPES, "Couldn\'t load skin " + this.cacheFile);
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
            private static final String __OBFID = "CL_00001050";
            public void run()
            {
                HttpURLConnection httpurlconnection = null;
                Debug.debug(Debug.EnumDebugMode.CAPES, "Downloading http texture from " + ThreadDownloadCapeData.this.imageUrl + " to " + ThreadDownloadCapeData.this.cacheFile);

                try
                {
                    httpurlconnection = (HttpURLConnection)(new URL(ThreadDownloadCapeData.this.imageUrl)).openConnection(Minecraft.getMinecraft().getProxy());
                    httpurlconnection.setDoInput(true);
                    httpurlconnection.setDoOutput(false);
                    httpurlconnection.connect();

                    if (httpurlconnection.getResponseCode() / 100 == 2)
                    {
                        BufferedImage bufferedimage;

                        if (ThreadDownloadCapeData.this.cacheFile != null)
                        {
                            FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), ThreadDownloadCapeData.this.cacheFile);
                            bufferedimage = ImageIO.read(ThreadDownloadCapeData.this.cacheFile);
                        }
                        else
                        {
                            bufferedimage = TextureUtil.readBufferedImage(httpurlconnection.getInputStream());
                        }

                        if (ThreadDownloadCapeData.this.imageBuffer != null)
                        {
                            bufferedimage = ThreadDownloadCapeData.this.imageBuffer.parseUserSkin(bufferedimage);
                        }

                        ThreadDownloadCapeData.this.setBufferedImage(bufferedimage);
                    }
                    else if (httpurlconnection.getErrorStream() != null)
                    {
                        Debug.debug(Debug.EnumDebugMode.CAPES, httpurlconnection.getErrorStream().toString());
                    }
                }
                catch (Exception exception)
                {
                    Debug.debug(Debug.EnumDebugMode.CAPES, "Couldn\'t download http texture: " + exception.getClass().getName() + ": " + exception.getMessage());
                }
                finally
                {
                    if (httpurlconnection != null)
                    {
                        httpurlconnection.disconnect();
                    }

                    ThreadDownloadCapeData.this.imageFound = Boolean.valueOf(ThreadDownloadCapeData.this.bufferedImage != null);

                    if (ThreadDownloadCapeData.this.imageFound.booleanValue())
                    {
                        ThreadDownloadCapeData.this.callBack.done();
                    }
                    else
                    {
                        ThreadDownloadCapeData.this.callBack.failed("Texture not found");
                    }
                }
            }
        };
        this.imageThread.setDaemon(true);
        this.imageThread.start();
    }
}
