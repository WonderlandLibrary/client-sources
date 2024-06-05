package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;

public class BlockChangeRecord1_8 implements BlockChangeRecord {
   private final byte sectionX;
   private final short y;
   private final byte sectionZ;
   private int blockId;

   public BlockChangeRecord1_8(byte var1, short var2, byte var3, int var4) {
      this.sectionX = var1;
      this.y = var2;
      this.sectionZ = var3;
      this.blockId = var4;
   }

   public BlockChangeRecord1_8(int var1, int var2, int var3, int var4) {
      this((byte)var1, (short)var2, (byte)var3, var4);
   }

   public byte getSectionX() {
      return this.sectionX;
   }

   public byte getSectionY() {
      return (byte)(this.y & 15);
   }

   public short getY(int var1) {
      return this.y;
   }

   public byte getSectionZ() {
      return this.sectionZ;
   }

   public int getBlockId() {
      return this.blockId;
   }

   public void setBlockId(int var1) {
      this.blockId = var1;
   }
}
