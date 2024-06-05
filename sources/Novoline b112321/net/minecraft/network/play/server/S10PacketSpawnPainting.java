package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityPainting$EnumArt;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class S10PacketSpawnPainting implements Packet {
   private int entityID;
   private BlockPos position;
   private EnumFacing facing;
   private String title;

   public S10PacketSpawnPainting() {
   }

   public S10PacketSpawnPainting(EntityPainting var1) {
      this.entityID = var1.getEntityID();
      this.position = var1.getHangingPosition();
      this.facing = var1.facingDirection;
      this.title = var1.art.title;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityID = var1.readVarIntFromBuffer();
      this.title = var1.a(EntityPainting$EnumArt.field_180001_A);
      this.position = var1.readBlockPos();
      this.facing = EnumFacing.getHorizontal(var1.readUnsignedByte());
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityID);
      var1.writeString(this.title);
      var1.writeBlockPos(this.position);
      var1.writeByte(this.facing.getHorizontalIndex());
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleSpawnPainting(this);
   }

   public int getEntityID() {
      return this.entityID;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public EnumFacing getFacing() {
      return this.facing;
   }

   public String getTitle() {
      return this.title;
   }
}
