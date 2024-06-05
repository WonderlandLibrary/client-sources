package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ListChunkFactory implements IRenderChunkFactory {
   public RenderChunk makeRenderChunk(World var1, RenderGlobal var2, BlockPos var3, int var4) {
      return new ListedRenderChunk(var1, var2, var3, var4);
   }
}
