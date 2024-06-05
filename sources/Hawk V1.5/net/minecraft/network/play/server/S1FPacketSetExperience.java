package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S1FPacketSetExperience implements Packet {
   private float field_149401_a;
   private static final String __OBFID = "CL_00001331";
   private int field_149399_b;
   private int field_149400_c;

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149401_a = var1.readFloat();
      this.field_149400_c = var1.readVarIntFromBuffer();
      this.field_149399_b = var1.readVarIntFromBuffer();
   }

   public float func_149397_c() {
      return this.field_149401_a;
   }

   public int func_149396_d() {
      return this.field_149399_b;
   }

   public int func_149395_e() {
      return this.field_149400_c;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeFloat(this.field_149401_a);
      var1.writeVarIntToBuffer(this.field_149400_c);
      var1.writeVarIntToBuffer(this.field_149399_b);
   }

   public void func_180749_a(INetHandlerPlayClient var1) {
      var1.handleSetExperience(this);
   }

   public S1FPacketSetExperience() {
   }

   public S1FPacketSetExperience(float var1, int var2, int var3) {
      this.field_149401_a = var1;
      this.field_149399_b = var2;
      this.field_149400_c = var3;
   }

   public void processPacket(INetHandler var1) {
      this.func_180749_a((INetHandlerPlayClient)var1);
   }
}
