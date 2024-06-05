package com.viaversion.viaversion.api.minecraft;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;

public class BlockChangeRecord1_16_2 implements BlockChangeRecord {
   private final byte sectionX;
   private final byte sectionY;
   private final byte sectionZ;
   private int blockId;

   public BlockChangeRecord1_16_2(byte var1, byte var2, byte var3, int var4) {
      this.sectionX = var1;
      this.sectionY = var2;
      this.sectionZ = var3;
      this.blockId = var4;
   }

   public BlockChangeRecord1_16_2(int var1, int var2, int var3, int var4) {
      this((byte)var1, (byte)var2, (byte)var3, var4);
   }

   public byte getSectionX() {
      return this.sectionX;
   }

   public byte getSectionY() {
      return this.sectionY;
   }

   public byte getSectionZ() {
      return this.sectionZ;
   }

   public short getY(int var1) {
      String[] var2 = Position.b();
      Preconditions.checkArgument(var1 >= 0 && var1 < 16, "Invalid chunkSectionY: " + var1);
      return (short)((var1 << 4) + this.sectionY);
   }

   public int getBlockId() {
      return this.blockId;
   }

   public void setBlockId(int var1) {
      this.blockId = var1;
   }
}
