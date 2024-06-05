package net.minecraft.tileentity;

import net.minecraft.command.CommandResultStats;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock$1;

public class TileEntityCommandBlock extends TileEntity {
   private final CommandBlockLogic commandBlockLogic = new TileEntityCommandBlock$1(this);

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      this.commandBlockLogic.writeDataToNBT(var1);
   }

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      this.commandBlockLogic.readDataFromNBT(var1);
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      return new S35PacketUpdateTileEntity(this.pos, 2, var1);
   }

   public boolean func_183000_F() {
      return true;
   }

   public CommandBlockLogic getCommandBlockLogic() {
      return this.commandBlockLogic;
   }

   public CommandResultStats getCommandResultStats() {
      return this.commandBlockLogic.getCommandResultStats();
   }
}
