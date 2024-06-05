package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.StairConnectionHandler$1;

final class StairConnectionHandler$StairData {
   private final boolean bottom;
   private final byte shape;
   private final byte type;
   private final BlockFace facing;

   private StairConnectionHandler$StairData(boolean var1, byte var2, byte var3, BlockFace var4) {
      this.bottom = var1;
      this.shape = var2;
      this.type = var3;
      this.facing = var4;
   }

   public boolean isBottom() {
      return this.bottom;
   }

   public byte getShape() {
      return this.shape;
   }

   public byte getType() {
      return this.type;
   }

   public BlockFace getFacing() {
      return this.facing;
   }

   StairConnectionHandler$StairData(boolean var1, byte var2, byte var3, BlockFace var4, StairConnectionHandler$1 var5) {
      this(var1, var2, var3, var4);
   }
}
