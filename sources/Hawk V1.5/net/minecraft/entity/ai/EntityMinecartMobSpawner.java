package net.minecraft.entity.ai;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner extends EntityMinecart {
   private static final String __OBFID = "CL_00001678";
   private final MobSpawnerBaseLogic mobSpawnerLogic = new MobSpawnerBaseLogic(this) {
      private static final String __OBFID = "CL_00001679";
      final EntityMinecartMobSpawner this$0;

      {
         this.this$0 = var1;
      }

      public BlockPos func_177221_b() {
         return new BlockPos(this.this$0);
      }

      public World getSpawnerWorld() {
         return this.this$0.worldObj;
      }

      public void func_98267_a(int var1) {
         this.this$0.worldObj.setEntityState(this.this$0, (byte)var1);
      }
   };

   public EntityMinecart.EnumMinecartType func_180456_s() {
      return EntityMinecart.EnumMinecartType.SPAWNER;
   }

   public EntityMinecartMobSpawner(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   public void handleHealthUpdate(byte var1) {
      this.mobSpawnerLogic.setDelayToMin(var1);
   }

   public EntityMinecartMobSpawner(World var1) {
      super(var1);
   }

   public IBlockState func_180457_u() {
      return Blocks.mob_spawner.getDefaultState();
   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      this.mobSpawnerLogic.writeToNBT(var1);
   }

   public void onUpdate() {
      super.onUpdate();
      this.mobSpawnerLogic.updateSpawner();
   }

   public MobSpawnerBaseLogic func_98039_d() {
      return this.mobSpawnerLogic;
   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.mobSpawnerLogic.readFromNBT(var1);
   }
}
