package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RenderGlobal;

public class VboChunkFactory implements IRenderChunkFactory
{
  private static final String __OBFID = "CL_00002451";
  
  public VboChunkFactory() {}
  
  public RenderChunk func_178602_a(net.minecraft.world.World worldIn, RenderGlobal p_178602_2_, net.minecraft.util.BlockPos p_178602_3_, int p_178602_4_)
  {
    return new RenderChunk(worldIn, p_178602_2_, p_178602_3_, p_178602_4_);
  }
}
