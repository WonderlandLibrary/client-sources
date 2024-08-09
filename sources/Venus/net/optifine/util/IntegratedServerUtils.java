/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.server.ServerWorld;
import net.optifine.Config;

public class IntegratedServerUtils {
    public static ServerWorld getWorldServer() {
        Minecraft minecraft = Config.getMinecraft();
        ClientWorld clientWorld = minecraft.world;
        if (clientWorld == null) {
            return null;
        }
        if (!minecraft.isIntegratedServerRunning()) {
            return null;
        }
        IntegratedServer integratedServer = minecraft.getIntegratedServer();
        if (integratedServer == null) {
            return null;
        }
        RegistryKey<World> registryKey = clientWorld.getDimensionKey();
        if (registryKey == null) {
            return null;
        }
        try {
            return integratedServer.getWorld(registryKey);
        } catch (NullPointerException nullPointerException) {
            return null;
        }
    }

    public static Entity getEntity(UUID uUID) {
        ServerWorld serverWorld = IntegratedServerUtils.getWorldServer();
        return serverWorld == null ? null : serverWorld.getEntityByUuid(uUID);
    }

    public static TileEntity getTileEntity(BlockPos blockPos) {
        ServerWorld serverWorld = IntegratedServerUtils.getWorldServer();
        if (serverWorld == null) {
            return null;
        }
        IChunk iChunk = serverWorld.getChunkProvider().getChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4, ChunkStatus.FULL, true);
        return iChunk == null ? null : iChunk.getTileEntity(blockPos);
    }
}

