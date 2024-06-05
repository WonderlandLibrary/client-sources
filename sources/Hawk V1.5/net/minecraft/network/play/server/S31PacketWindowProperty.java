package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S31PacketWindowProperty implements Packet {
   private int field_149186_a;
   private int field_149184_b;
   private static final String __OBFID = "CL_00001295";
   private int field_149185_c;

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149186_a = var1.readUnsignedByte();
      this.field_149184_b = var1.readShort();
      this.field_149185_c = var1.readShort();
   }

   public S31PacketWindowProperty() {
   }

   public S31PacketWindowProperty(int var1, int var2, int var3) {
      this.field_149186_a = var1;
      this.field_149184_b = var2;
      this.field_149185_c = var3;
   }

   public int func_149182_c() {
      return this.field_149186_a;
   }

   public void func_180733_a(INetHandlerPlayClient var1) {
      var1.handleWindowProperty(this);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeByte(this.field_149186_a);
      var1.writeShort(this.field_149184_b);
      var1.writeShort(this.field_149185_c);
   }

   public int func_149181_d() {
      return this.field_149184_b;
   }

   public void processPacket(INetHandler var1) {
      this.func_180733_a((INetHandlerPlayClient)var1);
   }

   public int func_149180_e() {
      return this.field_149185_c;
   }
}
