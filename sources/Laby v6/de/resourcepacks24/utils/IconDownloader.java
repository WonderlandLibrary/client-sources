package de.resourcepacks24.utils;

import de.resourcepacks24.main.ResourcePacks24;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class IconDownloader
{
    private HashMap<String, IconDownloader.TheImage> images = new HashMap();
    private HashMap<ResourceLocation, BufferedImage> loadList = new HashMap();
    private ArrayList<String> loading = new ArrayList();
    private long lastCheck = 0L;

    public void drawUrlImage(String location, String url, double x, double y, double width, double height, double size)
    {
        try
        {
            this.checkTextures();

            if (this.images.containsKey(url))
            {
                this.loading.remove(url);

                if (this.images.get(url) != null && ((IconDownloader.TheImage)this.images.get(url)).getLocation() != null && !this.loadList.containsKey(((IconDownloader.TheImage)this.images.get(url)).getLocation()))
                {
                    GL11.glPushMatrix();
                    GL11.glScaled(size, size, size);
                    Minecraft.getMinecraft().getTextureManager().bindTexture(((IconDownloader.TheImage)this.images.get(url)).getLocation());
                    ResourcePacks24.getInstance().getDraw().drawTexturedModalRect(x / size, y / size, (x + width) / size, (y + height) / size);
                    GL11.glPopMatrix();
                }
            }
            else if (!this.loading.contains(url))
            {
                this.loading.add(url);
                new IconDownloader.ImageDownloadThread(location, url);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void checkTextures()
    {
        ArrayList<ResourceLocation> arraylist = new ArrayList();
        arraylist.addAll(this.loadList.keySet());

        for (ResourceLocation resourcelocation : arraylist)
        {
            DynamicTexture dynamictexture = new DynamicTexture((BufferedImage)this.loadList.get(resourcelocation));
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, dynamictexture);
            this.loadList.remove(resourcelocation);
        }

        if (this.lastCheck + 5000L < System.currentTimeMillis())
        {
            this.lastCheck = System.currentTimeMillis();
            ArrayList<String> arraylist1 = new ArrayList();
            ArrayList<String> arraylist2 = new ArrayList();
            arraylist2.addAll(this.images.keySet());

            for (String s : arraylist2)
            {
                IconDownloader.TheImage icondownloader$theimage = (IconDownloader.TheImage)this.images.get(s);

                if (icondownloader$theimage.getLastUse() + 5000L < System.currentTimeMillis())
                {
                    arraylist1.add(s);
                    Minecraft.getMinecraft().getTextureManager().deleteTexture(icondownloader$theimage.getLocation());
                }
            }

            for (String s1 : arraylist1)
            {
                this.images.remove(s1);
            }
        }
    }

    public class ImageDownloadThread extends Thread
    {
        private String url;
        private String location;

        public ImageDownloadThread(String location, String url)
        {
            this.location = location;
            this.url = url;
            this.start();
        }

        public void run()
        {
            try
            {
                ResourceLocation resourcelocation = new ResourceLocation("images/" + this.location);

                if (this.url != null && !this.url.isEmpty())
                {
                    HttpURLConnection httpurlconnection = (HttpURLConnection)(new URL(this.url)).openConnection();
                    httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                    httpurlconnection.setRequestProperty("Cookie", "foo=bar");
                    httpurlconnection.connect();

                    if (httpurlconnection.getResponseCode() / 100 == 2)
                    {
                        BufferedImage bufferedimage = ImageIO.read(httpurlconnection.getInputStream());

                        if (bufferedimage != null)
                        {
                            IconDownloader.this.loadList.put(resourcelocation, bufferedimage);
                            IconDownloader.this.images.put(this.url, IconDownloader.this.new TheImage(resourcelocation));
                            IconDownloader.this.loading.remove(this.url);
                        }
                    }
                }
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }
    }

    class TheImage
    {
        private ResourceLocation location;
        private long lastUse;

        public TheImage(ResourceLocation loc)
        {
            this.location = loc;
            this.lastUse = System.currentTimeMillis();
        }

        public long getLastUse()
        {
            return this.lastUse;
        }

        public ResourceLocation getLocation()
        {
            this.lastUse = System.currentTimeMillis();
            return this.location;
        }
    }
}
