package net.minecraft.client.renderer;

import java.util.Iterator;
import net.minecraft.client.renderer.chunk.RenderChunk;
import org.lwjgl.opengl.GL11;

public class VboRenderList extends ChunkRenderContainer
{
  private static final String __OBFID = "CL_00002533";
  
  public VboRenderList() {}
  
  public void func_178001_a(net.minecraft.util.EnumWorldBlockLayer p_178001_1_)
  {
    if (field_178007_b)
    {
      Iterator var2 = field_178009_a.iterator();
      
      while (var2.hasNext())
      {
        RenderChunk var3 = (RenderChunk)var2.next();
        net.minecraft.client.renderer.vertex.VertexBuffer var4 = var3.func_178565_b(p_178001_1_.ordinal());
        GlStateManager.pushMatrix();
        func_178003_a(var3);
        var3.func_178572_f();
        var4.func_177359_a();
        func_178010_a();
        var4.func_177358_a(7);
        GlStateManager.popMatrix();
      }
      
      OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, 0);
      GlStateManager.func_179117_G();
      field_178009_a.clear();
    }
  }
  
  private void func_178010_a()
  {
    GL11.glVertexPointer(3, 5126, 28, 0L);
    GL11.glColorPointer(4, 5121, 28, 12L);
    GL11.glTexCoordPointer(2, 5126, 28, 16L);
    OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
    GL11.glTexCoordPointer(2, 5122, 28, 24L);
    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
  }
}
