package net.minecraft.client.shader;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;


public class Framebuffer
{
  public int framebufferTextureWidth;
  public int framebufferTextureHeight;
  public int framebufferWidth;
  public int framebufferHeight;
  public boolean useDepth;
  public int framebufferObject;
  public int framebufferTexture;
  public int depthBuffer;
  public float[] framebufferColor;
  public int framebufferFilter;
  private static final String __OBFID = "CL_00000959";
  
  public Framebuffer(int p_i45078_1_, int p_i45078_2_, boolean p_i45078_3_)
  {
    useDepth = p_i45078_3_;
    framebufferObject = -1;
    framebufferTexture = -1;
    depthBuffer = -1;
    framebufferColor = new float[4];
    framebufferColor[0] = 1.0F;
    framebufferColor[1] = 1.0F;
    framebufferColor[2] = 1.0F;
    framebufferColor[3] = 0.0F;
    createBindFramebuffer(p_i45078_1_, p_i45078_2_);
  }
  
  public void createBindFramebuffer(int p_147613_1_, int p_147613_2_)
  {
    if (!OpenGlHelper.isFramebufferEnabled())
    {
      framebufferWidth = p_147613_1_;
      framebufferHeight = p_147613_2_;
    }
    else
    {
      GlStateManager.enableDepth();
      
      if (framebufferObject >= 0)
      {
        deleteFramebuffer();
      }
      
      createFramebuffer(p_147613_1_, p_147613_2_);
      checkFramebufferComplete();
      OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
    }
  }
  
  public void deleteFramebuffer()
  {
    if (OpenGlHelper.isFramebufferEnabled())
    {
      unbindFramebufferTexture();
      unbindFramebuffer();
      
      if (depthBuffer > -1)
      {
        OpenGlHelper.func_153184_g(depthBuffer);
        depthBuffer = -1;
      }
      
      if (framebufferTexture > -1)
      {
        TextureUtil.deleteTexture(framebufferTexture);
        framebufferTexture = -1;
      }
      
      if (framebufferObject > -1)
      {
        OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
        OpenGlHelper.func_153174_h(framebufferObject);
        framebufferObject = -1;
      }
    }
  }
  
  public void createFramebuffer(int p_147605_1_, int p_147605_2_)
  {
    framebufferWidth = p_147605_1_;
    framebufferHeight = p_147605_2_;
    framebufferTextureWidth = p_147605_1_;
    framebufferTextureHeight = p_147605_2_;
    
    if (!OpenGlHelper.isFramebufferEnabled())
    {
      framebufferClear();
    }
    else
    {
      framebufferObject = OpenGlHelper.func_153165_e();
      framebufferTexture = TextureUtil.glGenTextures();
      
      if (useDepth)
      {
        depthBuffer = OpenGlHelper.func_153185_f();
      }
      
      setFramebufferFilter(9728);
      GlStateManager.func_179144_i(framebufferTexture);
      GL11.glTexImage2D(3553, 0, 32856, framebufferTextureWidth, framebufferTextureHeight, 0, 6408, 5121, null);
      OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, framebufferObject);
      OpenGlHelper.func_153188_a(OpenGlHelper.field_153198_e, OpenGlHelper.field_153200_g, 3553, framebufferTexture, 0);
      
      if (useDepth)
      {
        OpenGlHelper.func_153176_h(OpenGlHelper.field_153199_f, depthBuffer);
        OpenGlHelper.func_153186_a(OpenGlHelper.field_153199_f, 33190, framebufferTextureWidth, framebufferTextureHeight);
        OpenGlHelper.func_153190_b(OpenGlHelper.field_153198_e, OpenGlHelper.field_153201_h, OpenGlHelper.field_153199_f, depthBuffer);
      }
      
      framebufferClear();
      unbindFramebufferTexture();
    }
  }
  
  public void setFramebufferFilter(int p_147607_1_)
  {
    if (OpenGlHelper.isFramebufferEnabled())
    {
      framebufferFilter = p_147607_1_;
      GlStateManager.func_179144_i(framebufferTexture);
      GL11.glTexParameterf(3553, 10241, p_147607_1_);
      GL11.glTexParameterf(3553, 10240, p_147607_1_);
      GL11.glTexParameterf(3553, 10242, 10496.0F);
      GL11.glTexParameterf(3553, 10243, 10496.0F);
      GlStateManager.func_179144_i(0);
    }
  }
  
  public void checkFramebufferComplete()
  {
    int var1 = OpenGlHelper.func_153167_i(OpenGlHelper.field_153198_e);
    
    if (var1 != OpenGlHelper.field_153202_i)
    {
      if (var1 == OpenGlHelper.field_153203_j)
      {
        throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
      }
      if (var1 == OpenGlHelper.field_153204_k)
      {
        throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
      }
      if (var1 == OpenGlHelper.field_153205_l)
      {
        throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
      }
      if (var1 == OpenGlHelper.field_153206_m)
      {
        throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
      }
      

      throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + var1);
    }
  }
  

  public void bindFramebufferTexture()
  {
    if (OpenGlHelper.isFramebufferEnabled())
    {
      GlStateManager.func_179144_i(framebufferTexture);
    }
  }
  
  public void unbindFramebufferTexture()
  {
    if (OpenGlHelper.isFramebufferEnabled())
    {
      GlStateManager.func_179144_i(0);
    }
  }
  
  public void bindFramebuffer(boolean p_147610_1_)
  {
    if (OpenGlHelper.isFramebufferEnabled())
    {
      OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, framebufferObject);
      
      if (p_147610_1_)
      {
        GlStateManager.viewport(0, 0, framebufferWidth, framebufferHeight);
      }
    }
  }
  
  public void unbindFramebuffer()
  {
    if (OpenGlHelper.isFramebufferEnabled())
    {
      OpenGlHelper.func_153171_g(OpenGlHelper.field_153198_e, 0);
    }
  }
  
  public void setFramebufferColor(float p_147604_1_, float p_147604_2_, float p_147604_3_, float p_147604_4_)
  {
    framebufferColor[0] = p_147604_1_;
    framebufferColor[1] = p_147604_2_;
    framebufferColor[2] = p_147604_3_;
    framebufferColor[3] = p_147604_4_;
  }
  
  public void framebufferRender(int p_147615_1_, int p_147615_2_)
  {
    func_178038_a(p_147615_1_, p_147615_2_, true);
  }
  
  public void func_178038_a(int p_178038_1_, int p_178038_2_, boolean p_178038_3_)
  {
    if (OpenGlHelper.isFramebufferEnabled())
    {
      GlStateManager.colorMask(true, true, true, false);
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0D, p_178038_1_, p_178038_2_, 0.0D, 1000.0D, 3000.0D);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
      GlStateManager.viewport(0, 0, p_178038_1_, p_178038_2_);
      GlStateManager.func_179098_w();
      GlStateManager.disableLighting();
      GlStateManager.disableAlpha();
      
      if (p_178038_3_)
      {
        GlStateManager.disableBlend();
        GlStateManager.enableColorMaterial();
      }
      
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      bindFramebufferTexture();
      float var4 = p_178038_1_;
      float var5 = p_178038_2_;
      float var6 = framebufferWidth / framebufferTextureWidth;
      float var7 = framebufferHeight / framebufferTextureHeight;
      Tessellator var8 = Tessellator.getInstance();
      WorldRenderer var9 = var8.getWorldRenderer();
      var9.startDrawingQuads();
      var9.func_178991_c(-1);
      var9.addVertexWithUV(0.0D, var5, 0.0D, 0.0D, 0.0D);
      var9.addVertexWithUV(var4, var5, 0.0D, var6, 0.0D);
      var9.addVertexWithUV(var4, 0.0D, 0.0D, var6, var7);
      var9.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, var7);
      var8.draw();
      unbindFramebufferTexture();
      GlStateManager.depthMask(true);
      GlStateManager.colorMask(true, true, true, true);
    }
  }
  
  public void framebufferClear()
  {
    bindFramebuffer(true);
    GlStateManager.clearColor(framebufferColor[0], framebufferColor[1], framebufferColor[2], framebufferColor[3]);
    int var1 = 16384;
    
    if (useDepth)
    {
      GlStateManager.clearDepth(1.0D);
      var1 |= 0x100;
    }
    
    GlStateManager.clear(var1);
    unbindFramebuffer();
  }
}
