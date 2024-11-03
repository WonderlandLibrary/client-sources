package com.viaversion.viaversion.api.minecraft;

import java.util.EnumMap;
import java.util.Map;

public enum BlockFace {
   NORTH((byte)0, (byte)0, (byte)-1, BlockFace.EnumAxis.Z),
   SOUTH((byte)0, (byte)0, (byte)1, BlockFace.EnumAxis.Z),
   EAST((byte)1, (byte)0, (byte)0, BlockFace.EnumAxis.X),
   WEST((byte)-1, (byte)0, (byte)0, BlockFace.EnumAxis.X),
   TOP((byte)0, (byte)1, (byte)0, BlockFace.EnumAxis.Y),
   BOTTOM((byte)0, (byte)-1, (byte)0, BlockFace.EnumAxis.Y);

   public static final BlockFace[] HORIZONTAL = new BlockFace[]{NORTH, SOUTH, EAST, WEST};
   private static final Map<BlockFace, BlockFace> opposites = new EnumMap<>(BlockFace.class);
   private final byte modX;
   private final byte modY;
   private final byte modZ;
   private final BlockFace.EnumAxis axis;

   private BlockFace(byte modX, byte modY, byte modZ, BlockFace.EnumAxis axis) {
      this.modX = modX;
      this.modY = modY;
      this.modZ = modZ;
      this.axis = axis;
   }

   public BlockFace opposite() {
      return opposites.get(this);
   }

   public byte modX() {
      return this.modX;
   }

   public byte modY() {
      return this.modY;
   }

   public byte modZ() {
      return this.modZ;
   }

   public BlockFace.EnumAxis axis() {
      return this.axis;
   }

   @Deprecated
   public byte getModX() {
      return this.modX;
   }

   @Deprecated
   public byte getModY() {
      return this.modY;
   }

   @Deprecated
   public byte getModZ() {
      return this.modZ;
   }

   @Deprecated
   public BlockFace.EnumAxis getAxis() {
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

   public static enum EnumAxis {
      X,
      Y,
      Z;
   }
}
