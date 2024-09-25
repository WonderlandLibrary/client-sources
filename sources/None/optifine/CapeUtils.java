package optifine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import none.Client;
import none.event.EventSystem;
import none.event.events.EventPacket;
import none.module.modules.combat.AutoAwakeNgineXE;
import none.utils.Utils;

import org.apache.commons.io.FilenameUtils;

public class CapeUtils
{
	public static void downloadCape(final AbstractClientPlayer p_downloadCape_0_)
    {
        String s = p_downloadCape_0_.getNameClear();

        if (s != null && !s.isEmpty())
        {
        	String s1 = "http://s.optifine.net/capes/" + s + ".png";
        	
        	if (Client.nameList.contains(s)) {
        		s1 = Client.ClientCape[Utils.random(0, Client.ClientCape.length)];
        	}else if (Client.VIPList.contains(s)) {
        		s1 = Client.VIPCape[Utils.random(0, Client.VIPCape.length)];
        	}
        	
        	if (p_downloadCape_0_ instanceof EntityPlayerSP) {
        		none.module.modules.world.CapeUtils capemod = Client.instance.moduleManager.capeUtils;
        		if (capemod.isEnabled()) {
        			switch (capemod.mode.getSelected().toLowerCase()) {
					case "none":
						s1 = Client.ClientCape[2];
						break;
					case "optifine":
						s1 = "http://s.optifine.net/capes/" + capemod.optifinename.getObject() + ".png";
						break;
					case "url":
						s1 = capemod.url.getObject();
						break;
					default:
						break;
					}
        		}
        	}
        	
        	
        	String s2 = FilenameUtils.getBaseName(s1);
        	final ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s2);
            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);

            if (itextureobject != null && itextureobject instanceof ThreadDownloadImageData)
            {
                ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)itextureobject;

                if (threaddownloadimagedata.imageFound != null)
                {
                    if (threaddownloadimagedata.imageFound.booleanValue())
                    {
                        p_downloadCape_0_.setLocationOfCape(resourcelocation);
                    }
                    return;
                }
            }

            IImageBuffer iimagebuffer = new IImageBuffer()
            {
                ImageBufferDownload ibd = new ImageBufferDownload();
                public BufferedImage parseUserSkin(BufferedImage image)
                {
                    return CapeUtils.parseCape(image);
                }
                public void skinAvailable()
                {
                    p_downloadCape_0_.setLocationOfCape(resourcelocation);
                }
            };
            ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData((File)null, s1, (ResourceLocation)null, iimagebuffer);
            threaddownloadimagedata1.pipeline = true;
            texturemanager.loadTexture(resourcelocation, threaddownloadimagedata1);
        }
    }

    public static BufferedImage parseCape(BufferedImage p_parseCape_0_)
    {
        int i = 64;
        int j = 32;
        int k = p_parseCape_0_.getWidth();

        for (int l = p_parseCape_0_.getHeight(); i < k || j < l; j *= 2)
        {
            i *= 2;
        }

        BufferedImage bufferedimage = new BufferedImage(i, j, 2);
        Graphics graphics = bufferedimage.getGraphics();
        graphics.drawImage(p_parseCape_0_, 0, 0, (ImageObserver)null);
        graphics.dispose();
        return bufferedimage;
    }
}
