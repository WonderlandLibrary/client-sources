package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S49PacketUpdateEntityNBT implements Packet {
   private int entityId;
   private NBTTagCompound tagCompound;

   public S49PacketUpdateEntityNBT() {
   }

   public S49PacketUpdateEntityNBT(int var1, NBTTagCompound var2) {
      this.entityId = var1;
      this.tagCompound = var2;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      this.tagCompound = var1.readNBTTagCompoundFromBuffer();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      var1.writeNBTTagCompoundToBuffer(this.tagCompound);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityNBT(this);
   }

   public NBTTagCompound getTagCompound() {
      return this.tagCompound;
   }

   public Entity getEntity(World var1) {
      return var1.getEntityByID(this.entityId);
   }
}
