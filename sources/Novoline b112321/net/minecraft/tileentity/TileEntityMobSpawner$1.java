package net.minecraft.tileentity;

import net.mk;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

class TileEntityMobSpawner$1 extends MobSpawnerBaseLogic {
   final TileEntityMobSpawner this$0;

   TileEntityMobSpawner$1(TileEntityMobSpawner var1) {
      this.this$0 = var1;
   }

   public void func_98267_a(int var1) {
      this.this$0.worldObj.addBlockEvent(this.this$0.pos, Blocks.mob_spawner, var1, 0);
   }

   public World getSpawnerWorld() {
      return this.this$0.worldObj;
   }

   public BlockPos getSpawnerPosition() {
      return this.this$0.pos;
   }

   public void a(mk var1) {
      super.a(var1);
      if(this.getSpawnerWorld() != null) {
         this.getSpawnerWorld().markBlockForUpdate(this.this$0.pos);
      }

   }
}
