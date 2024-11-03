package com.viaversion.viaversion.util;

import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import java.util.ArrayList;

public class ChunkUtil {
   public static Chunk createEmptyChunk(int chunkX, int chunkZ) {
      ChunkSection[] airSections = new ChunkSection[16];

      for (int i = 0; i < airSections.length; i++) {
         airSections[i] = new ChunkSectionImpl(true);
         airSections[i].palette(PaletteType.BLOCKS).addId(0);
      }

      return new BaseChunk(chunkX, chunkZ, true, false, 65535, airSections, new int[256], new ArrayList<>());
   }
}
