package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.EnumFacing$AxisDirection;
import net.minecraft.util.EnumFacing$EnumFacing$1;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

public enum EnumFacing implements IStringSerializable {
   DOWN("DOWN", 0, 0, 1, -1, "down", EnumFacing$AxisDirection.NEGATIVE, EnumFacing$Axis.Y, new Vec3i(0, -1, 0)),
   UP("UP", 1, 1, 0, -1, "up", EnumFacing$AxisDirection.POSITIVE, EnumFacing$Axis.Y, new Vec3i(0, 1, 0)),
   NORTH("NORTH", 2, 2, 3, 2, "north", EnumFacing$AxisDirection.NEGATIVE, EnumFacing$Axis.Z, new Vec3i(0, 0, -1)),
   SOUTH("SOUTH", 3, 3, 2, 0, "south", EnumFacing$AxisDirection.POSITIVE, EnumFacing$Axis.Z, new Vec3i(0, 0, 1)),
   WEST("WEST", 4, 4, 5, 1, "west", EnumFacing$AxisDirection.NEGATIVE, EnumFacing$Axis.X, new Vec3i(-1, 0, 0)),
   EAST("EAST", 5, 5, 4, 3, "east", EnumFacing$AxisDirection.POSITIVE, EnumFacing$Axis.X, new Vec3i(1, 0, 0));

   private final int index;
   private final int opposite;
   private final int horizontalIndex;
   private final String name;
   private final EnumFacing$Axis axis;
   private final EnumFacing$AxisDirection axisDirection;
   private final Vec3i directionVec;
   public static final EnumFacing[] VALUES = new EnumFacing[6];
   private static final EnumFacing[] HORIZONTALS = new EnumFacing[4];
   private static final Map NAME_LOOKUP = Maps.newHashMap();
   private static final EnumFacing[] $VALUES = new EnumFacing[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
   private static final String k = "CL_00001201";
   private static final EnumFacing[] $VALUES$ = new EnumFacing[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};

   private EnumFacing(String var3, int var4, int var5, int var6, int var7, String var8, EnumFacing$AxisDirection var9, EnumFacing$Axis var10, Vec3i var11) {
      this.index = var5;
      this.horizontalIndex = var7;
      this.opposite = var6;
      this.name = var8;
      this.axis = var10;
      this.axisDirection = var9;
      this.directionVec = var11;
   }

   public int getIndex() {
      return this.index;
   }

   public int getHorizontalIndex() {
      return this.horizontalIndex;
   }

   public EnumFacing$AxisDirection getAxisDirection() {
      return this.axisDirection;
   }

   public EnumFacing getOpposite() {
      return VALUES[this.opposite];
   }

   public EnumFacing rotateAround(EnumFacing$Axis var1) {
      switch(EnumFacing$EnumFacing$1.field_179515_a[var1.ordinal()]) {
      case 1:
         if(this != WEST && this != EAST) {
            return this.rotateX();
         }

         return this;
      case 2:
         if(this != UP && this != DOWN) {
            return this.rotateY();
         }

         return this;
      case 3:
         if(this != NORTH && this != SOUTH) {
            return this.rotateZ();
         }

         return this;
      default:
         throw new IllegalStateException("Unable to get CW facing for axis " + var1);
      }
   }

   public EnumFacing rotateY() {
      switch(EnumFacing$EnumFacing$1.field_179513_b[this.ordinal()]) {
      case 1:
         return EAST;
      case 2:
         return SOUTH;
      case 3:
         return WEST;
      case 4:
         return NORTH;
      default:
         throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      }
   }

   private EnumFacing rotateX() {
      switch(EnumFacing$EnumFacing$1.field_179513_b[this.ordinal()]) {
      case 1:
         return DOWN;
      case 2:
      case 4:
      default:
         throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      case 3:
         return UP;
      case 5:
         return NORTH;
      case 6:
         return SOUTH;
      }
   }

   private EnumFacing rotateZ() {
      switch(EnumFacing$EnumFacing$1.field_179513_b[this.ordinal()]) {
      case 2:
         return DOWN;
      case 3:
      default:
         throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
      case 4:
         return UP;
      case 5:
         return EAST;
      case 6:
         return WEST;
      }
   }

   public EnumFacing rotateYCCW() {
      switch(EnumFacing$EnumFacing$1.field_179513_b[this.ordinal()]) {
      case 1:
         return WEST;
      case 2:
         return NORTH;
      case 3:
         return EAST;
      case 4:
         return SOUTH;
      default:
         throw new IllegalStateException("Unable to get CCW facing of " + this);
      }
   }

   public int getFrontOffsetX() {
      return this.axis == EnumFacing$Axis.X?this.axisDirection.getOffset():0;
   }

   public int getFrontOffsetY() {
      return this.axis == EnumFacing$Axis.Y?this.axisDirection.getOffset():0;
   }

   public int getFrontOffsetZ() {
      return this.axis == EnumFacing$Axis.Z?this.axisDirection.getOffset():0;
   }

   public String getName2() {
      return this.name;
   }

   public EnumFacing$Axis getAxis() {
      return this.axis;
   }

   public static EnumFacing a(String var0) {
      return null;
   }

   public static EnumFacing getFront(int var0) {
      return VALUES[MathHelper.abs_int(var0 % VALUES.length)];
   }

   public static EnumFacing getHorizontal(int var0) {
      return HORIZONTALS[MathHelper.abs_int(var0 % HORIZONTALS.length)];
   }

   public static EnumFacing fromAngle(double var0) {
      return getHorizontal(MathHelper.floor_double(var0 / 90.0D + 0.5D) & 3);
   }

   public static EnumFacing random(Random var0) {
      return values()[var0.nextInt(values().length)];
   }

   public static EnumFacing getFacingFromVector(float var0, float var1, float var2) {
      EnumFacing var3 = NORTH;
      float var4 = Float.MIN_VALUE;

      for(EnumFacing var8 : values()) {
         float var9 = var0 * (float)var8.directionVec.getX() + var1 * (float)var8.directionVec.getY() + var2 * (float)var8.directionVec.getZ();
         if(var9 > var4) {
            var4 = var9;
            var3 = var8;
         }
      }

      return var3;
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }

   public static EnumFacing func_181076_a(EnumFacing$AxisDirection var0, EnumFacing$Axis var1) {
      for(EnumFacing var5 : values()) {
         if(var5.getAxisDirection() == var0 && var5.getAxis() == var1) {
            return var5;
         }
      }

      throw new IllegalArgumentException("No such direction: " + var0 + " " + var1);
   }

   public Vec3i getDirectionVec() {
      return this.directionVec;
   }

   static {
      for(EnumFacing var10 : values()) {
         VALUES[var10.index] = var10;
         if(var10.getAxis().isHorizontal()) {
            HORIZONTALS[var10.horizontalIndex] = var10;
         }

         NAME_LOOKUP.put(var10.getName2().toLowerCase(), var10);
      }

   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}
