package net.minecraft.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public enum EnumFacing implements IStringSerializable {
   DOWN("DOWN", 0, 0, 1, -1, "down", EnumFacing.AxisDirection.NEGATIVE, EnumFacing.Axis.Y, new Vec3i(0, -1, 0)),
   UP("UP", 1, 1, 0, -1, "up", EnumFacing.AxisDirection.POSITIVE, EnumFacing.Axis.Y, new Vec3i(0, 1, 0)),
   NORTH("NORTH", 2, 2, 3, 2, "north", EnumFacing.AxisDirection.NEGATIVE, EnumFacing.Axis.Z, new Vec3i(0, 0, -1)),
   SOUTH("SOUTH", 3, 3, 2, 0, "south", EnumFacing.AxisDirection.POSITIVE, EnumFacing.Axis.Z, new Vec3i(0, 0, 1)),
   WEST("WEST", 4, 4, 5, 1, "west", EnumFacing.AxisDirection.NEGATIVE, EnumFacing.Axis.X, new Vec3i(-1, 0, 0)),
   EAST("EAST", 5, 5, 4, 3, "east", EnumFacing.AxisDirection.POSITIVE, EnumFacing.Axis.X, new Vec3i(1, 0, 0));

   private final int index;
   private final int opposite;
   private final int horizontalIndex;
   private final String name;
   private final EnumFacing.Axis axis;
   private final EnumFacing.AxisDirection axisDirection;
   private final Vec3i directionVec;
   public static final EnumFacing[] VALUES = new EnumFacing[6];
   private static final EnumFacing[] HORIZONTALS = new EnumFacing[4];
   private static final Map NAME_LOOKUP = Maps.newHashMap();
   private static final EnumFacing[] $VALUES = new EnumFacing[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};

   private EnumFacing(String p_i46016_1_, int p_i46016_2_, int indexIn, int oppositeIn, int horizontalIndexIn, String nameIn, EnumFacing.AxisDirection axisDirectionIn, EnumFacing.Axis axisIn, Vec3i directionVecIn) {
      this.index = indexIn;
      this.horizontalIndex = horizontalIndexIn;
      this.opposite = oppositeIn;
      this.name = nameIn;
      this.axis = axisIn;
      this.axisDirection = axisDirectionIn;
      this.directionVec = directionVecIn;
   }

   public int getIndex() {
      return this.index;
   }

   public int getHorizontalIndex() {
      return this.horizontalIndex;
   }

   public EnumFacing.AxisDirection getAxisDirection() {
      return this.axisDirection;
   }

   public EnumFacing getOpposite() {
      return getFront(this.opposite);
   }

   public EnumFacing rotateAround(EnumFacing.Axis axis) {
      switch(EnumFacing.SwitchPlane.AXIS_LOOKUP[axis.ordinal()]) {
      case 1:
         if (this != WEST && this != EAST) {
            return this.rotateX();
         }

         return this;
      case 2:
         if (this != UP && this != DOWN) {
            return this.rotateY();
         }

         return this;
      case 3:
         if (this != NORTH && this != SOUTH) {
            return this.rotateZ();
         }

         return this;
      default:
         throw new IllegalStateException("Unable to get CW facing for axis " + axis);
      }
   }

   public EnumFacing rotateY() {
      switch(EnumFacing.SwitchPlane.FACING_LOOKUP[this.ordinal()]) {
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
      switch(EnumFacing.SwitchPlane.FACING_LOOKUP[this.ordinal()]) {
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
      switch(EnumFacing.SwitchPlane.FACING_LOOKUP[this.ordinal()]) {
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
      switch(EnumFacing.SwitchPlane.FACING_LOOKUP[this.ordinal()]) {
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
      return this.axis == EnumFacing.Axis.X ? this.axisDirection.getOffset() : 0;
   }

   public int getFrontOffsetY() {
      return this.axis == EnumFacing.Axis.Y ? this.axisDirection.getOffset() : 0;
   }

   public int getFrontOffsetZ() {
      return this.axis == EnumFacing.Axis.Z ? this.axisDirection.getOffset() : 0;
   }

   public String getName2() {
      return this.name;
   }

   public EnumFacing.Axis getAxis() {
      return this.axis;
   }

   public static EnumFacing byName(String name) {
      return name == null ? null : (EnumFacing)NAME_LOOKUP.get(name.toLowerCase());
   }

   public static EnumFacing getFront(int index) {
      return VALUES[MathHelper.abs_int(index % VALUES.length)];
   }

   public static EnumFacing getHorizontal(int p_176731_0_) {
      return HORIZONTALS[MathHelper.abs_int(p_176731_0_ % HORIZONTALS.length)];
   }

   public static EnumFacing fromAngle(double angle) {
      return getHorizontal(MathHelper.floor_double(angle / 90.0D + 0.5D) & 3);
   }

   public static EnumFacing random(Random rand) {
      return values()[rand.nextInt(values().length)];
   }

   public static EnumFacing func_176737_a(float x, float y, float z) {
      EnumFacing face = NORTH;
      float min = Float.MIN_VALUE;
      EnumFacing[] faces = values();
      int arrayLength = faces.length;

      for(int index = 0; index < arrayLength; ++index) {
         EnumFacing getFace = faces[index];
         float dist = x * (float)getFace.directionVec.getX() + y * (float)getFace.directionVec.getY() + z * (float)getFace.directionVec.getZ();
         if (dist > min) {
            min = dist;
            face = getFace;
         }
      }

      return face;
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }

   public Vec3i getDirectionVec() {
      return this.directionVec;
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

   static final class SwitchPlane {
      static final int[] AXIS_LOOKUP;
      static final int[] FACING_LOOKUP;
      static final int[] PLANE_LOOKUP = new int[EnumFacing.Plane.values().length];

      static {
         try {
            PLANE_LOOKUP[EnumFacing.Plane.HORIZONTAL.ordinal()] = 1;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            PLANE_LOOKUP[EnumFacing.Plane.VERTICAL.ordinal()] = 2;
         } catch (NoSuchFieldError var10) {
            ;
         }

         FACING_LOOKUP = new int[EnumFacing.values().length];

         try {
            FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 2;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 3;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 4;
         } catch (NoSuchFieldError var6) {
            ;
         }

         try {
            FACING_LOOKUP[EnumFacing.UP.ordinal()] = 5;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 6;
         } catch (NoSuchFieldError var4) {
            ;
         }

         AXIS_LOOKUP = new int[EnumFacing.Axis.values().length];

         try {
            AXIS_LOOKUP[EnumFacing.Axis.X.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            AXIS_LOOKUP[EnumFacing.Axis.Y.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            AXIS_LOOKUP[EnumFacing.Axis.Z.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
            ;
         }

      }
   }

   public static enum Plane implements Predicate, Iterable {
      HORIZONTAL("HORIZONTAL", 0, "HORIZONTAL", 0),
      VERTICAL("VERTICAL", 1, "VERTICAL", 1);

      private static final EnumFacing.Plane[] $VALUES = new EnumFacing.Plane[]{HORIZONTAL, VERTICAL};
      private static final EnumFacing.Plane[] $VALUES$ = new EnumFacing.Plane[]{HORIZONTAL, VERTICAL};

      private Plane(String p_i46400_1_, int p_i46400_2_, String p_i46013_1_, int p_i46013_2_) {
      }

      public EnumFacing[] facings() {
         switch(EnumFacing.SwitchPlane.PLANE_LOOKUP[this.ordinal()]) {
         case 1:
            return new EnumFacing[]{EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST};
         case 2:
            return new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN};
         default:
            throw new Error("Someone's been tampering with the universe!");
         }
      }

      public EnumFacing random(Random rand) {
         EnumFacing[] var2 = this.facings();
         return var2[rand.nextInt(var2.length)];
      }

      public boolean apply(EnumFacing facing) {
         return facing != null && facing.getAxis().getPlane() == this;
      }

      public Iterator iterator() {
         return Iterators.forArray(this.facings());
      }

      public boolean apply(Object p_apply_1_) {
         return this.apply((EnumFacing)p_apply_1_);
      }
   }

   public static enum AxisDirection {
      POSITIVE("POSITIVE", 0, "POSITIVE", 0, 1, "Towards positive"),
      NEGATIVE("NEGATIVE", 1, "NEGATIVE", 1, -1, "Towards negative");

      private final int offset;
      private final String description;
      private static final EnumFacing.AxisDirection[] $VALUES = new EnumFacing.AxisDirection[]{POSITIVE, NEGATIVE};
      private static final EnumFacing.AxisDirection[] $VALUES$ = new EnumFacing.AxisDirection[]{POSITIVE, NEGATIVE};

      private AxisDirection(String p_i46399_1_, int p_i46399_2_, String p_i46014_1_, int p_i46014_2_, int offset, String description) {
         this.offset = offset;
         this.description = description;
      }

      public int getOffset() {
         return this.offset;
      }

      public String toString() {
         return this.description;
      }
   }

   public static enum Axis implements Predicate, IStringSerializable {
      X("X", 0, "X", 0, "x", EnumFacing.Plane.HORIZONTAL),
      Y("Y", 1, "Y", 1, "y", EnumFacing.Plane.VERTICAL),
      Z("Z", 2, "Z", 2, "z", EnumFacing.Plane.HORIZONTAL);

      private static final Map NAME_LOOKUP = Maps.newHashMap();
      private final String name;
      private final EnumFacing.Plane plane;
      private static final EnumFacing.Axis[] $VALUES = new EnumFacing.Axis[]{X, Y, Z};
      private static final EnumFacing.Axis[] $VALUES$ = new EnumFacing.Axis[]{X, Y, Z};

      private Axis(String p_i46398_1_, int p_i46398_2_, String p_i46015_1_, int p_i46015_2_, String name, EnumFacing.Plane plane) {
         this.name = name;
         this.plane = plane;
      }

      public static EnumFacing.Axis byName(String name) {
         return name == null ? null : (EnumFacing.Axis)NAME_LOOKUP.get(name.toLowerCase());
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

      public String toString() {
         return this.name;
      }

      public boolean apply(EnumFacing facing) {
         return facing != null && facing.getAxis() == this;
      }

      public EnumFacing.Plane getPlane() {
         return this.plane;
      }

      public String getName() {
         return this.name;
      }

      public boolean apply(Object p_apply_1_) {
         return this.apply((EnumFacing)p_apply_1_);
      }

      static {
         EnumFacing.Axis[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            EnumFacing.Axis var3 = var0[var2];
            NAME_LOOKUP.put(var3.getName2().toLowerCase(), var3);
         }

      }
   }
}
