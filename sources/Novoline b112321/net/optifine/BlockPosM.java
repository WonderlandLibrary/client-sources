package net.optifine;

import net.acE;
import net.q3;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.optifine.MatchBlock;

public class BlockPosM extends BlockPos {
   private int mx;
   private int my;
   private int mz;
   private int level;
   private BlockPosM[] facings;
   private boolean needsUpdate;

   public BlockPosM(int var1, int var2, int var3) {
      this(var1, var2, var3, 0);
   }

   public BlockPosM(double var1, double var3, double var5) {
      this(MathHelper.floor_double(var1), MathHelper.floor_double(var3), MathHelper.floor_double(var5));
   }

   public BlockPosM(int var1, int var2, int var3, int var4) {
      super(0, 0, 0);
      this.mx = var1;
      this.my = var2;
      this.mz = var3;
      this.level = var4;
   }

   public int getX() {
      return this.mx;
   }

   public int getY() {
      return this.my;
   }

   public int getZ() {
      return this.mz;
   }

   public void setXyz(int var1, int var2, int var3) {
      this.mx = var1;
      this.my = var2;
      this.mz = var3;
      this.needsUpdate = true;
   }

   public void setXyz(double var1, double var3, double var5) {
      this.setXyz(MathHelper.floor_double(var1), MathHelper.floor_double(var3), MathHelper.floor_double(var5));
   }

   public BlockPos offset(EnumFacing var1) {
      acE[] var2 = MatchBlock.b();
      if(this.level <= 0) {
         return super.offset(var1, 1);
      } else {
         if(this.facings == null) {
            this.facings = new BlockPosM[EnumFacing.VALUES.length];
         }

         if(this.needsUpdate) {
            this.update();
         }

         int var3 = var1.getIndex();
         BlockPosM var4 = this.facings[var3];
         if(var4 == null) {
            int var5 = this.mx + var1.getFrontOffsetX();
            int var6 = this.my + var1.getFrontOffsetY();
            int var7 = this.mz + var1.getFrontOffsetZ();
            var4 = new BlockPosM(var5, var6, var7, this.level - 1);
            this.facings[var3] = var4;
         }

         return var4;
      }
   }

   public BlockPos offset(EnumFacing var1, int var2) {
      return var2 == 1?this.offset(var1):super.offset(var1, var2);
   }

   private void update() {
      MatchBlock.b();
      int var2 = 0;
      if(var2 < 6) {
         BlockPosM var3 = this.facings[var2];
         if(var3 != null) {
            EnumFacing var4 = EnumFacing.VALUES[var2];
            int var5 = this.mx + var4.getFrontOffsetX();
            int var6 = this.my + var4.getFrontOffsetY();
            int var7 = this.mz + var4.getFrontOffsetZ();
            var3.setXyz(var5, var6, var7);
         }

         ++var2;
      }

      this.needsUpdate = false;
   }

   public static Iterable getAllInBoxMutable(BlockPos var0, BlockPos var1) {
      BlockPos var2 = new BlockPos(Math.min(var0.getX(), var1.getX()), Math.min(var0.getY(), var1.getY()), Math.min(var0.getZ(), var1.getZ()));
      BlockPos var3 = new BlockPos(Math.max(var0.getX(), var1.getX()), Math.max(var0.getY(), var1.getY()), Math.max(var0.getZ(), var1.getZ()));
      return new q3(var2, var3);
   }

   public BlockPos getImmutable() {
      return new BlockPos(this.getX(), this.getY(), this.getZ());
   }
}
