package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S32PacketConfirmTransaction implements Packet {
   private int field_148894_a;
   private short field_148892_b;
   private static final String __OBFID = "CL_00001291";
   private boolean field_148893_c;

   public int func_148889_c() {
      return this.field_148894_a;
   }

   public void func_180730_a(INetHandlerPlayClient var1) {
      var1.handleConfirmTransaction(this);
   }

   public void processPacket(INetHandler var1) {
      this.func_180730_a((INetHandlerPlayClient)var1);
   }

   public S32PacketConfirmTransaction() {
   }

   public short func_148890_d() {
      return this.field_148892_b;
   }

   public S32PacketConfirmTransaction(int var1, short var2, boolean var3) {
      this.field_148894_a = var1;
      this.field_148892_b = var2;
      this.field_148893_c = var3;
   }

   public boolean func_148888_e() {
      return this.field_148893_c;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_148894_a = var1.readUnsignedByte();
      this.field_148892_b = var1.readShort();
      this.field_148893_c = var1.readBoolean();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.field_148894_a);
      var1.writeShort(this.field_148892_b);
      var1.writeBoolean(this.field_148893_c);
   }
}
