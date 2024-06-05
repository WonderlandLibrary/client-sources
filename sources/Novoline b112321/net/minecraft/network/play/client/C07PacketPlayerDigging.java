package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class C07PacketPlayerDigging implements Packet {
   private BlockPos position;
   private EnumFacing facing;
   private C07PacketPlayerDigging$Action status;

   public C07PacketPlayerDigging() {
   }

   public C07PacketPlayerDigging(C07PacketPlayerDigging$Action var1, BlockPos var2, EnumFacing var3) {
      this.status = var1;
      this.position = var2;
      this.facing = var3;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.status = (C07PacketPlayerDigging$Action)var1.readEnumValue(C07PacketPlayerDigging$Action.class);
      this.position = var1.readBlockPos();
      this.facing = EnumFacing.getFront(var1.readUnsignedByte());
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeEnumValue(this.status);
      var1.writeBlockPos(this.position);
      var1.writeByte(this.facing.getIndex());
   }

   public void processPacket(INetHandlerPlayServer var1) {
      var1.processPlayerDigging(this);
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public EnumFacing getFacing() {
      return this.facing;
   }

   public C07PacketPlayerDigging$Action getStatus() {
      return this.status;
   }
}
