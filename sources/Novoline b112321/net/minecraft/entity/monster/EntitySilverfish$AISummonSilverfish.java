package net.minecraft.entity.monster;

import java.util.Random;
import net.aXp;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

class EntitySilverfish$AISummonSilverfish extends EntityAIBase {
   private EntitySilverfish silverfish;
   private int field_179463_b;

   public EntitySilverfish$AISummonSilverfish(EntitySilverfish var1) {
      this.silverfish = var1;
   }

   public void func_179462_f() {
      if(this.field_179463_b == 0) {
         this.field_179463_b = 20;
      }

   }

   public boolean shouldExecute() {
      return this.field_179463_b > 0;
   }

   public void updateTask() {
      --this.field_179463_b;
      if(this.field_179463_b <= 0) {
         World var1 = this.silverfish.worldObj;
         Random var2 = this.silverfish.getRNG();
         BlockPos var3 = new BlockPos(this.silverfish);

         for(int var4 = 0; var4 <= 5 && var4 >= -5; var4 = 1 - var4) {
            for(int var5 = 0; var5 <= 10 && var5 >= -10; var5 = 1 - var5) {
               for(int var6 = 0; var6 <= 10 && var6 >= -10; var6 = 1 - var6) {
                  BlockPos var7 = var3.a(var5, var4, var6);
                  IBlockState var8 = var1.getBlockState(var7);
                  if(var8.getBlock() == Blocks.monster_egg) {
                     if(var1.getGameRules().getBoolean("mobGriefing")) {
                        var1.destroyBlock(var7, true);
                     } else {
                        var1.setBlockState(var7, ((aXp)var8.getValue(BlockSilverfish.VARIANT)).getModelBlock(), 3);
                     }

                     if(var2.nextBoolean()) {
                        return;
                     }
                  }
               }
            }
         }
      }

   }
}
