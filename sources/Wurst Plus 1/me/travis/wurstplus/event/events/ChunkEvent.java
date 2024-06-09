/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketChunkData
 *  net.minecraft.world.chunk.Chunk
 */
package me.travis.wurstplus.event.events;

import me.travis.wurstplus.event.wurstplusEvent;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.world.chunk.Chunk;

public class ChunkEvent extends wurstplusEvent {
    private Chunk chunk;
    private SPacketChunkData packet;

    public ChunkEvent(Chunk chunk, SPacketChunkData packet) {
        this.chunk = chunk;
        this.packet = packet;
    }

    public Chunk getChunk() {
        return this.chunk;
    }

    public SPacketChunkData getPacket() {
        return this.packet;
    }
}

