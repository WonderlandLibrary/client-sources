package net.minecraft.client.entity;

import net.minecraft.entity.player.*;
import net.minecraft.client.network.*;
import com.mojang.authlib.*;
import net.minecraft.client.*;
import net.minecraft.world.*;
import net.minecraft.client.resources.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import optfine.*;
import net.minecraft.entity.ai.attributes.*;
import org.apache.commons.io.*;
import java.awt.image.*;
import java.awt.*;

public abstract class AbstractClientPlayer extends EntityPlayer
{
    private NetworkPlayerInfo playerInfo;
    private ResourceLocation ofLocationCape;
    private static final String __OBFID = "CL_00000935";
    
    public AbstractClientPlayer(final World worldIn, final GameProfile playerProfile) {
        super(worldIn, playerProfile);
        this.ofLocationCape = null;
        final String s = playerProfile.getName();
        this.downloadCape(s);
        PlayerConfigurations.getPlayerConfiguration(this);
    }
    
    @Override
    public boolean isSpectator() {
        final NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getGameProfile().getId());
        return networkplayerinfo != null && networkplayerinfo.getGameType() == WorldSettings.GameType.SPECTATOR;
    }
    
    public boolean hasPlayerInfo() {
        return this.getPlayerInfo() != null;
    }
    
    protected NetworkPlayerInfo getPlayerInfo() {
        if (this.playerInfo == null) {
            this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getUniqueID());
        }
        return this.playerInfo;
    }
    
    public boolean hasSkin() {
        final NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return networkplayerinfo != null && networkplayerinfo.hasLocationSkin();
    }
    
    public ResourceLocation getLocationSkin() {
        final NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return (networkplayerinfo == null) ? DefaultPlayerSkin.getDefaultSkin(this.getUniqueID()) : networkplayerinfo.getLocationSkin();
    }
    
    public ResourceLocation getLocationCape() {
        if (!Config.isShowCapes()) {
            return null;
        }
        if (this.ofLocationCape != null) {
            return this.ofLocationCape;
        }
        final NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return (networkplayerinfo == null) ? null : networkplayerinfo.getLocationCape();
    }
    
    public static ThreadDownloadImageData getDownloadImageSkin(final ResourceLocation resourceLocationIn, final String username) {
        final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        Object object = texturemanager.getTexture(resourceLocationIn);
        if (object == null) {
            object = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getOfflineUUID(username)), new ImageBufferDownload());
            texturemanager.loadTexture(resourceLocationIn, (ITextureObject)object);
        }
        return (ThreadDownloadImageData)object;
    }
    
    public static ResourceLocation getLocationSkin(final String username) {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
    }
    
    public String getSkinType() {
        final NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return (networkplayerinfo == null) ? DefaultPlayerSkin.getSkinType(this.getUniqueID()) : networkplayerinfo.getSkinType();
    }
    
    public float getFovModifier() {
        float f = 1.0f;
        if (this.capabilities.isFlying) {
            f *= 1.1f;
        }
        final IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        f *= (float)((iattributeinstance.getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0) / 2.0);
        if (this.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(f) || Float.isInfinite(f)) {
            f = 1.0f;
        }
        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
            final int i = this.getItemInUseDuration();
            float f2 = i / 20.0f;
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            else {
                f2 *= f2;
            }
            f *= 1.0f - f2 * 0.15f;
        }
        return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, this, f) : f;
    }
    
    private void downloadCape(String p_downloadCape_1_) {
        if (p_downloadCape_1_ != null && !p_downloadCape_1_.isEmpty()) {
            p_downloadCape_1_ = StringUtils.stripControlCodes(p_downloadCape_1_);
            final String s = "http://s.optifine.net/capes/" + p_downloadCape_1_ + ".png";
            final String s2 = FilenameUtils.getBaseName(s);
            final ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s2);
            final TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            final ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
            if (itextureobject != null && itextureobject instanceof ThreadDownloadImageData) {
                final ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)itextureobject;
                if (threaddownloadimagedata.imageFound != null) {
                    if (threaddownloadimagedata.imageFound) {
                        this.ofLocationCape = resourcelocation;
                    }
                    return;
                }
            }
            final IImageBuffer iimagebuffer = new IImageBuffer() {
                ImageBufferDownload ibd = new ImageBufferDownload();
                
                @Override
                public BufferedImage parseUserSkin(final BufferedImage image) {
                    return AbstractClientPlayer.this.parseCape(image);
                }
                
                @Override
                public void skinAvailable() {
                    AbstractClientPlayer.this.ofLocationCape = resourcelocation;
                }
            };
            final ThreadDownloadImageData threaddownloadimagedata2 = new ThreadDownloadImageData(null, s, null, iimagebuffer);
            texturemanager.loadTexture(resourcelocation, threaddownloadimagedata2);
        }
    }
    
    private BufferedImage parseCape(final BufferedImage p_parseCape_1_) {
        int i = 64;
        int j = 32;
        for (int k = p_parseCape_1_.getWidth(), l = p_parseCape_1_.getHeight(); i < k || j < l; i *= 2, j *= 2) {}
        final BufferedImage bufferedimage = new BufferedImage(i, j, 2);
        final Graphics graphics = bufferedimage.getGraphics();
        graphics.drawImage(p_parseCape_1_, 0, 0, null);
        graphics.dispose();
        return bufferedimage;
    }
}
