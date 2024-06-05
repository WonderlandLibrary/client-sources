package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class S10PacketSpawnPainting implements Packet {
   private String field_148968_f;
   private BlockPos field_179838_b;
   private static final String __OBFID = "CL_00001280";
   private EnumFacing field_179839_c;
   private int field_148973_a;

   public BlockPos func_179837_b() {
      return this.field_179838_b;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_148973_a = var1.readVarIntFromBuffer();
      this.field_148968_f = var1.readStringFromBuffer(EntityPainting.EnumArt.field_180001_A);
      this.field_179838_b = var1.readBlockPos();
      this.field_179839_c = EnumFacing.getHorizontal(var1.readUnsignedByte());
   }

   public S10PacketSpawnPainting() {
   }

   public String func_148961_h() {
      return this.field_148968_f;
   }

   public EnumFacing func_179836_c() {
      return this.field_179839_c;
   }

   public int func_148965_c() {
      return this.field_148973_a;
   }

   public void func_180722_a(INetHandlerPlayClient var1) {
      var1.handleSpawnPainting(this);
   }

   public S10PacketSpawnPainting(EntityPainting var1) {
      this.field_148973_a = var1.getEntityId();
      this.field_179838_b = var1.func_174857_n();
      this.field_179839_c = var1.field_174860_b;
      this.field_148968_f = var1.art.title;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_148973_a);
      var1.writeString(this.field_148968_f);
      var1.writeBlockPos(this.field_179838_b);
      var1.writeByte(this.field_179839_c.getHorizontalIndex());
   }

   public void processPacket(INetHandler var1) {
      this.func_180722_a((INetHandlerPlayClient)var1);
   }
}
