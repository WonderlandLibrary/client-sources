/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 */
package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BukkitBlockConnectionProvider
extends BlockConnectionProvider {
    private Chunk lastChunk;

    @Override
    public int getWorldBlockData(UserConnection userConnection, int n, int n2, int n3) {
        int n4;
        int n5;
        World world;
        UUID uUID = userConnection.getProtocolInfo().getUuid();
        Player player = Bukkit.getPlayer((UUID)uUID);
        if (player != null && (world = player.getWorld()).isChunkLoaded(n5 = n >> 4, n4 = n3 >> 4)) {
            Chunk chunk = this.getChunk(world, n5, n4);
            Block block = chunk.getBlock(n, n2, n3);
            return block.getTypeId() << 4 | block.getData();
        }
        return 1;
    }

    public Chunk getChunk(World world, int n, int n2) {
        if (this.lastChunk != null && this.lastChunk.getWorld().equals(world) && this.lastChunk.getX() == n && this.lastChunk.getZ() == n2) {
            return this.lastChunk;
        }
        this.lastChunk = world.getChunkAt(n, n2);
        return this.lastChunk;
    }
}

