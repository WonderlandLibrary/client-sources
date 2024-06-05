package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S18PacketEntityTeleport implements Packet {
   private byte field_149455_e;
   private int field_149458_a;
   private int field_149457_c;
   private int field_149456_b;
   private boolean field_179698_g;
   private byte field_149453_f;
   private int field_149454_d;
   private static final String __OBFID = "CL_00001340";

   public byte func_149447_h() {
      return this.field_149453_f;
   }

   public S18PacketEntityTeleport(int var1, int var2, int var3, int var4, byte var5, byte var6, boolean var7) {
      this.field_149458_a = var1;
      this.field_149456_b = var2;
      this.field_149457_c = var3;
      this.field_149454_d = var4;
      this.field_149455_e = var5;
      this.field_149453_f = var6;
      this.field_179698_g = var7;
   }

   public int func_149451_c() {
      return this.field_149458_a;
   }

   public boolean func_179697_g() {
      return this.field_179698_g;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleEntityTeleport(this);
   }

   public int func_149448_e() {
      return this.field_149457_c;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149458_a = var1.readVarIntFromBuffer();
      this.field_149456_b = var1.readInt();
      this.field_149457_c = var1.readInt();
      this.field_149454_d = var1.readInt();
      this.field_149455_e = var1.readByte();
      this.field_149453_f = var1.readByte();
      this.field_179698_g = var1.readBoolean();
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149458_a);
      var1.writeInt(this.field_149456_b);
      var1.writeInt(this.field_149457_c);
      var1.writeInt(this.field_149454_d);
      var1.writeByte(this.field_149455_e);
      var1.writeByte(this.field_149453_f);
      var1.writeBoolean(this.field_179698_g);
   }

   public S18PacketEntityTeleport(Entity var1) {
      this.field_149458_a = var1.getEntityId();
      this.field_149456_b = MathHelper.floor_double(var1.posX * 32.0D);
      this.field_149457_c = MathHelper.floor_double(var1.posY * 32.0D);
      this.field_149454_d = MathHelper.floor_double(var1.posZ * 32.0D);
      this.field_149455_e = (byte)((int)(var1.rotationYaw * 256.0F / 360.0F));
      this.field_149453_f = (byte)((int)(var1.rotationPitch * 256.0F / 360.0F));
      this.field_179698_g = var1.onGround;
   }

   public S18PacketEntityTeleport() {
   }

   public int func_149449_d() {
      return this.field_149456_b;
   }

   public byte func_149450_g() {
      return this.field_149455_e;
   }

   public int func_149446_f() {
      return this.field_149454_d;
   }
}
