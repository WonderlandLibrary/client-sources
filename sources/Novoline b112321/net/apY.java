package net;

import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.util.EnumWorldBlockLayer;

public class apY {
   public static CompiledChunk a(ListedRenderChunk var0) {
      return var0.getCompiledChunk();
   }

   public static int a(ListedRenderChunk var0, EnumWorldBlockLayer var1, CompiledChunk var2) {
      return var0.getDisplayList(var1, var2);
   }
}
