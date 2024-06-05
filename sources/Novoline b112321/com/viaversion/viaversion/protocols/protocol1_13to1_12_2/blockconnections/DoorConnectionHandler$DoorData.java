package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.DoorConnectionHandler$1;

final class DoorConnectionHandler$DoorData {
   private final boolean lower;
   private final boolean rightHinge;
   private final boolean powered;
   private final boolean open;
   private final BlockFace facing;
   private final int type;

   private DoorConnectionHandler$DoorData(boolean var1, boolean var2, boolean var3, boolean var4, BlockFace var5, int var6) {
      this.lower = var1;
      this.rightHinge = var2;
      this.powered = var3;
      this.open = var4;
      this.facing = var5;
      this.type = var6;
   }

   public boolean isLower() {
      return this.lower;
   }

   public boolean isRightHinge() {
      return this.rightHinge;
   }

   public boolean isPowered() {
      return this.powered;
   }

   public boolean isOpen() {
      return this.open;
   }

   public BlockFace getFacing() {
      return this.facing;
   }

   public int getType() {
      return this.type;
   }

   DoorConnectionHandler$DoorData(boolean var1, boolean var2, boolean var3, boolean var4, BlockFace var5, int var6, DoorConnectionHandler$1 var7) {
      this(var1, var2, var3, var4, var5, var6);
   }
}
