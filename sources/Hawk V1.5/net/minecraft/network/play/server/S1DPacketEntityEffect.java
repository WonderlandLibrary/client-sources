package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

public class S1DPacketEntityEffect implements Packet {
   private byte field_149432_b;
   private int field_149434_a;
   private byte field_179708_e;
   private byte field_149433_c;
   private static final String __OBFID = "CL_00001343";
   private int field_149431_d;

   public S1DPacketEntityEffect(int var1, PotionEffect var2) {
      this.field_149434_a = var1;
      this.field_149432_b = (byte)(var2.getPotionID() & 255);
      this.field_149433_c = (byte)(var2.getAmplifier() & 255);
      if (var2.getDuration() > 32767) {
         this.field_149431_d = 32767;
      } else {
         this.field_149431_d = var2.getDuration();
      }

      this.field_179708_e = (byte)(var2.func_180154_f() ? 1 : 0);
   }

   public boolean func_179707_f() {
      return this.field_179708_e != 0;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149434_a = var1.readVarIntFromBuffer();
      this.field_149432_b = var1.readByte();
      this.field_149433_c = var1.readByte();
      this.field_149431_d = var1.readVarIntFromBuffer();
      this.field_179708_e = var1.readByte();
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityEffect(this);
   }

   public boolean func_149429_c() {
      return this.field_149431_d == 32767;
   }

   public byte func_149427_e() {
      return this.field_149432_b;
   }

   public byte func_149428_f() {
      return this.field_149433_c;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public int func_149426_d() {
      return this.field_149434_a;
   }

   public S1DPacketEntityEffect() {
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149434_a);
      var1.writeByte(this.field_149432_b);
      var1.writeByte(this.field_149433_c);
      var1.writeVarIntToBuffer(this.field_149431_d);
      var1.writeByte(this.field_179708_e);
   }

   public int func_180755_e() {
      return this.field_149431_d;
   }
}
