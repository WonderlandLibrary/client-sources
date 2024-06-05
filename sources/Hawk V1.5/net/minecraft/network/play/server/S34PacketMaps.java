package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

public class S34PacketMaps implements Packet {
   private int field_179736_g;
   private Vec4b[] field_179740_c;
   private int field_179738_e;
   private int mapId;
   private int field_179735_f;
   private byte[] field_179741_h;
   private byte field_179739_b;
   private int field_179737_d;
   private static final String __OBFID = "CL_00001311";

   public void func_179734_a(MapData var1) {
      var1.scale = this.field_179739_b;
      var1.playersVisibleOnMap.clear();

      int var2;
      for(var2 = 0; var2 < this.field_179740_c.length; ++var2) {
         Vec4b var3 = this.field_179740_c[var2];
         var1.playersVisibleOnMap.put(String.valueOf((new StringBuilder("icon-")).append(var2)), var3);
      }

      for(var2 = 0; var2 < this.field_179735_f; ++var2) {
         for(int var4 = 0; var4 < this.field_179736_g; ++var4) {
            var1.colors[this.field_179737_d + var2 + (this.field_179738_e + var4) * 128] = this.field_179741_h[var2 + var4 * this.field_179735_f];
         }
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.mapId = var1.readVarIntFromBuffer();
      this.field_179739_b = var1.readByte();
      this.field_179740_c = new Vec4b[var1.readVarIntFromBuffer()];

      for(int var2 = 0; var2 < this.field_179740_c.length; ++var2) {
         short var3 = (short)var1.readByte();
         this.field_179740_c[var2] = new Vec4b((byte)(var3 >> 4 & 15), var1.readByte(), var1.readByte(), (byte)(var3 & 15));
      }

      this.field_179735_f = var1.readUnsignedByte();
      if (this.field_179735_f > 0) {
         this.field_179736_g = var1.readUnsignedByte();
         this.field_179737_d = var1.readUnsignedByte();
         this.field_179738_e = var1.readUnsignedByte();
         this.field_179741_h = var1.readByteArray();
      }

   }

   public int getMapId() {
      return this.mapId;
   }

   public S34PacketMaps() {
   }

   public S34PacketMaps(int var1, byte var2, Collection var3, byte[] var4, int var5, int var6, int var7, int var8) {
      this.mapId = var1;
      this.field_179739_b = var2;
      this.field_179740_c = (Vec4b[])var3.toArray(new Vec4b[var3.size()]);
      this.field_179737_d = var5;
      this.field_179738_e = var6;
      this.field_179735_f = var7;
      this.field_179736_g = var8;
      this.field_179741_h = new byte[var7 * var8];

      for(int var9 = 0; var9 < var7; ++var9) {
         for(int var10 = 0; var10 < var8; ++var10) {
            this.field_179741_h[var9 + var10 * var7] = var4[var5 + var9 + (var6 + var10) * 128];
         }
      }

   }

   public void processPacket(INetHandler var1) {
      this.func_180741_a((INetHandlerPlayClient)var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.mapId);
      var1.writeByte(this.field_179739_b);
      var1.writeVarIntToBuffer(this.field_179740_c.length);
      Vec4b[] var2 = this.field_179740_c;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Vec4b var5 = var2[var4];
         var1.writeByte((var5.func_176110_a() & 15) << 4 | var5.func_176111_d() & 15);
         var1.writeByte(var5.func_176112_b());
         var1.writeByte(var5.func_176113_c());
      }

      var1.writeByte(this.field_179735_f);
      if (this.field_179735_f > 0) {
         var1.writeByte(this.field_179736_g);
         var1.writeByte(this.field_179737_d);
         var1.writeByte(this.field_179738_e);
         var1.writeByteArray(this.field_179741_h);
      }

   }

   public void func_180741_a(INetHandlerPlayClient var1) {
      var1.handleMaps(this);
   }
}
