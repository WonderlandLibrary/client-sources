package de.labystudio.utils;

import com.google.common.base.Charsets;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Source;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;

public class TextureManager
{
    private HashMap<String, ResourceLocation> images = new HashMap();
    private ArrayList<String> loading = new ArrayList();

    public void drawFileImage(File file, double x, double y, double width, double height, double size)
    {
        if (this.images.containsKey(file.getName()))
        {
            if (this.images.get(file.getName()) != null)
            {
                GL11.glPushMatrix();
                GL11.glScaled(size, size, size);
                LabyMod.getInstance().mc.getTextureManager().bindTexture((ResourceLocation)this.images.get(file.getName()));
                LabyMod.getInstance().draw.drawTexturedModalRect(x / size, y / size, x + width, y + height);
                GL11.glPopMatrix();
            }
        }
        else
        {
            this.loadImage(file);
        }
    }

    public void drawFileImage(File file, double left, double top, double right, double bottom)
    {
        if (this.images.containsKey(file.getName()))
        {
            if (this.images.get(file.getName()) != null)
            {
                LabyMod.getInstance().mc.getTextureManager().bindTexture((ResourceLocation)this.images.get(file.getName()));
                LabyMod.getInstance().draw.drawTexturedModalRect(left, top, right, bottom);
            }
        }
        else
        {
            this.loadImage(file);
        }
    }

    public void drawUrlImage(String location, String url, double x, double y, double width, double height, double size)
    {
        if (this.images.containsKey(url))
        {
            this.loading.remove(url);

            if (this.images.get(url) != null)
            {
                GL11.glPushMatrix();
                GL11.glScaled(size, size, size);
                LabyMod.getInstance().mc.getTextureManager().bindTexture((ResourceLocation)this.images.get(url));
                LabyMod.getInstance().draw.drawTexturedModalRect(x / size, y / size, x + width, y + height);
                GL11.glPopMatrix();
            }
        }
        else if (!this.loading.contains(url))
        {
            this.loading.add(url);
            this.downloadImageFromUrl(location, url);
        }
    }

    public void drawUrlImage(String location, String url, double left, double top, double right, double bottom)
    {
        if (this.images.containsKey(url))
        {
            this.loading.remove(url);

            if (this.images.get(url) != null)
            {
                LabyMod.getInstance().mc.getTextureManager().bindTexture((ResourceLocation)this.images.get(url));
                LabyMod.getInstance().draw.drawTexturedModalRect(left, top, right, bottom);
            }
        }
        else if (!this.loading.contains(url))
        {
            this.loading.add(url);
            this.downloadImageFromUrl(location, url);
        }
    }

    public void drawServerIcon(String ip, double x, double y, double size)
    {
        String s = Source.url_favicon + ip;

        if (this.images.containsKey(s))
        {
            this.loading.remove(ip);

            if (this.images.get(s) != null)
            {
                GL11.glPushMatrix();
                GL11.glScaled(size, size, size);
                LabyMod.getInstance().mc.getTextureManager().bindTexture((ResourceLocation)this.images.get(s));
                double d0 = 31.0D;
                double d1 = 31.0D;
                LabyMod.getInstance().draw.drawTexturedModalRect(x / size, y / size, (x + d1) / size, (y + d0) / size);
                GL11.glPopMatrix();
            }
        }
        else if (!this.loading.contains(ip))
        {
            this.loading.add(ip);

            try
            {
                LogManager.getLogger().info("Loading Server Image of " + ip + " (" + s + ")");
                this.downloadImageFromUrl(ip, s);
            }
            catch (Exception var13)
            {
                LogManager.getLogger().info("Failed to load Server Image of " + ip);
            }
        }
    }

    public void drawPlayerHead(String playerName, double x, double y, double size)
    {
        if (this.images.containsKey(playerName))
        {
            this.loading.remove(playerName);

            if (this.images.get(playerName) != null)
            {
                GL11.glPushMatrix();
                GL11.glScaled(size, size, size);
                LabyMod.getInstance().mc.getTextureManager().bindTexture((ResourceLocation)this.images.get(playerName));
                LabyMod.getInstance().draw.drawTexturedModalRect(x / size, (y - 3.0D) / size, 32.0D, 32.0D, 32.0D, 32.0D);
                LabyMod.getInstance().draw.drawTexturedModalRect(x / size, (y - 3.0D) / size, 160.0D, 32.0D, 32.0D, 32.0D);
                GL11.glPopMatrix();
            }
        }
        else if (!this.loading.contains(playerName))
        {
            this.loading.add(playerName);
            ResourceLocation resourcelocation = new ResourceLocation("images/" + playerName);
            ThreadDownloadImageData threaddownloadimagedata = new ThreadDownloadImageData((File)null, String.format(Source.url_minotar + "/%s.png", new Object[] {StringUtils.stripControlCodes(playerName)}), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(playerName)), new ImageBufferDownload());
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, threaddownloadimagedata);
            this.images.put(playerName, resourcelocation);
        }
    }

    public static UUID getOfflineUUID(String username)
    {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(Charsets.UTF_8));
    }

    private void loadImage(File file)
    {
        ResourceLocation resourcelocation = new ResourceLocation("images/" + file.getName());
        ThreadDownloadImageData threaddownloadimagedata = new ThreadDownloadImageData(file.getAbsoluteFile(), "", (ResourceLocation)null, new IImageBuffer()
        {
            public BufferedImage parseUserSkin(BufferedImage image)
            {
                BufferedImage bufferedimage = TextureManager.this.parseImage(image);
                return bufferedimage == null ? image : bufferedimage;
            }
            public void skinAvailable()
            {
            }
        });
        Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, threaddownloadimagedata);
        this.images.put(file.getName(), resourcelocation);
    }

    private void downloadImageFromUrl(String location, String url)
    {
        try
        {
            ResourceLocation resourcelocation = new ResourceLocation("images/" + location);

            if (url != null && !url.isEmpty())
            {
                BufferedImage bufferedimage = ImageIO.read(new URL(url));

                if (bufferedimage != null)
                {
                    DynamicTexture dynamictexture = new DynamicTexture(bufferedimage);
                    Minecraft.getMinecraft().getTextureManager().deleteTexture(resourcelocation);
                    Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, dynamictexture);
                }

                this.images.put(url, resourcelocation);
            }
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }
    }

    public void renderFrame(BufferedImage read)
    {
        try
        {
            if (read != null)
            {
                ResourceLocation resourcelocation = new ResourceLocation("images/frame");
                DynamicTexture dynamictexture = new DynamicTexture(read);
                Minecraft.getMinecraft().getTextureManager().deleteTexture(resourcelocation);
                Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, dynamictexture);
                LabyMod.getInstance().mc.getTextureManager().bindTexture(resourcelocation);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private BufferedImage parseImage(BufferedImage img)
    {
        int i = 0;
        int j = 0;

        if (img == null)
        {
            return null;
        }
        else
        {
            int k = img.getWidth();

            for (int l = img.getHeight(); i < k || j < l; ++j)
            {
                ++i;
            }

            BufferedImage bufferedimage = new BufferedImage(i, j, 2);
            Graphics graphics = bufferedimage.getGraphics();
            graphics.drawImage(img, 0, 0, (ImageObserver)null);
            graphics.dispose();
            return bufferedimage;
        }
    }
}
