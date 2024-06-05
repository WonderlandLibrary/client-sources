package net;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

class aR extends EntityAIBase {
   private EntityEnderman b;

   public aR(EntityEnderman var1) {
      this.b = var1;
   }

   public boolean shouldExecute() {
      return this.b.worldObj.getGameRules().getBoolean("mobGriefing") && this.b.getHeldBlockState().getBlock().getMaterial() == Material.air && this.b.getRNG().nextInt(20) == 0;
   }

   public void updateTask() {
      Random var1 = this.b.getRNG();
      World var2 = this.b.worldObj;
      int var3 = MathHelper.floor_double(this.b.posX - 2.0D + var1.nextDouble() * 4.0D);
      int var4 = MathHelper.floor_double(this.b.posY + var1.nextDouble() * 3.0D);
      int var5 = MathHelper.floor_double(this.b.posZ - 2.0D + var1.nextDouble() * 4.0D);
      BlockPos var6 = new BlockPos(var3, var4, var5);
      IBlockState var7 = var2.getBlockState(var6);
      Block var8 = var7.getBlock();
      if(EntityEnderman.access$300().contains(var8)) {
         this.b.setHeldBlockState(var7);
         var2.setBlockState(var6, Blocks.air.getDefaultState());
      }

   }
}
