package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S35PacketUpdateTileEntity implements Packet {
   private BlockPos field_179824_a;
   private static final String __OBFID = "CL_00001285";
   private NBTTagCompound nbt;
   private int metadata;

   public void processPacket(INetHandler var1) {
      this.func_180725_a((INetHandlerPlayClient)var1);
   }

   public void func_180725_a(INetHandlerPlayClient var1) {
      var1.handleUpdateTileEntity(this);
   }

   public S35PacketUpdateTileEntity() {
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeBlockPos(this.field_179824_a);
      var1.writeByte((byte)this.metadata);
      var1.writeNBTTagCompoundToBuffer(this.nbt);
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179824_a = var1.readBlockPos();
      this.metadata = var1.readUnsignedByte();
      this.nbt = var1.readNBTTagCompoundFromBuffer();
   }

   public S35PacketUpdateTileEntity(BlockPos var1, int var2, NBTTagCompound var3) {
      this.field_179824_a = var1;
      this.metadata = var2;
      this.nbt = var3;
   }

   public NBTTagCompound getNbtCompound() {
      return this.nbt;
   }

   public BlockPos func_179823_a() {
      return this.field_179824_a;
   }

   public int getTileEntityType() {
      return this.metadata;
   }
}
