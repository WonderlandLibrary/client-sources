/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.render.NoFov;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.events.SprintEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public abstract class AbstractClientPlayer
extends EntityPlayer {
    public NetworkPlayerInfo field_175157_a;
    private ResourceLocation ofLocationCape = null;
    private static final String __OBFID = "CL_00000935";

    public AbstractClientPlayer(World worldIn, GameProfile p_i45074_2_) {
        super(worldIn, p_i45074_2_);
        String username = p_i45074_2_.getName();
        this.downloadCape(username);
    }

    @Override
    public boolean func_175149_v() {
        Minecraft.getMinecraft();
        NetworkPlayerInfo var1 = Minecraft.getNetHandler().func_175102_a(this.getGameProfile().getId());
        return var1 != null && var1.getGameType() == WorldSettings.GameType.SPECTATOR;
    }

    public boolean hasCape() {
        return this.func_175155_b() != null;
    }

    protected NetworkPlayerInfo func_175155_b() {
        if (this.field_175157_a == null) {
            Minecraft.getMinecraft();
            this.field_175157_a = Minecraft.getNetHandler().func_175102_a(this.getUniqueID());
        }
        return this.field_175157_a;
    }

    public boolean hasSkin() {
        NetworkPlayerInfo var1 = this.func_175155_b();
        return var1 != null && var1.func_178856_e();
    }

    public ResourceLocation getLocationSkin() {
        NetworkPlayerInfo var1 = this.func_175155_b();
        return var1 == null ? DefaultPlayerSkin.func_177334_a(this.getUniqueID()) : var1.func_178837_g();
    }

    public ResourceLocation getLocationCape() {
        if (!Config.isShowCapes()) {
            return null;
        }
        if (this.ofLocationCape != null) {
            return this.ofLocationCape;
        }
        NetworkPlayerInfo var1 = this.func_175155_b();
        return var1 == null ? null : var1.func_178861_h();
    }

    public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
        TextureManager var2 = Minecraft.getMinecraft().getTextureManager();
        ITextureObject var3 = var2.getTexture(resourceLocationIn);
        if (var3 == null) {
            var3 = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.func_177334_a(AbstractClientPlayer.func_175147_b(username)), new ImageBufferDownload());
            var2.loadTexture(resourceLocationIn, var3);
        }
        return (ThreadDownloadImageData)var3;
    }

    public static ResourceLocation getLocationSkin(String username) {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
    }

    public String func_175154_l() {
        NetworkPlayerInfo var1 = this.func_175155_b();
        return var1 == null ? DefaultPlayerSkin.func_177332_b(this.getUniqueID()) : var1.func_178851_f();
    }

    public float func_175156_o() {
        float var1 = 1.0f;
        if (this.capabilities.isFlying) {
            var1 *= 1.1f;
        }
        IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        var1 = (float)((double)var1 * ((var2.getAttributeValue() / (double)this.capabilities.getWalkSpeed() + 1.0) / 2.0));
        if (this.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(var1) || Float.isInfinite(var1)) {
            var1 = 1.0f;
        }
        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
            int var3 = this.getItemInUseDuration();
            float var4 = (float)var3 / 20.0f;
            var4 = var4 > 1.0f ? 1.0f : (var4 *= var4);
            var1 *= 1.0f - var4 * 0.15f;
        }
        if (new NoFov().getInstance().isEnabled()) {
            return this.isSprinting() ? 1.15f : 1.0f;
        }
        return var1;
    }

    private void downloadCape(String username) {
        if (username != null && !username.isEmpty()) {
            username = StringUtils.stripControlCodes(username);
            String ofCapeUrl = "http://s.optifine.net/capes/" + username + ".png";
            MinecraftProfileTexture mpt = new MinecraftProfileTexture(ofCapeUrl, new HashMap());
            final ResourceLocation rl = new ResourceLocation("skins/" + mpt.getHash());
            IImageBuffer iib = new IImageBuffer(){
                ImageBufferDownload ibd = new ImageBufferDownload();

                @Override
                public BufferedImage parseUserSkin(BufferedImage var1) {
                    return AbstractClientPlayer.this.parseCape(var1);
                }

                @Override
                public void func_152634_a() {
                    AbstractClientPlayer.access$1(AbstractClientPlayer.this, rl);
                }
            };
            ThreadDownloadImageData textureCape = new ThreadDownloadImageData(null, mpt.getUrl(), null, iib);
            Minecraft.getMinecraft().getTextureManager().loadTexture(rl, textureCape);
        }
    }

    private BufferedImage parseCape(BufferedImage img) {
        int imageHeight;
        int imageWidth = 64;
        int srcWidth = img.getWidth();
        int srcHeight = img.getHeight();
        for (imageHeight = 32; imageWidth < srcWidth || imageHeight < srcHeight; imageWidth *= 2, imageHeight *= 2) {
        }
        BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
        Graphics g = imgNew.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return imgNew;
    }

    @Override
    public void setSprinting(boolean sprinting) {
        SprintEvent event = new SprintEvent(sprinting);
        event.call();
        sprinting = event.isSprinting();
        super.setSprinting(sprinting);
        IAttributeInstance var2 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (var2.getModifier(sprintingSpeedBoostModifierUUID) != null) {
            var2.removeModifier(sprintingSpeedBoostModifier);
        }
        if (sprinting) {
            var2.applyModifier(sprintingSpeedBoostModifier);
        }
    }

    static /* synthetic */ void access$1(AbstractClientPlayer abstractClientPlayer, ResourceLocation resourceLocation) {
        abstractClientPlayer.ofLocationCape = resourceLocation;
    }

}

