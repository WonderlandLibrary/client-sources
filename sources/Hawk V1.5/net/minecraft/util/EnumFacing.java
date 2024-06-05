package net.minecraft.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public enum EnumFacing implements IStringSerializable {
   SOUTH("SOUTH", 3, 3, 2, 0, "south", EnumFacing.AxisDirection.POSITIVE, EnumFacing.Axis.Z, new Vec3i(0, 0, 1));

   private static final EnumFacing[] HORIZONTALS = new EnumFacing[4];
   private static final EnumFacing[] ENUM$VALUES = new EnumFacing[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
   UP("UP", 1, 1, 0, -1, "up", EnumFacing.AxisDirection.POSITIVE, EnumFacing.Axis.Y, new Vec3i(0, 1, 0));

   private final int horizontalIndex;
   NORTH("NORTH", 2, 2, 3, 2, "north", EnumFacing.AxisDirection.NEGATIVE, EnumFacing.Axis.Z, new Vec3i(0, 0, -1));

   private static final Map NAME_LOOKUP = Maps.newHashMap();
   private final EnumFacing.Axis axis;
   WEST("WEST", 4, 4, 5, 1, "west", EnumFacing.AxisDirection.NEGATIVE, EnumFacing.Axis.X, new Vec3i(-1, 0, 0));

   private static final String __OBFID = "CL_00001201";
   private final EnumFacing.AxisDirection axisDirection;
   private final String name;
   EAST("EAST", 5, 5, 4, 3, "east", EnumFacing.AxisDirection.POSITIVE, EnumFacing.Axis.X, new Vec3i(1, 0, 0)),
   DOWN("DOWN", 0, 0, 1, -1, "down", EnumFacing.AxisDirection.NEGATIVE, EnumFacing.Axis.Y, new Vec3i(0, -1, 0));

   private final Vec3i directionVec;
   private static final EnumFacing[] $VALUES = new EnumFacing[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
   private final int index;
   public static final EnumFacing[] VALUES = new EnumFacing[6];
   private final int opposite;

   public Vec3i getDirectionVec() {
      return this.directionVec;
   }

   public int getFrontOffsetX() {
      return this.axis == EnumFacing.Axis.X ? this.axisDirection.getOffset() : 0;
   }

   public int getFrontOffsetY() {
      return this.axis == EnumFacing.Axis.Y ? this.axisDirection.getOffset() : 0;
   }

   public static EnumFacing getHorizontal(int var0) {
      return HORIZONTALS[MathHelper.abs_int(var0 % HORIZONTALS.length)];
   }

   public EnumFacing.Axis getAxis() {
      return this.axis;
   }

   public EnumFacing.AxisDirection getAxisDirection() {
      return this.axisDirection;
   }

   public int getHorizontalIndex() {
      return this.horizontalIndex;
   }

   private EnumFacing(String var3, int var4, int var5, int var6, int var7, String var8, EnumFacing.AxisDirection var9, EnumFacing.Axis var10, Vec3i var11) {
      this.index = var5;
      this.horizontalIndex = var7;
      this.opposite = var6;
      this.name = var8;
      this.axis = var10;
      this.axisDirection = var9;
      this.directionVec = var11;
   }

   public int getFrontOffsetZ() {
      return this.axis == EnumFacing.Axis.Z ? this.axisDirection.getOffset() : 0;
   }

   private EnumFacing rotateX() {
      switch(this) {
      case NORTH:
         return DOWN;
      case EAST:
      case WEST:
      default:
         throw new IllegalStateException(String.valueOf((new StringBuilder("Unable to get X-rotated facing of ")).append(this)));
      case SOUTH:
         return UP;
      case UP:
         return NORTH;
      case DOWN:
         return SOUTH;
      }
   }

   public String getName2() {
      return this.name;
   }

   public static EnumFacing byName(String var0) {
      return var0 == null ? null : (EnumFacing)NAME_LOOKUP.get(var0.toLowerCase());
   }

   public int getIndex() {
      return this.index;
   }

   static {
      EnumFacing[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         EnumFacing var3 = var0[var2];
         VALUES[var3.index] = var3;
         if (var3.getAxis().isHorizontal()) {
            HORIZONTALS[var3.horizontalIndex] = var3;
         }

         NAME_LOOKUP.put(var3.getName2().toLowerCase(), var3);
      }

   }

   public EnumFacing rotateY() {
      switch(this) {
      case NORTH:
         return EAST;
      case EAST:
         return SOUTH;
      case SOUTH:
         return WEST;
      case WEST:
         return NORTH;
      default:
         throw new IllegalStateException(String.valueOf((new StringBuilder("Unable to get Y-rotated facing of ")).append(this)));
      }
   }

   public static EnumFacing fromAngle(double var0) {
      return getHorizontal(MathHelper.floor_double(var0 / 90.0D + 0.5D) & 3);
   }

   public String getName() {
      return this.name;
   }

   public EnumFacing rotateAround(EnumFacing.Axis var1) {
      switch(var1) {
      case X:
         if (this != WEST && this != EAST) {
            return this.rotateX();
         }

         return this;
      case Y:
         if (this != UP && this != DOWN) {
            return this.rotateY();
         }

         return this;
      case Z:
         if (this != NORTH && this != SOUTH) {
            return this.rotateZ();
         }

         return this;
      default:
         throw new IllegalStateException(String.valueOf((new StringBuilder("Unable to get CW facing for axis ")).append(var1)));
      }
   }

   public String toString() {
      return this.name;
   }

   private EnumFacing rotateZ() {
      switch(this) {
      case EAST:
         return DOWN;
      case SOUTH:
      default:
         throw new IllegalStateException(String.valueOf((new StringBuilder("Unable to get Z-rotated facing of ")).append(this)));
      case WEST:
         return UP;
      case UP:
         return EAST;
      case DOWN:
         return WEST;
      }
   }

   public static EnumFacing getFront(int var0) {
      return VALUES[MathHelper.abs_int(var0 % VALUES.length)];
   }

   public static EnumFacing func_176737_a(float var0, float var1, float var2) {
      EnumFacing var3 = NORTH;
      float var4 = Float.MIN_VALUE;
      EnumFacing[] var5 = values();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         EnumFacing var8 = var5[var7];
         float var9 = var0 * (float)var8.directionVec.getX() + var1 * (float)var8.directionVec.getY() + var2 * (float)var8.directionVec.getZ();
         if (var9 > var4) {
            var4 = var9;
            var3 = var8;
         }
      }

      return var3;
   }

   public EnumFacing getOpposite() {
      return VALUES[this.opposite];
   }

   public static EnumFacing random(Random var0) {
      return values()[var0.nextInt(values().length)];
   }

   public EnumFacing rotateYCCW() {
      switch(this) {
      case NORTH:
         return WEST;
      case EAST:
         return NORTH;
      case SOUTH:
         return EAST;
      case WEST:
         return SOUTH;
      default:
         throw new IllegalStateException(String.valueOf((new StringBuilder("Unable to get CCW facing of ")).append(this)));
      }
   }

   public static enum Plane implements Predicate, Iterable {
      HORIZONTAL("HORIZONTAL", 0, "HORIZONTAL", 0);

      private static final EnumFacing.Plane[] ENUM$VALUES = new EnumFacing.Plane[]{HORIZONTAL, VERTICAL};
      VERTICAL("VERTICAL", 1, "VERTICAL", 1);

      private static final EnumFacing.Plane[] $VALUES = new EnumFacing.Plane[]{HORIZONTAL, VERTICAL};
      private static final String __OBFID = "CL_00002319";

      public boolean apply(EnumFacing var1) {
         return var1 != null && var1.getAxis().getPlane() == this;
      }

      public EnumFacing[] facings() {
         switch(this) {
         case HORIZONTAL:
            return new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST};
         case VERTICAL:
            return new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN};
         default:
            throw new Error("Someone's been tampering with the universe!");
         }
      }

      public boolean apply(Object var1) {
         return this.apply((EnumFacing)var1);
      }

      public EnumFacing random(Random var1) {
         EnumFacing[] var2 = this.facings();
         return var2[var1.nextInt(var2.length)];
      }

      public Iterator iterator() {
         return Iterators.forArray(this.facings());
      }

      private Plane(String var3, int var4, String var5, int var6) {
      }
   }

   static final class SwitchPlane {
      private static final String __OBFID = "CL_00002322";
      static final int[] AXIS_LOOKUP;
      static final int[] PLANE_LOOKUP = new int[EnumFacing.Plane.values().length];
      static final int[] FACING_LOOKUP;

      static {
         try {
            PLANE_LOOKUP[EnumFacing.Plane.HORIZONTAL.ordinal()] = 1;
         } catch (NoSuchFieldError var11) {
         }

         try {
            PLANE_LOOKUP[EnumFacing.Plane.VERTICAL.ordinal()] = 2;
         } catch (NoSuchFieldError var10) {
         }

         FACING_LOOKUP = new int[EnumFacing.values().length];

         try {
            FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var9) {
         }

         try {
            FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 2;
         } catch (NoSuchFieldError var8) {
         }

         try {
            FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 3;
         } catch (NoSuchFieldError var7) {
         }

         try {
            FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 4;
         } catch (NoSuchFieldError var6) {
         }

         try {
            FACING_LOOKUP[EnumFacing.UP.ordinal()] = 5;
         } catch (NoSuchFieldError var5) {
         }

         try {
            FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 6;
         } catch (NoSuchFieldError var4) {
         }

         AXIS_LOOKUP = new int[EnumFacing.Axis.values().length];

         try {
            AXIS_LOOKUP[EnumFacing.Axis.X.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            AXIS_LOOKUP[EnumFacing.Axis.Y.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            AXIS_LOOKUP[EnumFacing.Axis.Z.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   public static enum Axis implements Predicate, IStringSerializable {
      private static final EnumFacing.Axis[] ENUM$VALUES = new EnumFacing.Axis[]{X, Y, Z};
      private static final Map NAME_LOOKUP = Maps.newHashMap();
      private final String name;
      private static final String __OBFID = "CL_00002321";
      private static final EnumFacing.Axis[] $VALUES = new EnumFacing.Axis[]{X, Y, Z};
      private static final EnumFacing.Axis[] $VALUES$ = new EnumFacing.Axis[]{X, Y, Z};
      private final EnumFacing.Plane plane;
      X("X", 0, "X", 0, "x", EnumFacing.Plane.HORIZONTAL),
      Y("Y", 1, "Y", 1, "y", EnumFacing.Plane.VERTICAL),
      Z("Z", 2, "Z", 2, "z", EnumFacing.Plane.HORIZONTAL);

      public static EnumFacing.Axis byName(String var0) {
         return var0 == null ? null : (EnumFacing.Axis)NAME_LOOKUP.get(var0.toLowerCase());
      }

      private Axis(String var3, int var4, String var5, int var6, String var7, EnumFacing.Plane var8) {
         this.name = var7;
         this.plane = var8;
      }

      public String toString() {
         return this.name;
      }

      public String getName2() {
         return this.name;
      }

      public boolean isVertical() {
         return this.plane == EnumFacing.Plane.VERTICAL;
      }

      public boolean isHorizontal() {
         return this.plane == EnumFacing.Plane.HORIZONTAL;
      }

      public boolean apply(Object var1) {
         return this.apply((EnumFacing)var1);
      }

      public String getName() {
         return this.name;
      }

      static {
         EnumFacing.Axis[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            EnumFacing.Axis var3 = var0[var2];
            NAME_LOOKUP.put(var3.getName2().toLowerCase(), var3);
         }

      }

      public EnumFacing.Plane getPlane() {
         return this.plane;
      }

      public boolean apply(EnumFacing var1) {
         return var1 != null && var1.getAxis() == this;
      }
   }

   public static enum AxisDirection {
      private static final String __OBFID = "CL_00002320";
      NEGATIVE("NEGATIVE", 1, "NEGATIVE", 1, -1, "Towards negative"),
      POSITIVE("POSITIVE", 0, "POSITIVE", 0, 1, "Towards positive");

      private static final EnumFacing.AxisDirection[] $VALUES = new EnumFacing.AxisDirection[]{POSITIVE, NEGATIVE};
      private final int offset;
      private final String description;
      private static final EnumFacing.AxisDirection[] ENUM$VALUES = new EnumFacing.AxisDirection[]{POSITIVE, NEGATIVE};

      public String toString() {
         return this.description;
      }

      private AxisDirection(String var3, int var4, String var5, int var6, int var7, String var8) {
         this.offset = var7;
         this.description = var8;
      }

      public int getOffset() {
         return this.offset;
      }
   }
}
