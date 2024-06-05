package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S21PacketChunkData$Extracted;
import net.minecraft.world.chunk.Chunk;

public class S26PacketMapChunkBulk implements Packet {
   private int[] xPositions;
   private int[] zPositions;
   private S21PacketChunkData$Extracted[] chunksData;
   private boolean isOverworld;

   public S26PacketMapChunkBulk() {
   }

   public S26PacketMapChunkBulk(List var1) {
      int var2 = var1.size();
      this.xPositions = new int[var2];
      this.zPositions = new int[var2];
      this.chunksData = new S21PacketChunkData$Extracted[var2];
      this.isOverworld = !((Chunk)var1.get(0)).getWorld().provider.getHasNoSky();

      for(int var3 = 0; var3 < var2; ++var3) {
         Chunk var4 = (Chunk)var1.get(var3);
         S21PacketChunkData$Extracted var5 = S21PacketChunkData.func_179756_a(var4, true, this.isOverworld, '\uffff');
         this.xPositions[var3] = var4.xPosition;
         this.zPositions[var3] = var4.zPosition;
         this.chunksData[var3] = var5;
      }

   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.isOverworld = var1.readBoolean();
      int var2 = var1.readVarIntFromBuffer();
      this.xPositions = new int[var2];
      this.zPositions = new int[var2];
      this.chunksData = new S21PacketChunkData$Extracted[var2];

      for(int var3 = 0; var3 < var2; ++var3) {
         this.xPositions[var3] = var1.readInt();
         this.zPositions[var3] = var1.readInt();
         this.chunksData[var3] = new S21PacketChunkData$Extracted();
         this.chunksData[var3].dataSize = var1.readShort() & '\uffff';
         this.chunksData[var3].data = new byte[S21PacketChunkData.func_180737_a(Integer.bitCount(this.chunksData[var3].dataSize), this.isOverworld, true)];
      }

      for(int var4 = 0; var4 < var2; ++var4) {
         var1.readBytes(this.chunksData[var4].data);
      }

   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeBoolean(this.isOverworld);
      var1.writeVarIntToBuffer(this.chunksData.length);

      for(int var2 = 0; var2 < this.xPositions.length; ++var2) {
         var1.writeInt(this.xPositions[var2]);
         var1.writeInt(this.zPositions[var2]);
         var1.writeShort((short)(this.chunksData[var2].dataSize & '\uffff'));
      }

      for(int var3 = 0; var3 < this.xPositions.length; ++var3) {
         var1.writeBytes(this.chunksData[var3].data);
      }

   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleMapChunkBulk(this);
   }

   public int getChunkX(int var1) {
      return this.xPositions[var1];
   }

   public int getChunkZ(int var1) {
      return this.zPositions[var1];
   }

   public int getChunkCount() {
      return this.xPositions.length;
   }

   public byte[] getChunkBytes(int var1) {
      return this.chunksData[var1].data;
   }

   public int getChunkSize(int var1) {
      return this.chunksData[var1].dataSize;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
