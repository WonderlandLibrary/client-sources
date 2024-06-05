package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;

final class CompiledChunk$1 extends CompiledChunk {
   protected void setLayerUsed(EnumWorldBlockLayer var1) {
      throw new UnsupportedOperationException();
   }

   public void setLayerStarted(EnumWorldBlockLayer var1) {
      throw new UnsupportedOperationException();
   }

   public boolean isVisible(EnumFacing var1, EnumFacing var2) {
      return false;
   }
}
