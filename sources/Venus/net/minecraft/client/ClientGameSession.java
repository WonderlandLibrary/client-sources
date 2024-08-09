/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import com.mojang.bridge.game.GameSession;
import java.util.UUID;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.world.ClientWorld;

public class ClientGameSession
implements GameSession {
    private final int playerCount;
    private final boolean remoteServer;
    private final String difficulty;
    private final String gameMode;
    private final UUID sessionId;

    public ClientGameSession(ClientWorld clientWorld, ClientPlayerEntity clientPlayerEntity, ClientPlayNetHandler clientPlayNetHandler) {
        this.playerCount = clientPlayNetHandler.getPlayerInfoMap().size();
        this.remoteServer = !clientPlayNetHandler.getNetworkManager().isLocalChannel();
        this.difficulty = clientWorld.getDifficulty().getTranslationKey();
        NetworkPlayerInfo networkPlayerInfo = clientPlayNetHandler.getPlayerInfo(clientPlayerEntity.getUniqueID());
        this.gameMode = networkPlayerInfo != null ? networkPlayerInfo.getGameType().getName() : "unknown";
        this.sessionId = clientPlayNetHandler.getSessionId();
    }

    @Override
    public int getPlayerCount() {
        return this.playerCount;
    }

    @Override
    public boolean isRemoteServer() {
        return this.remoteServer;
    }

    @Override
    public String getDifficulty() {
        return this.difficulty;
    }

    @Override
    public String getGameMode() {
        return this.gameMode;
    }

    @Override
    public UUID getSessionId() {
        return this.sessionId;
    }
}

