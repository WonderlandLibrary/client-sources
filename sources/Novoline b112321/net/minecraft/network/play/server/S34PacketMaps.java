package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

public class S34PacketMaps implements Packet {
   private int mapId;
   private byte mapScale;
   private Vec4b[] mapVisiblePlayersVec4b;
   private int mapMinX;
   private int mapMinY;
   private int mapMaxX;
   private int mapMaxY;
   private byte[] mapDataBytes;

   public S34PacketMaps() {
   }

   public S34PacketMaps(int var1, byte var2, Collection var3, byte[] var4, int var5, int var6, int var7, int var8) {
      this.mapId = var1;
      this.mapScale = var2;
      this.mapVisiblePlayersVec4b = (Vec4b[])((Vec4b[])var3.toArray(new Vec4b[var3.size()]));
      this.mapMinX = var5;
      this.mapMinY = var6;
      this.mapMaxX = var7;
      this.mapMaxY = var8;
      this.mapDataBytes = new byte[var7 * var8];

      for(int var9 = 0; var9 < var7; ++var9) {
         for(int var10 = 0; var10 < var8; ++var10) {
            this.mapDataBytes[var9 + var10 * var7] = var4[var5 + var9 + (var6 + var10) * 128];
         }
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.mapId = var1.readVarIntFromBuffer();
      this.mapScale = var1.readByte();
      this.mapVisiblePlayersVec4b = new Vec4b[var1.readVarIntFromBuffer()];

      for(int var2 = 0; var2 < this.mapVisiblePlayersVec4b.length; ++var2) {
         short var3 = (short)var1.readByte();
         this.mapVisiblePlayersVec4b[var2] = new Vec4b((byte)(var3 >> 4 & 15), var1.readByte(), var1.readByte(), (byte)(var3 & 15));
      }

      this.mapMaxX = var1.readUnsignedByte();
      if(this.mapMaxX > 0) {
         this.mapMaxY = var1.readUnsignedByte();
         this.mapMinX = var1.readUnsignedByte();
         this.mapMinY = var1.readUnsignedByte();
         this.mapDataBytes = var1.readByteArray();
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.mapId);
      var1.writeByte(this.mapScale);
      var1.writeVarIntToBuffer(this.mapVisiblePlayersVec4b.length);

      for(Vec4b var5 : this.mapVisiblePlayersVec4b) {
         var1.writeByte((var5.func_176110_a() & 15) << 4 | var5.func_176111_d() & 15);
         var1.writeByte(var5.func_176112_b());
         var1.writeByte(var5.func_176113_c());
      }

      var1.writeByte(this.mapMaxX);
      if(this.mapMaxX > 0) {
         var1.writeByte(this.mapMaxY);
         var1.writeByte(this.mapMinX);
         var1.writeByte(this.mapMinY);
         var1.writeByteArray(this.mapDataBytes);
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleMaps(this);
   }

   public int getMapId() {
      return this.mapId;
   }

   public void setMapdataTo(MapData var1) {
      var1.scale = this.mapScale;
      var1.mapDecorations.clear();

      for(int var2 = 0; var2 < this.mapVisiblePlayersVec4b.length; ++var2) {
         Vec4b var3 = this.mapVisiblePlayersVec4b[var2];
         var1.mapDecorations.put("icon-" + var2, var3);
      }

      for(int var4 = 0; var4 < this.mapMaxX; ++var4) {
         for(int var5 = 0; var5 < this.mapMaxY; ++var5) {
            var1.colors[this.mapMinX + var4 + (this.mapMinY + var5) * 128] = this.mapDataBytes[var4 + var5 * this.mapMaxX];
         }
      }

   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
