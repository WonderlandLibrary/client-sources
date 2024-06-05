package net.minecraft.village;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class VillageDoorInfo {
   private final BlockPos field_179859_a;
   private boolean isDetachedFromVillageFlag;
   private int doorOpeningRestrictionCounter;
   private int lastActivityTimestamp;
   private final BlockPos field_179857_b;
   private static final String __OBFID = "CL_00001630";
   private final EnumFacing field_179858_c;

   public void incrementDoorOpeningRestrictionCounter() {
      ++this.doorOpeningRestrictionCounter;
   }

   public int getDoorOpeningRestrictionCounter() {
      return this.doorOpeningRestrictionCounter;
   }

   public boolean func_179851_i() {
      return this.isDetachedFromVillageFlag;
   }

   public int func_179846_b(BlockPos var1) {
      return (int)this.field_179857_b.distanceSq(var1);
   }

   public int func_179855_g() {
      return this.field_179858_c.getFrontOffsetZ() * 2;
   }

   private static EnumFacing func_179854_a(int var0, int var1) {
      return var0 < 0 ? EnumFacing.WEST : (var0 > 0 ? EnumFacing.EAST : (var1 < 0 ? EnumFacing.NORTH : EnumFacing.SOUTH));
   }

   public VillageDoorInfo(BlockPos var1, int var2, int var3, int var4) {
      this(var1, func_179854_a(var2, var3), var4);
   }

   public VillageDoorInfo(BlockPos var1, EnumFacing var2, int var3) {
      this.field_179859_a = var1;
      this.field_179858_c = var2;
      this.field_179857_b = var1.offset(var2, 2);
      this.lastActivityTimestamp = var3;
   }

   public BlockPos func_179856_e() {
      return this.field_179857_b;
   }

   public BlockPos func_179852_d() {
      return this.field_179859_a;
   }

   public int func_179847_f() {
      return this.field_179858_c.getFrontOffsetX() * 2;
   }

   public int getDistanceSquared(int var1, int var2, int var3) {
      return (int)this.field_179859_a.distanceSq((double)var1, (double)var2, (double)var3);
   }

   public void func_179853_a(boolean var1) {
      this.isDetachedFromVillageFlag = var1;
   }

   public void resetDoorOpeningRestrictionCounter() {
      this.doorOpeningRestrictionCounter = 0;
   }

   public int getInsidePosY() {
      return this.lastActivityTimestamp;
   }

   public boolean func_179850_c(BlockPos var1) {
      int var2 = var1.getX() - this.field_179859_a.getX();
      int var3 = var1.getZ() - this.field_179859_a.getY();
      return var2 * this.field_179858_c.getFrontOffsetX() + var3 * this.field_179858_c.getFrontOffsetZ() >= 0;
   }

   public int func_179848_a(BlockPos var1) {
      return (int)var1.distanceSq(this.func_179852_d());
   }

   public void func_179849_a(int var1) {
      this.lastActivityTimestamp = var1;
   }
}
