package net.minecraft.util;

import net.aYA;
import net.abd;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class BlockPos extends Vec3i {
   public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
   private static final int NUM_X_BITS = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
   private static final int NUM_Z_BITS = NUM_X_BITS;
   private static final int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
   private static final int Y_SHIFT = NUM_Z_BITS;
   private static final int X_SHIFT = Y_SHIFT + NUM_Y_BITS;
   private static final long X_MASK = (1L << NUM_X_BITS) - 1L;
   private static final long Y_MASK = (1L << NUM_Y_BITS) - 1L;
   private static final long Z_MASK = (1L << NUM_Z_BITS) - 1L;

   public BlockPos(int var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   public BlockPos(double var1, double var3, double var5) {
      super(var1, var3, var5);
   }

   public BlockPos(Entity var1) {
      this(var1.posX, var1.posY, var1.posZ);
   }

   public BlockPos(Vec3 var1) {
      this(var1.xCoord, var1.yCoord, var1.zCoord);
   }

   public BlockPos(Vec3i var1) {
      this(var1.getX(), var1.getY(), var1.getZ());
   }

   public BlockPos add(double var1, double var3, double var5) {
      return var1 == 0.0D && var3 == 0.0D && var5 == 0.0D?this:new BlockPos((double)this.getX() + var1, (double)this.getY() + var3, (double)this.getZ() + var5);
   }

   public BlockPos a(int var1, int var2, int var3) {
      return this;
   }

   public BlockPos add(Vec3i var1) {
      return var1.getX() == 0 && var1.getY() == 0 && var1.getZ() == 0?this:new BlockPos(this.getX() + var1.getX(), this.getY() + var1.getY(), this.getZ() + var1.getZ());
   }

   public BlockPos subtract(Vec3i var1) {
      return var1.getX() == 0 && var1.getY() == 0 && var1.getZ() == 0?this:new BlockPos(this.getX() - var1.getX(), this.getY() - var1.getY(), this.getZ() - var1.getZ());
   }

   public BlockPos up() {
      return this.up(1);
   }

   public BlockPos up(int var1) {
      return this.offset(EnumFacing.UP, var1);
   }

   public BlockPos down() {
      return this.down(1);
   }

   public BlockPos down(int var1) {
      return this.offset(EnumFacing.DOWN, var1);
   }

   public BlockPos north() {
      return this.north(1);
   }

   public BlockPos north(int var1) {
      return this.offset(EnumFacing.NORTH, var1);
   }

   public BlockPos south() {
      return this.south(1);
   }

   public BlockPos south(int var1) {
      return this.offset(EnumFacing.SOUTH, var1);
   }

   public BlockPos west() {
      return this.west(1);
   }

   public BlockPos west(int var1) {
      return this.offset(EnumFacing.WEST, var1);
   }

   public BlockPos east() {
      return this.east(1);
   }

   public BlockPos east(int var1) {
      return this.offset(EnumFacing.EAST, var1);
   }

   public BlockPos offset(EnumFacing var1) {
      return this.offset(var1, 1);
   }

   public BlockPos offset(EnumFacing var1, int var2) {
      return this;
   }

   public BlockPos crossProduct(Vec3i var1) {
      return new BlockPos(this.getY() * var1.getZ() - this.getZ() * var1.getY(), this.getZ() * var1.getX() - this.getX() * var1.getZ(), this.getX() * var1.getY() - this.getY() * var1.getX());
   }

   public long toLong() {
      return ((long)this.getX() & X_MASK) << X_SHIFT | ((long)this.getY() & Y_MASK) << Y_SHIFT | (long)this.getZ() & Z_MASK;
   }

   public static BlockPos fromLong(long var0) {
      int var2 = (int)(var0 << 64 - X_SHIFT - NUM_X_BITS >> 64 - NUM_X_BITS);
      int var3 = (int)(var0 << 64 - Y_SHIFT - NUM_Y_BITS >> 64 - NUM_Y_BITS);
      int var4 = (int)(var0 << 64 - NUM_Z_BITS >> 64 - NUM_Z_BITS);
      return new BlockPos(var2, var3, var4);
   }

   public static Iterable getAllInBox(BlockPos var0, BlockPos var1) {
      BlockPos var2 = new BlockPos(Math.min(var0.getX(), var1.getX()), Math.min(var0.getY(), var1.getY()), Math.min(var0.getZ(), var1.getZ()));
      BlockPos var3 = new BlockPos(Math.max(var0.getX(), var1.getX()), Math.max(var0.getY(), var1.getY()), Math.max(var0.getZ(), var1.getZ()));
      return new aYA(var2, var3);
   }

   public static Iterable getAllInBoxMutable(BlockPos var0, BlockPos var1) {
      BlockPos var2 = new BlockPos(Math.min(var0.getX(), var1.getX()), Math.min(var0.getY(), var1.getY()), Math.min(var0.getZ(), var1.getZ()));
      BlockPos var3 = new BlockPos(Math.max(var0.getX(), var1.getX()), Math.max(var0.getY(), var1.getY()), Math.max(var0.getZ(), var1.getZ()));
      return new abd(var2, var3);
   }
}
