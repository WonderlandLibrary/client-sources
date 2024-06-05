package net;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

class ay extends EntityAIBase {
   private EntityEnderman b;

   public ay(EntityEnderman var1) {
      this.b = var1;
   }

   public boolean shouldExecute() {
      return this.b.worldObj.getGameRules().getBoolean("mobGriefing") && this.b.getHeldBlockState().getBlock().getMaterial() != Material.air && this.b.getRNG().nextInt(2000) == 0;
   }

   public void updateTask() {
      Random var1 = this.b.getRNG();
      World var2 = this.b.worldObj;
      int var3 = MathHelper.floor_double(this.b.posX - 1.0D + var1.nextDouble() * 2.0D);
      int var4 = MathHelper.floor_double(this.b.posY + var1.nextDouble() * 2.0D);
      int var5 = MathHelper.floor_double(this.b.posZ - 1.0D + var1.nextDouble() * 2.0D);
      BlockPos var6 = new BlockPos(var3, var4, var5);
      Block var7 = var2.getBlockState(var6).getBlock();
      Block var8 = var2.getBlockState(var6.down()).getBlock();
      if(this.a(var2, var6, this.b.getHeldBlockState().getBlock(), var7, var8)) {
         var2.setBlockState(var6, this.b.getHeldBlockState(), 3);
         this.b.setHeldBlockState(Blocks.air.getDefaultState());
      }

   }

   private boolean a(World var1, BlockPos var2, Block var3, Block var4, Block var5) {
      return var3.canPlaceBlockAt(var1, var2) && var4.getMaterial() == Material.air && var5.getMaterial() != Material.air && var5.isFullCube();
   }
}
