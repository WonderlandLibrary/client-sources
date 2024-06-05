/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import optfine.Config;
import optfine.PlayerConfigurations;
import optfine.Reflector;
import org.apache.commons.io.FilenameUtils;

public abstract class AbstractClientPlayer
extends EntityPlayer {
    private NetworkPlayerInfo playerInfo;
    private ResourceLocation ofLocationCape = null;
    private static final String __OBFID = "CL_00000935";

    public AbstractClientPlayer(World worldIn, GameProfile playerProfile) {
        super(worldIn, playerProfile);
        String s = playerProfile.getName();
        this.downloadCape(s);
        PlayerConfigurations.getPlayerConfiguration(this);
    }

    @Override
    public boolean isSpectator() {
        NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getGameProfile().getId());
        if (networkplayerinfo == null) return false;
        if (networkplayerinfo.getGameType() != WorldSettings.GameType.SPECTATOR) return false;
        return true;
    }

    public boolean hasPlayerInfo() {
        if (this.getPlayerInfo() == null) return false;
        return true;
    }

    protected NetworkPlayerInfo getPlayerInfo() {
        if (this.playerInfo != null) return this.playerInfo;
        this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getUniqueID());
        return this.playerInfo;
    }

    public boolean hasSkin() {
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        if (networkplayerinfo == null) return false;
        if (!networkplayerinfo.hasLocationSkin()) return false;
        return true;
    }

    public ResourceLocation getLocationSkin() {
        ResourceLocation resourceLocation;
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        if (networkplayerinfo == null) {
            resourceLocation = DefaultPlayerSkin.getDefaultSkin(this.getUniqueID());
            return resourceLocation;
        }
        resourceLocation = networkplayerinfo.getLocationSkin();
        return resourceLocation;
    }

    public ResourceLocation getLocationCape() {
        if (!Config.isShowCapes()) {
            return null;
        }
        if (this.ofLocationCape != null) {
            return this.ofLocationCape;
        }
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        if (networkplayerinfo == null) {
            return null;
        }
        ResourceLocation resourceLocation = networkplayerinfo.getLocationCape();
        return resourceLocation;
    }

    public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        ITextureObject object = texturemanager.getTexture(resourceLocationIn);
        if (object != null) return (ThreadDownloadImageData)object;
        object = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.getDefaultSkin(AbstractClientPlayer.getOfflineUUID(username)), new ImageBufferDownload());
        texturemanager.loadTexture(resourceLocationIn, object);
        return (ThreadDownloadImageData)object;
    }

    public static ResourceLocation getLocationSkin(String username) {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
    }

    public String getSkinType() {
        String string;
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        if (networkplayerinfo == null) {
            string = DefaultPlayerSkin.getSkinType(this.getUniqueID());
            return string;
        }
        string = networkplayerinfo.getSkinType();
        return string;
    }

    public float getFovModifier() {
        float f;
        float f2 = 1.0f;
        if (this.capabilities.isFlying) {
            f2 *= 1.1f;
        }
        IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        f2 = (float)((double)f2 * ((iattributeinstance.getAttributeValue() / (double)this.capabilities.getWalkSpeed() + 1.0) / 2.0));
        if (this.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(f2) || Float.isInfinite(f2)) {
            f2 = 1.0f;
        }
        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
            int i = this.getItemInUseDuration();
            float f1 = (float)i / 20.0f;
            f1 = f1 > 1.0f ? 1.0f : (f1 *= f1);
            f2 *= 1.0f - f1 * 0.15f;
        }
        if (Reflector.ForgeHooksClient_getOffsetFOV.exists()) {
            f = Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, this, Float.valueOf(f2));
            return f;
        }
        f = f2;
        return f;
    }

    private void downloadCape(String p_downloadCape_1_) {
        if (p_downloadCape_1_ == null) return;
        if (p_downloadCape_1_.isEmpty()) return;
        p_downloadCape_1_ = StringUtils.stripControlCodes(p_downloadCape_1_);
        String s = "http://s.optifine.net/capes/" + p_downloadCape_1_ + ".png";
        String s1 = FilenameUtils.getBaseName(s);
        final ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s1);
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
        if (itextureobject != null && itextureobject instanceof ThreadDownloadImageData) {
            ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)itextureobject;
            if (threaddownloadimagedata.imageFound != null) {
                if (threaddownloadimagedata.imageFound == false) return;
                this.ofLocationCape = resourcelocation;
                return;
            }
        }
        IImageBuffer iimagebuffer = new IImageBuffer(){
            ImageBufferDownload ibd = new ImageBufferDownload();

            @Override
            public BufferedImage parseUserSkin(BufferedImage image) {
                return AbstractClientPlayer.this.parseCape(image);
            }

            @Override
            public void skinAvailable() {
                AbstractClientPlayer.this.ofLocationCape = resourcelocation;
            }
        };
        ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData(null, s, null, iimagebuffer);
        texturemanager.loadTexture(resourcelocation, threaddownloadimagedata1);
    }

    private BufferedImage parseCape(BufferedImage p_parseCape_1_) {
        int i = 64;
        int j = 32;
        int k = p_parseCape_1_.getWidth();
        int l = p_parseCape_1_.getHeight();
        while (true) {
            if (i >= k && j >= l) {
                BufferedImage bufferedimage = new BufferedImage(i, j, 2);
                Graphics graphics = bufferedimage.getGraphics();
                graphics.drawImage(p_parseCape_1_, 0, 0, null);
                graphics.dispose();
                return bufferedimage;
            }
            i *= 2;
            j *= 2;
        }
    }
}

