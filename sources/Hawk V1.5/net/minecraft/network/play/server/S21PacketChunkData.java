package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class S21PacketChunkData implements Packet {
   private boolean field_149279_g;
   private int field_149284_a;
   private S21PacketChunkData.Extracted field_179758_c;
   private int field_149282_b;
   private static final String __OBFID = "CL_00001304";

   private static int func_179757_a(byte[] var0, byte[] var1, int var2) {
      System.arraycopy(var0, 0, var1, var2, var0.length);
      return var2 + var0.length;
   }

   public boolean func_149274_i() {
      return this.field_149279_g;
   }

   public S21PacketChunkData() {
   }

   public S21PacketChunkData(Chunk var1, boolean var2, int var3) {
      this.field_149284_a = var1.xPosition;
      this.field_149282_b = var1.zPosition;
      this.field_149279_g = var2;
      this.field_179758_c = func_179756_a(var1, var2, !var1.getWorld().provider.getHasNoSky(), var3);
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public static S21PacketChunkData.Extracted func_179756_a(Chunk var0, boolean var1, boolean var2, int var3) {
      ExtendedBlockStorage[] var4 = var0.getBlockStorageArray();
      S21PacketChunkData.Extracted var5 = new S21PacketChunkData.Extracted();
      ArrayList var6 = Lists.newArrayList();

      int var7;
      for(var7 = 0; var7 < var4.length; ++var7) {
         ExtendedBlockStorage var8 = var4[var7];
         if (var8 != null && (!var1 || !var8.isEmpty()) && (var3 & 1 << var7) != 0) {
            var5.field_150280_b |= 1 << var7;
            var6.add(var8);
         }
      }

      var5.field_150282_a = new byte[func_180737_a(Integer.bitCount(var5.field_150280_b), var2, var1)];
      var7 = 0;
      Iterator var15 = var6.iterator();

      ExtendedBlockStorage var9;
      while(var15.hasNext()) {
         var9 = (ExtendedBlockStorage)var15.next();
         char[] var10 = var9.getData();
         char[] var11 = var10;
         int var12 = var10.length;

         for(int var13 = 0; var13 < var12; ++var13) {
            char var14 = var11[var13];
            var5.field_150282_a[var7++] = (byte)(var14 & 255);
            var5.field_150282_a[var7++] = (byte)(var14 >> 8 & 255);
         }
      }

      for(var15 = var6.iterator(); var15.hasNext(); var7 = func_179757_a(var9.getBlocklightArray().getData(), var5.field_150282_a, var7)) {
         var9 = (ExtendedBlockStorage)var15.next();
      }

      if (var2) {
         for(var15 = var6.iterator(); var15.hasNext(); var7 = func_179757_a(var9.getSkylightArray().getData(), var5.field_150282_a, var7)) {
            var9 = (ExtendedBlockStorage)var15.next();
         }
      }

      if (var1) {
         func_179757_a(var0.getBiomeArray(), var5.field_150282_a, var7);
      }

      return var5;
   }

   public int func_149271_f() {
      return this.field_149282_b;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeInt(this.field_149284_a);
      var1.writeInt(this.field_149282_b);
      var1.writeBoolean(this.field_149279_g);
      var1.writeShort((short)(this.field_179758_c.field_150280_b & '\uffff'));
      var1.writeByteArray(this.field_179758_c.field_150282_a);
   }

   public byte[] func_149272_d() {
      return this.field_179758_c.field_150282_a;
   }

   public int func_149276_g() {
      return this.field_179758_c.field_150280_b;
   }

   public int func_149273_e() {
      return this.field_149284_a;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleChunkData(this);
   }

   protected static int func_180737_a(int var0, boolean var1, boolean var2) {
      int var3 = var0 * 2 * 16 * 16 * 16;
      int var4 = var0 * 16 * 16 * 16 / 2;
      int var5 = var1 ? var0 * 16 * 16 * 16 / 2 : 0;
      int var6 = var2 ? 256 : 0;
      return var3 + var4 + var5 + var6;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149284_a = var1.readInt();
      this.field_149282_b = var1.readInt();
      this.field_149279_g = var1.readBoolean();
      this.field_179758_c = new S21PacketChunkData.Extracted();
      this.field_179758_c.field_150280_b = var1.readShort();
      this.field_179758_c.field_150282_a = var1.readByteArray();
   }

   public static class Extracted {
      public byte[] field_150282_a;
      public int field_150280_b;
      private static final String __OBFID = "CL_00001305";
   }
}
