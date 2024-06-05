package net.minecraft.entity.monster;

import java.util.Random;
import net.aXp;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

class EntitySilverfish$AIHideInStone extends EntityAIWander {
   private final EntitySilverfish field_179485_a;
   private EnumFacing facing;
   private boolean field_179484_c;

   public EntitySilverfish$AIHideInStone(EntitySilverfish var1) {
      super(var1, 1.0D, 10);
      this.field_179485_a = var1;
      this.setMutexBits(1);
   }

   public boolean shouldExecute() {
      if(this.field_179485_a.getAttackTarget() != null) {
         return false;
      } else if(!this.field_179485_a.getNavigator().noPath()) {
         return false;
      } else {
         Random var1 = this.field_179485_a.getRNG();
         if(var1.nextInt(10) == 0) {
            this.facing = EnumFacing.random(var1);
            BlockPos var2 = (new BlockPos(this.field_179485_a.posX, this.field_179485_a.posY + 0.5D, this.field_179485_a.posZ)).offset(this.facing);
            IBlockState var3 = this.field_179485_a.worldObj.getBlockState(var2);
            if(BlockSilverfish.canContainSilverfish(var3)) {
               this.field_179484_c = true;
               return true;
            }
         }

         this.field_179484_c = false;
         return super.shouldExecute();
      }
   }

   public boolean continueExecuting() {
      return !this.field_179484_c && super.continueExecuting();
   }

   public void startExecuting() {
      if(!this.field_179484_c) {
         super.startExecuting();
      } else {
         World var1 = this.field_179485_a.worldObj;
         BlockPos var2 = (new BlockPos(this.field_179485_a.posX, this.field_179485_a.posY + 0.5D, this.field_179485_a.posZ)).offset(this.facing);
         IBlockState var3 = var1.getBlockState(var2);
         if(BlockSilverfish.canContainSilverfish(var3)) {
            var1.setBlockState(var2, Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT, aXp.a(var3)), 3);
            this.field_179485_a.spawnExplosionParticle();
            this.field_179485_a.setDead();
         }
      }

   }
}
