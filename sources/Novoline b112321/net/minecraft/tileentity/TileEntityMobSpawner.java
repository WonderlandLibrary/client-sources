package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner$1;
import net.minecraft.util.ITickable;

public class TileEntityMobSpawner extends TileEntity implements ITickable {
   private final MobSpawnerBaseLogic spawnerLogic = new TileEntityMobSpawner$1(this);

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      this.spawnerLogic.readFromNBT(var1);
   }

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      this.spawnerLogic.writeToNBT(var1);
   }

   public void update() {
      this.spawnerLogic.updateSpawner();
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      var1.removeTag("SpawnPotentials");
      return new S35PacketUpdateTileEntity(this.pos, 1, var1);
   }

   public boolean receiveClientEvent(int var1, int var2) {
      return this.spawnerLogic.setDelayToMin(var1) || super.receiveClientEvent(var1, var2);
   }

   public boolean func_183000_F() {
      return true;
   }

   public MobSpawnerBaseLogic getSpawnerBaseLogic() {
      return this.spawnerLogic;
   }
}
