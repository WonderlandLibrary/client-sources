package net.minecraft.util;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.entity.Entity;

public class BlockPos extends Vec3i {
   private static final long field_177993_j;
   private static final int field_177989_d;
   public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
   private static final String __OBFID = "CL_00002334";
   private static final int field_177988_g;
   private static final int field_177987_f;
   private static final int field_177991_c;
   private static final long field_177995_i;
   private static final long field_177994_h;
   private static final int field_177990_b = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));

   public long toLong() {
      return ((long)this.getX() & field_177994_h) << field_177988_g | ((long)this.getY() & field_177995_i) << field_177987_f | ((long)this.getZ() & field_177993_j) << 0;
   }

   public BlockPos offsetSouth() {
      return this.offsetSouth(1);
   }

   public BlockPos add(double var1, double var3, double var5) {
      return new BlockPos((double)this.getX() + var1, (double)this.getY() + var3, (double)this.getZ() + var5);
   }

   public BlockPos add(int var1, int var2, int var3) {
      return new BlockPos(this.getX() + var1, this.getY() + var2, this.getZ() + var3);
   }

   public BlockPos offsetNorth(int var1) {
      return this.offset(EnumFacing.NORTH, var1);
   }

   public BlockPos offsetNorth() {
      return this.offsetNorth(1);
   }

   public BlockPos(Vec3i var1) {
      this(var1.getX(), var1.getY(), var1.getZ());
   }

   public static Iterable getAllInBox(BlockPos var0, BlockPos var1) {
      BlockPos var2 = new BlockPos(Math.min(var0.getX(), var1.getX()), Math.min(var0.getY(), var1.getY()), Math.min(var0.getZ(), var1.getZ()));
      BlockPos var3 = new BlockPos(Math.max(var0.getX(), var1.getX()), Math.max(var0.getY(), var1.getY()), Math.max(var0.getZ(), var1.getZ()));
      return new Iterable(var2, var3) {
         private final BlockPos val$var2;
         private final BlockPos val$var3;
         private static final String __OBFID = "CL_00002333";

         public Iterator iterator() {
            return new AbstractIterator(this, this.val$var2, this.val$var3) {
               final <undefinedtype> this$1;
               private final BlockPos val$var3;
               private static final String __OBFID = "CL_00002332";
               private final BlockPos val$var2;
               private BlockPos lastReturned;

               {
                  this.this$1 = var1;
                  this.val$var2 = var2;
                  this.val$var3 = var3;
                  this.lastReturned = null;
               }

               protected Object computeNext() {
                  return this.computeNext0();
               }

               protected BlockPos computeNext0() {
                  if (this.lastReturned == null) {
                     this.lastReturned = this.val$var2;
                     return this.lastReturned;
                  } else if (this.lastReturned.equals(this.val$var3)) {
                     return (BlockPos)this.endOfData();
                  } else {
                     int var1 = this.lastReturned.getX();
                     int var2 = this.lastReturned.getY();
                     int var3 = this.lastReturned.getZ();
                     if (var1 < this.val$var3.getX()) {
                        ++var1;
                     } else if (var2 < this.val$var3.getY()) {
                        var1 = this.val$var2.getX();
                        ++var2;
                     } else if (var3 < this.val$var3.getZ()) {
                        var1 = this.val$var2.getX();
                        var2 = this.val$var2.getY();
                        ++var3;
                     }

                     this.lastReturned = new BlockPos(var1, var2, var3);
                     return this.lastReturned;
                  }
               }
            };
         }

         {
            this.val$var2 = var1;
            this.val$var3 = var2;
         }
      };
   }

   public BlockPos(Entity var1) {
      this(var1.posX, var1.posY, var1.posZ);
   }

   public BlockPos(double var1, double var3, double var5) {
      super(var1, var3, var5);
   }

   public BlockPos crossProductBP(Vec3i var1) {
      return new BlockPos(this.getY() * var1.getZ() - this.getZ() * var1.getY(), this.getZ() * var1.getX() - this.getX() * var1.getZ(), this.getX() * var1.getY() - this.getY() * var1.getX());
   }

   public BlockPos offsetDown() {
      return this.offsetDown(1);
   }

   public BlockPos offset(EnumFacing var1) {
      return this.offset(var1, 1);
   }

   public BlockPos offsetWest(int var1) {
      return this.offset(EnumFacing.WEST, var1);
   }

   public BlockPos multiply(int var1) {
      return new BlockPos(this.getX() * var1, this.getY() * var1, this.getZ() * var1);
   }

   public BlockPos offsetDown(int var1) {
      return this.offset(EnumFacing.DOWN, var1);
   }

   public static BlockPos fromLong(long var0) {
      int var2 = (int)(var0 << 64 - field_177988_g - field_177990_b >> 64 - field_177990_b);
      int var3 = (int)(var0 << 64 - field_177987_f - field_177989_d >> 64 - field_177989_d);
      int var4 = (int)(var0 << 64 - field_177991_c >> 64 - field_177991_c);
      return new BlockPos(var2, var3, var4);
   }

   static {
      field_177991_c = field_177990_b;
      field_177989_d = 64 - field_177990_b - field_177991_c;
      field_177987_f = 0 + field_177991_c;
      field_177988_g = field_177987_f + field_177989_d;
      field_177994_h = (1L << field_177990_b) - 1L;
      field_177995_i = (1L << field_177989_d) - 1L;
      field_177993_j = (1L << field_177991_c) - 1L;
   }

   public BlockPos(int var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   public BlockPos offset(EnumFacing var1, int var2) {
      return new BlockPos(this.getX() + var1.getFrontOffsetX() * var2, this.getY() + var1.getFrontOffsetY() * var2, this.getZ() + var1.getFrontOffsetZ() * var2);
   }

   public Vec3i crossProduct(Vec3i var1) {
      return this.crossProductBP(var1);
   }

   public BlockPos offsetUp() {
      return this.offsetUp(1);
   }

   public BlockPos offsetSouth(int var1) {
      return this.offset(EnumFacing.SOUTH, var1);
   }

   public BlockPos add(Vec3i var1) {
      return new BlockPos(this.getX() + var1.getX(), this.getY() + var1.getY(), this.getZ() + var1.getZ());
   }

   public BlockPos offsetEast() {
      return this.offsetEast(1);
   }

   public BlockPos offsetWest() {
      return this.offsetWest(1);
   }

   public BlockPos offsetUp(int var1) {
      return this.offset(EnumFacing.UP, var1);
   }

   public static Iterable getAllInBoxMutable(BlockPos var0, BlockPos var1) {
      BlockPos var2 = new BlockPos(Math.min(var0.getX(), var1.getX()), Math.min(var0.getY(), var1.getY()), Math.min(var0.getZ(), var1.getZ()));
      BlockPos var3 = new BlockPos(Math.max(var0.getX(), var1.getX()), Math.max(var0.getY(), var1.getY()), Math.max(var0.getZ(), var1.getZ()));
      return new Iterable(var2, var3) {
         private final BlockPos val$var2;
         private final BlockPos val$var3;
         private static final String __OBFID = "CL_00002331";

         public Iterator iterator() {
            return new AbstractIterator(this, this.val$var2, this.val$var3) {
               private final BlockPos val$var2;
               final <undefinedtype> this$1;
               private BlockPos.MutableBlockPos theBlockPos;
               private final BlockPos val$var3;
               private static final String __OBFID = "CL_00002330";

               protected Object computeNext() {
                  return this.computeNext0();
               }

               {
                  this.this$1 = var1;
                  this.val$var2 = var2;
                  this.val$var3 = var3;
                  this.theBlockPos = null;
               }

               protected BlockPos.MutableBlockPos computeNext0() {
                  if (this.theBlockPos == null) {
                     this.theBlockPos = new BlockPos.MutableBlockPos(this.val$var2.getX(), this.val$var2.getY(), this.val$var2.getZ(), (Object)null);
                     return this.theBlockPos;
                  } else if (this.theBlockPos.equals(this.val$var3)) {
                     return (BlockPos.MutableBlockPos)this.endOfData();
                  } else {
                     int var1 = this.theBlockPos.getX();
                     int var2 = this.theBlockPos.getY();
                     int var3 = this.theBlockPos.getZ();
                     if (var1 < this.val$var3.getX()) {
                        ++var1;
                     } else if (var2 < this.val$var3.getY()) {
                        var1 = this.val$var2.getX();
                        ++var2;
                     } else if (var3 < this.val$var3.getZ()) {
                        var1 = this.val$var2.getX();
                        var2 = this.val$var2.getY();
                        ++var3;
                     }

                     this.theBlockPos.x = var1;
                     this.theBlockPos.y = var2;
                     this.theBlockPos.z = var3;
                     return this.theBlockPos;
                  }
               }
            };
         }

         {
            this.val$var2 = var1;
            this.val$var3 = var2;
         }
      };
   }

   public BlockPos(Vec3 var1) {
      this(var1.xCoord, var1.yCoord, var1.zCoord);
   }

   public BlockPos offsetEast(int var1) {
      return this.offset(EnumFacing.EAST, var1);
   }

   public BlockPos subtract(Vec3i var1) {
      return new BlockPos(this.getX() - var1.getX(), this.getY() - var1.getY(), this.getZ() - var1.getZ());
   }

   public static final class MutableBlockPos extends BlockPos {
      public int z;
      private static final String __OBFID = "CL_00002329";
      public int y;
      public int x;

      public int getX() {
         return this.x;
      }

      private MutableBlockPos(int var1, int var2, int var3) {
         super(0, 0, 0);
         this.x = var1;
         this.y = var2;
         this.z = var3;
      }

      public int getZ() {
         return this.z;
      }

      public int getY() {
         return this.y;
      }

      MutableBlockPos(int var1, int var2, int var3, Object var4) {
         this(var1, var2, var3);
      }

      public Vec3i crossProduct(Vec3i var1) {
         return super.crossProductBP(var1);
      }
   }
}
