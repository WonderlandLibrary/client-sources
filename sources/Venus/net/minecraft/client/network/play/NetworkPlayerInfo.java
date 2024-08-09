/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.network.play;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameType;

public class NetworkPlayerInfo {
    private final GameProfile gameProfile;
    private final Map<MinecraftProfileTexture.Type, ResourceLocation> playerTextures = Maps.newEnumMap(MinecraftProfileTexture.Type.class);
    private GameType gameType;
    private int responseTime;
    private boolean playerTexturesLoaded;
    @Nullable
    private String skinType;
    @Nullable
    private ITextComponent displayName;
    private int lastHealth;
    private int displayHealth;
    private long lastHealthTime;
    private long healthBlinkTime;
    private long renderVisibilityId;

    public NetworkPlayerInfo(SPlayerListItemPacket.AddPlayerData addPlayerData) {
        this.gameProfile = addPlayerData.getProfile();
        this.gameType = addPlayerData.getGameMode();
        this.responseTime = addPlayerData.getPing();
        this.displayName = addPlayerData.getDisplayName();
    }

    public GameProfile getGameProfile() {
        return this.gameProfile;
    }

    @Nullable
    public GameType getGameType() {
        return this.gameType;
    }

    protected void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public int getResponseTime() {
        return this.responseTime;
    }

    protected void setResponseTime(int n) {
        this.responseTime = n;
    }

    public boolean hasLocationSkin() {
        return this.getLocationSkin() != null;
    }

    public String getSkinType() {
        return this.skinType == null ? DefaultPlayerSkin.getSkinType(this.gameProfile.getId()) : this.skinType;
    }

    public ResourceLocation getLocationSkin() {
        this.loadPlayerTextures();
        return MoreObjects.firstNonNull(this.playerTextures.get((Object)MinecraftProfileTexture.Type.SKIN), DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
    }

    @Nullable
    public ResourceLocation getLocationCape() {
        this.loadPlayerTextures();
        return this.playerTextures.get((Object)MinecraftProfileTexture.Type.CAPE);
    }

    @Nullable
    public ResourceLocation getLocationElytra() {
        this.loadPlayerTextures();
        return this.playerTextures.get((Object)MinecraftProfileTexture.Type.ELYTRA);
    }

    @Nullable
    public ScorePlayerTeam getPlayerTeam() {
        return Minecraft.getInstance().world.getScoreboard().getPlayersTeam(this.getGameProfile().getName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void loadPlayerTextures() {
        NetworkPlayerInfo networkPlayerInfo = this;
        synchronized (networkPlayerInfo) {
            if (!this.playerTexturesLoaded) {
                this.playerTexturesLoaded = true;
                Minecraft.getInstance().getSkinManager().loadProfileTextures(this.gameProfile, this::lambda$loadPlayerTextures$0, false);
            }
        }
    }

    public void setDisplayName(@Nullable ITextComponent iTextComponent) {
        this.displayName = iTextComponent;
    }

    @Nullable
    public ITextComponent getDisplayName() {
        return this.displayName;
    }

    public int getLastHealth() {
        return this.lastHealth;
    }

    public void setLastHealth(int n) {
        this.lastHealth = n;
    }

    public int getDisplayHealth() {
        return this.displayHealth;
    }

    public void setDisplayHealth(int n) {
        this.displayHealth = n;
    }

    public long getLastHealthTime() {
        return this.lastHealthTime;
    }

    public void setLastHealthTime(long l) {
        this.lastHealthTime = l;
    }

    public long getHealthBlinkTime() {
        return this.healthBlinkTime;
    }

    public void setHealthBlinkTime(long l) {
        this.healthBlinkTime = l;
    }

    public long getRenderVisibilityId() {
        return this.renderVisibilityId;
    }

    public void setRenderVisibilityId(long l) {
        this.renderVisibilityId = l;
    }

    private void lambda$loadPlayerTextures$0(MinecraftProfileTexture.Type type, ResourceLocation resourceLocation, MinecraftProfileTexture minecraftProfileTexture) {
        this.playerTextures.put(type, resourceLocation);
        if (type == MinecraftProfileTexture.Type.SKIN) {
            this.skinType = minecraftProfileTexture.getMetadata("model");
            if (this.skinType == null) {
                this.skinType = "default";
            }
        }
    }
}

