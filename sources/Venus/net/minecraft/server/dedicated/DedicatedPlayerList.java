/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.dedicated;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.ServerProperties;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.storage.PlayerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DedicatedPlayerList
extends PlayerList {
    private static final Logger LOGGER = LogManager.getLogger();

    public DedicatedPlayerList(DedicatedServer dedicatedServer, DynamicRegistries.Impl impl, PlayerData playerData) {
        super(dedicatedServer, impl, playerData, dedicatedServer.getServerProperties().maxPlayers);
        ServerProperties serverProperties = dedicatedServer.getServerProperties();
        this.setViewDistance(serverProperties.viewDistance);
        super.setWhiteListEnabled(serverProperties.whitelistEnabled.get());
        this.loadPlayerBanList();
        this.savePlayerBanList();
        this.loadIPBanList();
        this.saveIPBanList();
        this.loadOpsList();
        this.readWhiteList();
        this.saveOpsList();
        if (!this.getWhitelistedPlayers().getSaveFile().exists()) {
            this.saveWhiteList();
        }
    }

    @Override
    public void setWhiteListEnabled(boolean bl) {
        super.setWhiteListEnabled(bl);
        this.getServer().func_213223_o(bl);
    }

    @Override
    public void addOp(GameProfile gameProfile) {
        super.addOp(gameProfile);
        this.saveOpsList();
    }

    @Override
    public void removeOp(GameProfile gameProfile) {
        super.removeOp(gameProfile);
        this.saveOpsList();
    }

    @Override
    public void reloadWhitelist() {
        this.readWhiteList();
    }

    private void saveIPBanList() {
        try {
            this.getBannedIPs().writeChanges();
        } catch (IOException iOException) {
            LOGGER.warn("Failed to save ip banlist: ", (Throwable)iOException);
        }
    }

    private void savePlayerBanList() {
        try {
            this.getBannedPlayers().writeChanges();
        } catch (IOException iOException) {
            LOGGER.warn("Failed to save user banlist: ", (Throwable)iOException);
        }
    }

    private void loadIPBanList() {
        try {
            this.getBannedIPs().readSavedFile();
        } catch (IOException iOException) {
            LOGGER.warn("Failed to load ip banlist: ", (Throwable)iOException);
        }
    }

    private void loadPlayerBanList() {
        try {
            this.getBannedPlayers().readSavedFile();
        } catch (IOException iOException) {
            LOGGER.warn("Failed to load user banlist: ", (Throwable)iOException);
        }
    }

    private void loadOpsList() {
        try {
            this.getOppedPlayers().readSavedFile();
        } catch (Exception exception) {
            LOGGER.warn("Failed to load operators list: ", (Throwable)exception);
        }
    }

    private void saveOpsList() {
        try {
            this.getOppedPlayers().writeChanges();
        } catch (Exception exception) {
            LOGGER.warn("Failed to save operators list: ", (Throwable)exception);
        }
    }

    private void readWhiteList() {
        try {
            this.getWhitelistedPlayers().readSavedFile();
        } catch (Exception exception) {
            LOGGER.warn("Failed to load white-list: ", (Throwable)exception);
        }
    }

    private void saveWhiteList() {
        try {
            this.getWhitelistedPlayers().writeChanges();
        } catch (Exception exception) {
            LOGGER.warn("Failed to save white-list: ", (Throwable)exception);
        }
    }

    @Override
    public boolean canJoin(GameProfile gameProfile) {
        return !this.isWhiteListEnabled() || this.canSendCommands(gameProfile) || this.getWhitelistedPlayers().isWhitelisted(gameProfile);
    }

    @Override
    public DedicatedServer getServer() {
        return (DedicatedServer)super.getServer();
    }

    @Override
    public boolean bypassesPlayerLimit(GameProfile gameProfile) {
        return this.getOppedPlayers().bypassesPlayerLimit(gameProfile);
    }

    @Override
    public MinecraftServer getServer() {
        return this.getServer();
    }
}

