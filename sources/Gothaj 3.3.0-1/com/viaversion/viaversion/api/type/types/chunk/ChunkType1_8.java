package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.util.ChunkUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.logging.Level;

public class ChunkType1_8 extends Type<Chunk> {
   private static final ChunkType1_8 WITH_SKYLIGHT = new ChunkType1_8(true);
   private static final ChunkType1_8 WITHOUT_SKYLIGHT = new ChunkType1_8(false);
   private final boolean hasSkyLight;

   public ChunkType1_8(boolean hasSkyLight) {
      super(Chunk.class);
      this.hasSkyLight = hasSkyLight;
   }

   public static ChunkType1_8 forEnvironment(Environment environment) {
      return environment == Environment.NORMAL ? WITH_SKYLIGHT : WITHOUT_SKYLIGHT;
   }

   public Chunk read(ByteBuf input) throws Exception {
      int chunkX = input.readInt();
      int chunkZ = input.readInt();
      boolean fullChunk = input.readBoolean();
      int bitmask = input.readUnsignedShort();
      int dataLength = Type.VAR_INT.readPrimitive(input);
      byte[] data = new byte[dataLength];
      input.readBytes(data);
      if (fullChunk && bitmask == 0) {
         return new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList<>());
      } else {
         try {
            return deserialize(chunkX, chunkZ, fullChunk, this.hasSkyLight, bitmask, data);
         } catch (Throwable var9) {
            Via.getPlatform().getLogger().log(Level.WARNING, "The server sent an invalid chunk data packet, returning an empty chunk instead", var9);
            return ChunkUtil.createEmptyChunk(chunkX, chunkZ);
         }
      }
   }

   public void write(ByteBuf output, Chunk chunk) throws Exception {
      output.writeInt(chunk.getX());
      output.writeInt(chunk.getZ());
      output.writeBoolean(chunk.isFullChunk());
      output.writeShort(chunk.getBitmask());
      byte[] data = serialize(chunk);
      Type.VAR_INT.writePrimitive(output, data.length);
      output.writeBytes(data);
   }

   public static Chunk deserialize(int chunkX, int chunkZ, boolean fullChunk, boolean skyLight, int bitmask, byte[] data) throws Exception {
      ByteBuf input = Unpooled.wrappedBuffer(data);
      ChunkSection[] sections = new ChunkSection[16];
      int[] biomeData = null;

      for (int i = 0; i < sections.length; i++) {
         if ((bitmask & 1 << i) != 0) {
            sections[i] = Types1_8.CHUNK_SECTION.read(input);
         }
      }

      for (int ix = 0; ix < sections.length; ix++) {
         if ((bitmask & 1 << ix) != 0) {
            sections[ix].getLight().readBlockLight(input);
         }
      }

      if (skyLight) {
         for (int ixx = 0; ixx < sections.length; ixx++) {
            if ((bitmask & 1 << ixx) != 0) {
               sections[ixx].getLight().readSkyLight(input);
            }
         }
      }

      if (fullChunk) {
         biomeData = new int[256];

         for (int ixxx = 0; ixxx < 256; ixxx++) {
            biomeData[ixxx] = input.readUnsignedByte();
         }
      }

      input.release();
      return new BaseChunk(chunkX, chunkZ, fullChunk, false, bitmask, sections, biomeData, new ArrayList<>());
   }

   public static byte[] serialize(Chunk chunk) throws Exception {
      ByteBuf output = Unpooled.buffer();

      for (int i = 0; i < chunk.getSections().length; i++) {
         if ((chunk.getBitmask() & 1 << i) != 0) {
            Types1_8.CHUNK_SECTION.write(output, chunk.getSections()[i]);
         }
      }

      for (int ix = 0; ix < chunk.getSections().length; ix++) {
         if ((chunk.getBitmask() & 1 << ix) != 0) {
            chunk.getSections()[ix].getLight().writeBlockLight(output);
         }
      }

      for (int ixx = 0; ixx < chunk.getSections().length; ixx++) {
         if ((chunk.getBitmask() & 1 << ixx) != 0 && chunk.getSections()[ixx].getLight().hasSkyLight()) {
            chunk.getSections()[ixx].getLight().writeSkyLight(output);
         }
      }

      if (chunk.isFullChunk() && chunk.getBiomeData() != null) {
         for (int biome : chunk.getBiomeData()) {
            output.writeByte((byte)biome);
         }
      }

      byte[] data = new byte[output.readableBytes()];
      output.readBytes(data);
      output.release();
      return data;
   }
}
