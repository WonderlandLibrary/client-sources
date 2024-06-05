package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;

public class S26PacketMapChunkBulk implements Packet {
   private int[] field_149264_b;
   private int[] field_149266_a;
   private S21PacketChunkData.Extracted[] field_179755_c;
   private boolean field_149267_h;
   private static final String __OBFID = "CL_00001306";

   public S26PacketMapChunkBulk(List var1) {
      int var2 = var1.size();
      this.field_149266_a = new int[var2];
      this.field_149264_b = new int[var2];
      this.field_179755_c = new S21PacketChunkData.Extracted[var2];
      this.field_149267_h = !((Chunk)var1.get(0)).getWorld().provider.getHasNoSky();

      for(int var3 = 0; var3 < var2; ++var3) {
         Chunk var4 = (Chunk)var1.get(var3);
         S21PacketChunkData.Extracted var5 = S21PacketChunkData.func_179756_a(var4, true, this.field_149267_h, 65535);
         this.field_149266_a[var3] = var4.xPosition;
         this.field_149264_b[var3] = var4.zPosition;
         this.field_179755_c[var3] = var5;
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeBoolean(this.field_149267_h);
      var1.writeVarIntToBuffer(this.field_179755_c.length);

      int var2;
      for(var2 = 0; var2 < this.field_149266_a.length; ++var2) {
         var1.writeInt(this.field_149266_a[var2]);
         var1.writeInt(this.field_149264_b[var2]);
         var1.writeShort((short)(this.field_179755_c[var2].field_150280_b & '\uffff'));
      }

      for(var2 = 0; var2 < this.field_149266_a.length; ++var2) {
         var1.writeBytes(this.field_179755_c[var2].field_150282_a);
      }

   }

   public int func_149255_a(int var1) {
      return this.field_149266_a[var1];
   }

   public void func_180738_a(INetHandlerPlayClient var1) {
      var1.handleMapChunkBulk(this);
   }

   public S26PacketMapChunkBulk() {
   }

   public void processPacket(INetHandler var1) {
      this.func_180738_a((INetHandlerPlayClient)var1);
   }

   public int func_179754_d(int var1) {
      return this.field_179755_c[var1].field_150280_b;
   }

   public int func_149254_d() {
      return this.field_149266_a.length;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149267_h = var1.readBoolean();
      int var2 = var1.readVarIntFromBuffer();
      this.field_149266_a = new int[var2];
      this.field_149264_b = new int[var2];
      this.field_179755_c = new S21PacketChunkData.Extracted[var2];

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         this.field_149266_a[var3] = var1.readInt();
         this.field_149264_b[var3] = var1.readInt();
         this.field_179755_c[var3] = new S21PacketChunkData.Extracted();
         this.field_179755_c[var3].field_150280_b = var1.readShort() & '\uffff';
         this.field_179755_c[var3].field_150282_a = new byte[S21PacketChunkData.func_180737_a(Integer.bitCount(this.field_179755_c[var3].field_150280_b), this.field_149267_h, true)];
      }

      for(var3 = 0; var3 < var2; ++var3) {
         var1.readBytes(this.field_179755_c[var3].field_150282_a);
      }

   }

   public byte[] func_149256_c(int var1) {
      return this.field_179755_c[var1].field_150282_a;
   }

   public int func_149253_b(int var1) {
      return this.field_149264_b[var1];
   }
}
