/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.entity.player;

import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.render.Cape;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.texture.DownloadingTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameType;
import net.optifine.Config;
import net.optifine.player.CapeUtils;
import net.optifine.player.PlayerConfigurations;
import net.optifine.reflect.Reflector;

public abstract class AbstractClientPlayerEntity
extends PlayerEntity {
    private NetworkPlayerInfo playerInfo;
    public float rotateElytraX;
    public float rotateElytraY;
    public float rotateElytraZ;
    public final ClientWorld worldClient;
    private ResourceLocation locationOfCape = null;
    private long reloadCapeTimeMs = 0L;
    private boolean elytraOfCape = false;
    private String nameClear = null;
    public ShoulderRidingEntity entityShoulderLeft;
    public ShoulderRidingEntity entityShoulderRight;
    public float capeRotateX;
    public float capeRotateY;
    public float capeRotateZ;
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
    private static final ResourceLocation Cape = new ResourceLocation("venusfr/images/cape.png");

    public AbstractClientPlayerEntity(ClientWorld clientWorld, GameProfile gameProfile) {
        super(clientWorld, clientWorld.func_239140_u_(), clientWorld.func_243489_v(), gameProfile);
        this.worldClient = clientWorld;
        this.nameClear = gameProfile.getName();
        if (this.nameClear != null && !this.nameClear.isEmpty()) {
            this.nameClear = StringUtils.stripControlCodes(this.nameClear);
        }
        CapeUtils.downloadCape(this);
        PlayerConfigurations.getPlayerConfiguration(this);
    }

    @Override
    public boolean isSpectator() {
        NetworkPlayerInfo networkPlayerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(this.getGameProfile().getId());
        return networkPlayerInfo != null && networkPlayerInfo.getGameType() == GameType.SPECTATOR;
    }

    @Override
    public boolean isCreative() {
        NetworkPlayerInfo networkPlayerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(this.getGameProfile().getId());
        return networkPlayerInfo != null && networkPlayerInfo.getGameType() == GameType.CREATIVE;
    }

    public boolean hasPlayerInfo() {
        return this.getPlayerInfo() != null;
    }

    @Nullable
    protected NetworkPlayerInfo getPlayerInfo() {
        if (this.playerInfo == null) {
            this.playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(this.getUniqueID());
        }
        return this.playerInfo;
    }

    public boolean hasSkin() {
        NetworkPlayerInfo networkPlayerInfo = this.getPlayerInfo();
        return networkPlayerInfo != null && networkPlayerInfo.hasLocationSkin();
    }

    public ResourceLocation getLocationSkin() {
        NetworkPlayerInfo networkPlayerInfo = this.getPlayerInfo();
        return networkPlayerInfo == null ? DefaultPlayerSkin.getDefaultSkin(this.getUniqueID()) : networkPlayerInfo.getLocationSkin();
    }

    @Nullable
    public ResourceLocation getLocationCape() {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        Cape cape = functionRegistry.getCape();
        if (this instanceof ClientPlayerEntity && cape.isState()) {
            return Cape;
        }
        if (!Config.isShowCapes()) {
            return null;
        }
        if (this.reloadCapeTimeMs != 0L && System.currentTimeMillis() > this.reloadCapeTimeMs) {
            CapeUtils.reloadCape(this);
            this.reloadCapeTimeMs = 0L;
        }
        if (this.locationOfCape != null) {
            return this.locationOfCape;
        }
        NetworkPlayerInfo networkPlayerInfo = this.getPlayerInfo();
        return networkPlayerInfo == null ? null : networkPlayerInfo.getLocationCape();
    }

    public boolean isPlayerInfoSet() {
        return this.getPlayerInfo() != null;
    }

    @Nullable
    public ResourceLocation getLocationElytra() {
        NetworkPlayerInfo networkPlayerInfo = this.getPlayerInfo();
        return networkPlayerInfo == null ? null : networkPlayerInfo.getLocationElytra();
    }

    public static DownloadingTexture getDownloadImageSkin(ResourceLocation resourceLocation, String string) {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        Texture texture = textureManager.getTexture(resourceLocation);
        if (texture == null) {
            texture = new DownloadingTexture(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(string)), DefaultPlayerSkin.getDefaultSkin(AbstractClientPlayerEntity.getOfflineUUID(string)), true, null);
            textureManager.loadTexture(resourceLocation, texture);
        }
        return (DownloadingTexture)texture;
    }

    public static ResourceLocation getLocationSkin(String string) {
        return new ResourceLocation("skins/" + Hashing.sha1().hashUnencodedChars(StringUtils.stripControlCodes(string)));
    }

    public String getSkinType() {
        NetworkPlayerInfo networkPlayerInfo = this.getPlayerInfo();
        return networkPlayerInfo == null ? DefaultPlayerSkin.getSkinType(this.getUniqueID()) : networkPlayerInfo.getSkinType();
    }

    public float getFovModifier() {
        float f = 1.0f;
        if (this.abilities.isFlying) {
            f *= 1.1f;
        }
        f = (float)((double)f * ((this.getAttributeValue(Attributes.MOVEMENT_SPEED) / (double)this.abilities.getWalkSpeed() + 1.0) / 2.0));
        if (this.abilities.getWalkSpeed() == 0.0f || Float.isNaN(f) || Float.isInfinite(f)) {
            f = 1.0f;
        }
        if (this.isHandActive() && this.getActiveItemStack().getItem() instanceof BowItem) {
            int n = this.getItemInUseMaxCount();
            float f2 = (float)n / 20.0f;
            f2 = f2 > 1.0f ? 1.0f : (f2 *= f2);
            f *= 1.0f - f2 * 0.15f;
        }
        return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, this, Float.valueOf(f)) : MathHelper.lerp(Minecraft.getInstance().gameSettings.fovScaleEffect, 1.0f, f);
    }

    public String getNameClear() {
        return this.nameClear;
    }

    public ResourceLocation getLocationOfCape() {
        return this.locationOfCape;
    }

    public void setLocationOfCape(ResourceLocation resourceLocation) {
        this.locationOfCape = resourceLocation;
    }

    public boolean hasElytraCape() {
        ResourceLocation resourceLocation = this.getLocationCape();
        if (resourceLocation == null) {
            return true;
        }
        return resourceLocation == this.locationOfCape ? this.elytraOfCape : true;
    }

    public void setElytraOfCape(boolean bl) {
        this.elytraOfCape = bl;
    }

    public boolean isElytraOfCape() {
        return this.elytraOfCape;
    }

    public long getReloadCapeTimeMs() {
        return this.reloadCapeTimeMs;
    }

    public void setReloadCapeTimeMs(long l) {
        this.reloadCapeTimeMs = l;
    }
}

