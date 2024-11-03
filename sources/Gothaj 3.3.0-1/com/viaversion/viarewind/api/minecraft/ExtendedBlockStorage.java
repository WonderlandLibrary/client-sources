package com.viaversion.viarewind.api.minecraft;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;

public class ExtendedBlockStorage {
   private final byte[] blockLSBArray = new byte[4096];
   private final NibbleArray blockMetadataArray = new NibbleArray(this.blockLSBArray.length);
   private final NibbleArray blockLightArray = new NibbleArray(this.blockLSBArray.length);
   private NibbleArray blockMSBArray;
   private NibbleArray skyLightArray;

   public ExtendedBlockStorage(boolean skylight) {
      if (skylight) {
         this.skyLightArray = new NibbleArray(this.blockLSBArray.length);
      }
   }

   public void setBlockId(int x, int y, int z, int value) {
      this.blockLSBArray[ChunkSection.index(x, y, z)] = (byte)(value & 0xFF);
      if (value > 255) {
         this.getOrCreateBlockMSBArray().set(x, y, z, (value & 3840) >> 8);
      } else if (this.blockMSBArray != null) {
         this.blockMSBArray.set(x, y, z, 0);
      }
   }

   public void setBlockMetadata(int x, int y, int z, int value) {
      this.blockMetadataArray.set(x, y, z, value);
   }

   public boolean hasBlockMSBArray() {
      return this.blockMSBArray != null;
   }

   public byte[] getBlockLSBArray() {
      return this.blockLSBArray;
   }

   public NibbleArray getOrCreateBlockMSBArray() {
      return this.blockMSBArray == null ? (this.blockMSBArray = new NibbleArray(this.blockLSBArray.length)) : this.blockMSBArray;
   }

   public NibbleArray getBlockMetadataArray() {
      return this.blockMetadataArray;
   }

   public NibbleArray getBlockLightArray() {
      return this.blockLightArray;
   }

   public NibbleArray getSkyLightArray() {
      return this.skyLightArray;
   }
}
