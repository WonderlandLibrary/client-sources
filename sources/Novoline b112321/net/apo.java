package net;

import net.apC;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class apo extends apC {
   public AxisAlignedBB e;
   private Block d;
   private BlockPos c;

   public apo(Block var1, BlockPos var2, AxisAlignedBB var3) {
      this.d = var1;
      this.c = var2;
      this.e = var3;
   }

   public Block b() {
      return this.d;
   }

   public void a(Block var1) {
      this.d = var1;
   }

   public BlockPos a() {
      return this.c;
   }

   public AxisAlignedBB c() {
      return this.e;
   }

   public void a(AxisAlignedBB var1) {
      this.e = var1;
   }

   public void a(BlockPos var1) {
      this.c = var1;
   }
}
