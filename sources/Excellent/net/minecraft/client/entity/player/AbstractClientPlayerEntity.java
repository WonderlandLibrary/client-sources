package net.minecraft.client.entity.player;

import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import dev.excellent.Constants;
import dev.excellent.Excellent;
import dev.excellent.api.event.impl.player.PlayerLookEvent;
import dev.waveycapes.CapeHolder;
import dev.waveycapes.sim.StickSimulation;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameType;
import net.optifine.Config;
import net.optifine.player.CapeUtils;
import net.optifine.player.PlayerConfigurations;
import net.optifine.reflect.Reflector;
import org.joml.Vector2f;

import javax.annotation.Nullable;

public abstract class AbstractClientPlayerEntity extends PlayerEntity implements CapeHolder {
    private final StickSimulation stickSimulation;
    private NetworkPlayerInfo playerInfo;
    public float rotateElytraX;
    public float rotateElytraY;
    public float rotateElytraZ;
    public final ClientWorld worldClient;
    @Getter
    @Setter
    private ResourceLocation locationOfCape = null;
    @Getter
    @Setter
    private long reloadCapeTimeMs = 0L;
    @Getter
    @Setter
    private boolean elytraOfCape = false;
    @Getter
    private String nameClear = null;
    public ShoulderRidingEntity entityShoulderLeft;
    public ShoulderRidingEntity entityShoulderRight;
    public float capeRotateX;
    public float capeRotateY;
    public float capeRotateZ;
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");

    public AbstractClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, world.func_239140_u_(), world.func_243489_v(), profile);
        this.worldClient = world;
        this.nameClear = profile.getName();

        if (this.nameClear != null && !this.nameClear.isEmpty()) {
            this.nameClear = StringUtils.stripControlCodes(this.nameClear);
        }

        CapeUtils.downloadCape(this);
        PlayerConfigurations.getPlayerConfiguration(this);
        this.stickSimulation = new StickSimulation();
    }

    /**
     * Returns true if the player is in spectator mode.
     */
    public boolean isSpectator() {
        NetworkPlayerInfo networkplayerinfo = Minecraft.getInstance().getConnection().getPlayerInfo(this.getGameProfile().getId());
        return networkplayerinfo != null && networkplayerinfo.getGameType() == GameType.SPECTATOR;
    }

    public boolean isCreative() {
        NetworkPlayerInfo networkplayerinfo = Minecraft.getInstance().getConnection().getPlayerInfo(this.getGameProfile().getId());
        return networkplayerinfo != null && networkplayerinfo.getGameType() == GameType.CREATIVE;
    }

    /**
     * Checks if this instance of AbstractClientPlayer has any associated player data.
     */
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

    /**
     * Returns true if the player has an associated skin.
     */
    public boolean hasSkin() {
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return networkplayerinfo != null && networkplayerinfo.hasLocationSkin();
    }

    /**
     * Returns the ResourceLocation associated with the player's skin
     */
    public ResourceLocation getLocationSkin() {
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return networkplayerinfo == null ? DefaultPlayerSkin.getDefaultSkin(this.getUniqueID()) : networkplayerinfo.getLocationSkin();
    }

    private static final ResourceLocation CAPE_LOCATION = new ResourceLocation(Constants.namespace, "texture/cape.png");

    @Nullable
    public ResourceLocation getLocationCape() {
        if (this.hasCustomCape()) {
            return CAPE_LOCATION;
        }
        if (!Config.isShowCapes()) {
            return null;
        } else {
            if (this.reloadCapeTimeMs != 0L && System.currentTimeMillis() > this.reloadCapeTimeMs) {
                CapeUtils.reloadCape(this);
                this.reloadCapeTimeMs = 0L;
            }

            if (this.locationOfCape != null) {
                return this.locationOfCape;
            } else {
                NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
                return networkplayerinfo == null ? null : networkplayerinfo.getLocationCape();
            }
        }
    }

    @Override
    protected void updateCape() {
        if (this.hasCustomCape()) {
            this.simulate(this);
        }
        super.updateCape();
    }

    public boolean hasCustomCape() {
        return this instanceof ClientPlayerEntity
                || Excellent.getInst().getFriendManager().isFriend(this.getNameClear());
    }

    public boolean isPlayerInfoSet() {
        return this.getPlayerInfo() != null;
    }

    @Nullable

    /**
     * Gets the special Elytra texture for the player.
     */
    public ResourceLocation getLocationElytra() {
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return networkplayerinfo == null ? null : networkplayerinfo.getLocationElytra();
    }

    public static DownloadingTexture getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
        TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
        Texture texture = texturemanager.getTexture(resourceLocationIn);

        if (texture == null) {
            texture = new DownloadingTexture(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(username)), true, null);
            texturemanager.loadTexture(resourceLocationIn, texture);
        }

        return (DownloadingTexture) texture;
    }

    /**
     * Returns true if the username has an associated skin.
     */
    public static ResourceLocation getLocationSkin(String username) {
        return new ResourceLocation("skins/" + Hashing.sha1().hashUnencodedChars(StringUtils.stripControlCodes(username)));
    }

    public String getSkinType() {
        NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
        return networkplayerinfo == null ? DefaultPlayerSkin.getSkinType(this.getUniqueID()) : networkplayerinfo.getSkinType();
    }

    public float getFovModifier() {
        float f = 1.0F;

        if (this.abilities.isFlying) {
            f *= 1.1F;
        }

        f = (float) ((double) f * ((this.getAttributeValue(Attributes.MOVEMENT_SPEED) / (double) this.abilities.getWalkSpeed() + 1.0D) / 2.0D));

        if (this.abilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
            f = 1.0F;
        }

        if (this.isHandActive() && this.getActiveItemStack().getItem() instanceof BowItem) {
            int i = this.getItemInUseMaxCount();
            float f1 = (float) i / 20.0F;

            if (f1 > 1.0F) {
                f1 = 1.0F;
            } else {
                f1 = f1 * f1;
            }

            f *= 1.0F - f1 * 0.15F;
        }

        return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, this, f) : MathHelper.lerp(Minecraft.getInstance().gameSettings.fovScaleEffect, 1.0F, f);
    }

    public boolean hasElytraCape() {
        if (this.hasCustomCape()) {
            return false;
        }

        ResourceLocation resourcelocation = this.getLocationCape();

        if (resourcelocation == null) {
            return false;
        } else {
            return resourcelocation != this.locationOfCape || this.elytraOfCape;
        }
    }

    public Vector3d getLook(float partialTicks) {
        float yaw = this.rotationYaw;
        float pitch = this.rotationPitch;

        PlayerLookEvent playerLookEvent = new PlayerLookEvent(new Vector2f(yaw, pitch));
        Excellent.getInst().getEventBus().handle(playerLookEvent);

        yaw = playerLookEvent.getRotation().x;
        pitch = playerLookEvent.getRotation().y;

        return this.getVectorForRotation(pitch, yaw);
    }

    @Override
    public StickSimulation getSimulation() {
        return this.stickSimulation;
    }
}
