package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.util.ChunkUtil;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class ChunkType1_9_3 extends Type<Chunk> {
   private static final ChunkType1_9_3 WITH_SKYLIGHT = new ChunkType1_9_3(true);
   private static final ChunkType1_9_3 WITHOUT_SKYLIGHT = new ChunkType1_9_3(false);
   private final boolean hasSkyLight;

   public ChunkType1_9_3(boolean hasSkyLight) {
      super(Chunk.class);
      this.hasSkyLight = hasSkyLight;
   }

   public static ChunkType1_9_3 forEnvironment(Environment environment) {
      return environment == Environment.NORMAL ? WITH_SKYLIGHT : WITHOUT_SKYLIGHT;
   }

   public Chunk read(ByteBuf input) throws Exception {
      int chunkX = input.readInt();
      int chunkZ = input.readInt();
      boolean fullChunk = input.readBoolean();
      int primaryBitmask = Type.VAR_INT.readPrimitive(input);
      ByteBuf data = input.readSlice(Type.VAR_INT.readPrimitive(input));
      ChunkSection[] sections = new ChunkSection[16];
      int[] biomeData = fullChunk ? new int[256] : null;

      try {
         for (int i = 0; i < 16; i++) {
            if ((primaryBitmask & 1 << i) != 0) {
               ChunkSection section = Types1_9.CHUNK_SECTION.read(data);
               sections[i] = section;
               section.getLight().readBlockLight(data);
               if (this.hasSkyLight) {
                  section.getLight().readSkyLight(data);
               }
            }
         }

         if (fullChunk) {
            for (int ix = 0; ix < 256; ix++) {
               biomeData[ix] = data.readByte() & 255;
            }
         }
      } catch (Throwable var11) {
         Via.getPlatform().getLogger().log(Level.WARNING, "The server sent an invalid chunk data packet, returning an empty chunk instead", var11);
         return ChunkUtil.createEmptyChunk(chunkX, chunkZ);
      }

      List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList(Type.NAMED_COMPOUND_TAG_ARRAY.read(input)));
      return new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, nbtData);
   }

   public void write(ByteBuf output, Chunk chunk) throws Exception {
      output.writeInt(chunk.getX());
      output.writeInt(chunk.getZ());
      output.writeBoolean(chunk.isFullChunk());
      Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
      ByteBuf buf = output.alloc().buffer();

      try {
         for (int i = 0; i < 16; i++) {
            ChunkSection section = chunk.getSections()[i];
            if (section != null) {
               Types1_9.CHUNK_SECTION.write(buf, section);
               section.getLight().writeBlockLight(buf);
               if (section.getLight().hasSkyLight()) {
                  section.getLight().writeSkyLight(buf);
               }
            }
         }

         buf.readerIndex(0);
         Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 256 : 0));
         output.writeBytes(buf);
      } finally {
         buf.release();
      }

      if (chunk.isBiomeData()) {
         for (int biome : chunk.getBiomeData()) {
            output.writeByte((byte)biome);
         }
      }

      Type.NAMED_COMPOUND_TAG_ARRAY.write(output, chunk.getBlockEntities().toArray(new CompoundTag[0]));
   }
}
