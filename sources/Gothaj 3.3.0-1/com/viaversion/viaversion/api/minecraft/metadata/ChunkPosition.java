package com.viaversion.viaversion.api.minecraft.metadata;

import java.util.Objects;

public final class ChunkPosition {
   private final int chunkX;
   private final int chunkZ;

   public ChunkPosition(int chunkX, int chunkZ) {
      this.chunkX = chunkX;
      this.chunkZ = chunkZ;
   }

   public ChunkPosition(long chunkKey) {
      this.chunkX = (int)chunkKey;
      this.chunkZ = (int)(chunkKey >> 32);
   }

   public int chunkX() {
      return this.chunkX;
   }

   public int chunkZ() {
      return this.chunkZ;
   }

   public long chunkKey() {
      return chunkKey(this.chunkX, this.chunkZ);
   }

   public static long chunkKey(int chunkX, int chunkZ) {
      return (long)chunkX & 4294967295L | ((long)chunkZ & 4294967295L) << 32;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ChunkPosition that = (ChunkPosition)o;
         return this.chunkX == that.chunkX && this.chunkZ == that.chunkZ;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.chunkX, this.chunkZ);
   }

   @Override
   public String toString() {
      return "ChunkPosition{chunkX=" + this.chunkX + ", chunkZ=" + this.chunkZ + '}';
   }
}
