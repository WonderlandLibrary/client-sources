package de.labystudio.capes;

import de.labystudio.capes.downloader.ThreadDownloadCapeData;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Source;
import de.labystudio.utils.Debug;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FilenameUtils;

public class CapeManager
{
    private ArrayList<String> userCapes = new ArrayList();

    public void downloadCape(final AbstractClientPlayer player, boolean refresh, final boolean invert)
    {
        if (player != null)
        {
            String s = player.getNameClear();

            if (s != null && !s.isEmpty() && player.getUniqueID() != null)
            {
                final EnumCapePriority enumcapepriority = this.getCapePriority();
                String s1 = null;

                if (enumcapepriority == EnumCapePriority.OPTIFINE || invert && enumcapepriority == EnumCapePriority.LABYMOD)
                {
                    s1 = Source.url_cape_optifine + s + ".png";
                }

                if ((enumcapepriority == EnumCapePriority.LABYMOD || invert && enumcapepriority == EnumCapePriority.OPTIFINE) && this.isWhitelisted(player.getUniqueID()))
                {
                    s1 = Source.url_cape_labymod + player.getUniqueID();
                }

                if (s1 == null)
                {
                    if (invert)
                    {
                        return;
                    }

                    this.downloadCape(player, false, true);
                    return;
                }

                String s2 = FilenameUtils.getBaseName(s1);
                final ResourceLocation resourcelocation = new ResourceLocation("capes/" + s2);
                TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
                ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);

                if (itextureobject != null && !refresh && itextureobject instanceof ThreadDownloadCapeData)
                {
                    ThreadDownloadCapeData threaddownloadcapedata = (ThreadDownloadCapeData)itextureobject;

                    if (threaddownloadcapedata.imageFound != null)
                    {
                        if (threaddownloadcapedata.imageFound.booleanValue())
                        {
                            player.setLocationOfCape(resourcelocation, enumcapepriority);
                        }

                        return;
                    }
                }

                IImageBuffer iimagebuffer = new IImageBuffer()
                {
                    ImageBufferDownload ibd = new ImageBufferDownload();
                    public BufferedImage parseUserSkin(BufferedImage var1)
                    {
                        return CapeManager.parseCape(var1);
                    }
                    public void skinAvailable()
                    {
                        player.setLocationOfCape(resourcelocation, enumcapepriority);
                    }
                };
                CapeCallback capecallback = new CapeCallback()
                {
                    public void failed(String error)
                    {
                        if (!invert)
                        {
                            CapeManager.this.downloadCape(player, false, true);
                        }
                    }
                    public void done()
                    {
                    }
                };
                ThreadDownloadCapeData threaddownloadcapedata1 = new ThreadDownloadCapeData((File)null, s1, (ResourceLocation)null, iimagebuffer, capecallback);
                texturemanager.loadTexture(resourcelocation, threaddownloadcapedata1);
            }
        }
    }

    public static BufferedImage parseCape(BufferedImage img)
    {
        int i = 64;
        int j = 32;
        int k = img.getWidth();

        for (int l = img.getHeight(); i < k || j < l; j *= 2)
        {
            i *= 2;
        }

        BufferedImage bufferedimage = new BufferedImage(i, j, 2);
        Graphics graphics = bufferedimage.getGraphics();
        graphics.drawImage(img, 0, 0, (ImageObserver)null);
        graphics.dispose();
        return bufferedimage;
    }

    public void setUserCapes(ArrayList<String> userCapes)
    {
        this.userCapes = userCapes;
    }

    public boolean isWhitelisted(UUID uuid)
    {
        Debug.debug(Debug.EnumDebugMode.CAPES, "Is whitelisted? " + uuid);

        for (String s : this.userCapes)
        {
            if (s != null && uuid.toString().startsWith(s) && !s.isEmpty())
            {
                Debug.debug(Debug.EnumDebugMode.CAPES, "IS WHITELISTED because " + uuid.toString() + " starts with " + s);
                return true;
            }
        }

        Debug.debug(Debug.EnumDebugMode.CAPES, "skip..");
        return false;
    }

    public void refresh()
    {
        if (LabyMod.getInstance().isInGame())
        {
            int i = 0;
            ArrayList<EntityPlayer> arraylist = new ArrayList();
            arraylist.addAll(Minecraft.getMinecraft().theWorld.playerEntities);

            for (EntityPlayer entityplayer : arraylist)
            {
                if (entityplayer != null && entityplayer instanceof AbstractClientPlayer)
                {
                    LabyMod.getInstance().getCapeManager().downloadCape((AbstractClientPlayer)entityplayer, true, false);
                    ++i;
                }
            }

            System.out.println("[LabyMod] Refreshed " + i + " mod capes");
        }
    }

    public EnumCapePriority getCapePriority()
    {
        return ConfigManager.settings.capePriority.equals("of") ? EnumCapePriority.OPTIFINE : (ConfigManager.settings.capePriority.equals("original") ? EnumCapePriority.ORIGINAL : EnumCapePriority.LABYMOD);
    }
}
