package net.minecraft.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TileEntityMobSpawner extends TileEntity implements IUpdatePlayerListBox {
   private static final String __OBFID = "CL_00000360";
   private final MobSpawnerBaseLogic field_145882_a = new MobSpawnerBaseLogic(this) {
      final TileEntityMobSpawner this$0;
      private static final String __OBFID = "CL_00000361";

      public BlockPos func_177221_b() {
         return this.this$0.pos;
      }

      public void func_98267_a(int var1) {
         this.this$0.worldObj.addBlockEvent(this.this$0.pos, Blocks.mob_spawner, var1, 0);
      }

      {
         this.this$0 = var1;
      }

      public World getSpawnerWorld() {
         return this.this$0.worldObj;
      }

      public void setRandomEntity(MobSpawnerBaseLogic.WeightedRandomMinecart var1) {
         super.setRandomEntity(var1);
         if (this.getSpawnerWorld() != null) {
            this.getSpawnerWorld().markBlockForUpdate(this.this$0.pos);
         }

      }
   };

   public MobSpawnerBaseLogic getSpawnerBaseLogic() {
      return this.field_145882_a;
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      this.field_145882_a.writeToNBT(var1);
   }

   public boolean receiveClientEvent(int var1, int var2) {
      return this.field_145882_a.setDelayToMin(var1) ? true : super.receiveClientEvent(var1, var2);
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      var1.removeTag("SpawnPotentials");
      return new S35PacketUpdateTileEntity(this.pos, 1, var1);
   }

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      this.field_145882_a.readFromNBT(var1);
   }

   public void update() {
      this.field_145882_a.updateSpawner();
   }
}
