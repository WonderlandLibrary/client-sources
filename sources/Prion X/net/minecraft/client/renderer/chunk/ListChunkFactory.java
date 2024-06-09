package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RenderGlobal;

public class ListChunkFactory implements IRenderChunkFactory
{
  public ListChunkFactory() {}
  
  public RenderChunk func_178602_a(net.minecraft.world.World worldIn, RenderGlobal p_178602_2_, net.minecraft.util.BlockPos p_178602_3_, int p_178602_4_)
  {
    return new ListedRenderChunk(worldIn, p_178602_2_, p_178602_3_, p_178602_4_);
  }
}
