package net.minecraft.entity.ai;

import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

class EntityMinecartMobSpawner$1 extends MobSpawnerBaseLogic {
   final EntityMinecartMobSpawner this$0;

   EntityMinecartMobSpawner$1(EntityMinecartMobSpawner var1) {
      this.this$0 = var1;
   }

   public void func_98267_a(int var1) {
      this.this$0.worldObj.setEntityState(this.this$0, (byte)var1);
   }

   public World getSpawnerWorld() {
      return this.this$0.worldObj;
   }

   public BlockPos getSpawnerPosition() {
      return new BlockPos(this.this$0);
   }
}
