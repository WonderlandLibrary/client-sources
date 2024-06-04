package net.minecraft.client.renderer;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumWorldBlockLayer;
import org.lwjgl.opengl.GL11;

public class RenderList extends ChunkRenderContainer
{
  public RenderList() {}
  
  public void func_178001_a(EnumWorldBlockLayer p_178001_1_)
  {
    if (field_178007_b)
    {
      if (field_178009_a.size() == 0)
      {
        return;
      }
      
      Iterator var2 = field_178009_a.iterator();
      
      while (var2.hasNext())
      {
        RenderChunk var3 = (RenderChunk)var2.next();
        ListedRenderChunk var4 = (ListedRenderChunk)var3;
        GlStateManager.pushMatrix();
        func_178003_a(var3);
        GL11.glCallList(var4.func_178600_a(p_178001_1_, var4.func_178571_g()));
        GlStateManager.popMatrix();
      }
      
      if (optifine.Config.isMultiTexture())
      {
        GlStateManager.bindCurrentTexture();
      }
      
      GlStateManager.func_179117_G();
      field_178009_a.clear();
    }
  }
}
