package com.example.editme.events;

import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.world.chunk.Chunk;

public class ChunkEvent extends EditmeEvent {
   private Chunk chunk;
   private SPacketChunkData packet;

   public SPacketChunkData getPacket() {
      return this.packet;
   }

   public Chunk getChunk() {
      return this.chunk;
   }

   public ChunkEvent(Chunk var1, SPacketChunkData var2) {
      this.chunk = var1;
      this.packet = var2;
   }
}
