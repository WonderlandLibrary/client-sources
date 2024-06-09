package net.minecraft.client.renderer;

import java.util.Iterator;
import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.util.EnumWorldBlockLayer;

public class RenderList extends ChunkRenderContainer
{
  private static final String __OBFID = "CL_00000957";
  
  public RenderList() {}
  
  public void func_178001_a(EnumWorldBlockLayer p_178001_1_)
  {
    if (field_178007_b)
    {
      Iterator var2 = field_178009_a.iterator();
      
      while (var2.hasNext())
      {
        net.minecraft.client.renderer.chunk.RenderChunk var3 = (net.minecraft.client.renderer.chunk.RenderChunk)var2.next();
        ListedRenderChunk var4 = (ListedRenderChunk)var3;
        GlStateManager.pushMatrix();
        func_178003_a(var3);
        org.lwjgl.opengl.GL11.glCallList(var4.func_178600_a(p_178001_1_, var4.func_178571_g()));
        GlStateManager.popMatrix();
      }
      
      GlStateManager.func_179117_G();
      field_178009_a.clear();
    }
  }
}
