package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S11PacketSpawnExperienceOrb implements Packet {
   private int field_148990_b;
   private static final String __OBFID = "CL_00001277";
   private int field_148989_e;
   private int field_148988_d;
   private int field_148991_c;
   private int field_148992_a;

   public int func_148983_e() {
      return this.field_148991_c;
   }

   public int func_148982_f() {
      return this.field_148988_d;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_148992_a = var1.readVarIntFromBuffer();
      this.field_148990_b = var1.readInt();
      this.field_148991_c = var1.readInt();
      this.field_148988_d = var1.readInt();
      this.field_148989_e = var1.readShort();
   }

   public S11PacketSpawnExperienceOrb() {
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_148992_a);
      var1.writeInt(this.field_148990_b);
      var1.writeInt(this.field_148991_c);
      var1.writeInt(this.field_148988_d);
      var1.writeShort(this.field_148989_e);
   }

   public void processPacket(INetHandler var1) {
      this.func_180719_a((INetHandlerPlayClient)var1);
   }

   public S11PacketSpawnExperienceOrb(EntityXPOrb var1) {
      this.field_148992_a = var1.getEntityId();
      this.field_148990_b = MathHelper.floor_double(var1.posX * 32.0D);
      this.field_148991_c = MathHelper.floor_double(var1.posY * 32.0D);
      this.field_148988_d = MathHelper.floor_double(var1.posZ * 32.0D);
      this.field_148989_e = var1.getXpValue();
   }

   public int func_148985_c() {
      return this.field_148992_a;
   }

   public int func_148984_d() {
      return this.field_148990_b;
   }

   public void func_180719_a(INetHandlerPlayClient var1) {
      var1.handleSpawnExperienceOrb(this);
   }

   public int func_148986_g() {
      return this.field_148989_e;
   }
}
