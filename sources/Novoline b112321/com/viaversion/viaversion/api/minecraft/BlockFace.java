package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.minecraft.BlockFace$EnumAxis;
import java.util.HashMap;
import java.util.Map;

public enum BlockFace {
   NORTH((byte)0, (byte)0, (byte)-1, BlockFace$EnumAxis.Z),
   SOUTH((byte)0, (byte)0, (byte)1, BlockFace$EnumAxis.Z),
   EAST((byte)1, (byte)0, (byte)0, BlockFace$EnumAxis.X),
   WEST((byte)-1, (byte)0, (byte)0, BlockFace$EnumAxis.X),
   TOP((byte)0, (byte)1, (byte)0, BlockFace$EnumAxis.Y),
   BOTTOM((byte)0, (byte)-1, (byte)0, BlockFace$EnumAxis.Y);

   private static final Map opposites = new HashMap();
   private final byte modX;
   private final byte modY;
   private final byte modZ;
   private final BlockFace$EnumAxis axis;
   private static final BlockFace[] $VALUES = new BlockFace[]{NORTH, SOUTH, EAST, WEST, TOP, BOTTOM};

   private BlockFace(byte var3, byte var4, byte var5, BlockFace$EnumAxis var6) {
      this.modX = var3;
      this.modY = var4;
      this.modZ = var5;
      this.axis = var6;
   }

   public BlockFace opposite() {
      return (BlockFace)opposites.get(this);
   }

   public byte getModX() {
      return this.modX;
   }

   public byte getModY() {
      return this.modY;
   }

   public byte getModZ() {
      return this.modZ;
   }

   public BlockFace$EnumAxis getAxis() {
      return this.axis;
   }

   static {
      opposites.put(NORTH, SOUTH);
      opposites.put(SOUTH, NORTH);
      opposites.put(EAST, WEST);
      opposites.put(WEST, EAST);
      opposites.put(TOP, BOTTOM);
      opposites.put(BOTTOM, TOP);
   }
}
