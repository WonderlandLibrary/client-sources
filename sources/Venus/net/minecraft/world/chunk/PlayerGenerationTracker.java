/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.stream.Stream;
import net.minecraft.entity.player.ServerPlayerEntity;

public final class PlayerGenerationTracker {
    private final Object2BooleanMap<ServerPlayerEntity> generatingPlayers = new Object2BooleanOpenHashMap<ServerPlayerEntity>();

    public Stream<ServerPlayerEntity> getGeneratingPlayers(long l) {
        return this.generatingPlayers.keySet().stream();
    }

    public void addPlayer(long l, ServerPlayerEntity serverPlayerEntity, boolean bl) {
        this.generatingPlayers.put(serverPlayerEntity, bl);
    }

    public void removePlayer(long l, ServerPlayerEntity serverPlayerEntity) {
        this.generatingPlayers.removeBoolean(serverPlayerEntity);
    }

    public void disableGeneration(ServerPlayerEntity serverPlayerEntity) {
        this.generatingPlayers.replace(serverPlayerEntity, true);
    }

    public void enableGeneration(ServerPlayerEntity serverPlayerEntity) {
        this.generatingPlayers.replace(serverPlayerEntity, false);
    }

    public boolean cannotGenerateChunks(ServerPlayerEntity serverPlayerEntity) {
        return this.generatingPlayers.getOrDefault((Object)serverPlayerEntity, true);
    }

    public boolean canGeneratePlayer(ServerPlayerEntity serverPlayerEntity) {
        return this.generatingPlayers.getBoolean(serverPlayerEntity);
    }

    public void updatePlayerPosition(long l, long l2, ServerPlayerEntity serverPlayerEntity) {
    }
}

