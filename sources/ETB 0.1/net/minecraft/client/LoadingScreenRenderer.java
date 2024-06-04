package net.minecraft.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.MinecraftError;

public class LoadingScreenRenderer implements net.minecraft.util.IProgressUpdate
{
  private String field_73727_a = "";
  


  private Minecraft mc;
  


  private String currentlyDisplayedText = "";
  private long field_73723_d = Minecraft.getSystemTime();
  private boolean field_73724_e;
  private ScaledResolution field_146587_f;
  private Framebuffer field_146588_g;
  private static final String __OBFID = "CL_00000655";
  
  public LoadingScreenRenderer(Minecraft mcIn)
  {
    mc = mcIn;
    field_146587_f = new ScaledResolution(mcIn);
    field_146588_g = new Framebuffer(displayWidth, displayHeight, false);
    field_146588_g.setFramebufferFilter(9728);
  }
  




  public void resetProgressAndMessage(String p_73721_1_)
  {
    field_73724_e = false;
    func_73722_d(p_73721_1_);
  }
  



  public void displaySavingString(String message)
  {
    field_73724_e = true;
    func_73722_d(message);
  }
  
  private void func_73722_d(String p_73722_1_)
  {
    currentlyDisplayedText = p_73722_1_;
    
    if (!mc.running)
    {
      if (!field_73724_e)
      {
        throw new MinecraftError();
      }
    }
    else
    {
      GlStateManager.clear(256);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      
      if (OpenGlHelper.isFramebufferEnabled())
      {
        int var2 = field_146587_f.getScaleFactor();
        GlStateManager.ortho(0.0D, field_146587_f.getScaledWidth() * var2, field_146587_f.getScaledHeight() * var2, 0.0D, 100.0D, 300.0D);
      }
      else
      {
        ScaledResolution var3 = new ScaledResolution(mc);
        GlStateManager.ortho(0.0D, var3.getScaledWidth_double(), var3.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
      }
      
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -200.0F);
    }
  }
  



  public void displayLoadingString(String message)
  {
    if (!mc.running)
    {
      if (!field_73724_e)
      {
        throw new MinecraftError();
      }
    }
    else
    {
      field_73723_d = 0L;
      field_73727_a = message;
      setLoadingProgress(-1);
      field_73723_d = 0L;
    }
  }
  



  public void setLoadingProgress(int progress)
  {
    if (!mc.running)
    {
      if (!field_73724_e)
      {
        throw new MinecraftError();
      }
    }
    else
    {
      long var2 = Minecraft.getSystemTime();
      
      if (var2 - field_73723_d >= 100L)
      {
        field_73723_d = var2;
        ScaledResolution var4 = new ScaledResolution(mc);
        int var5 = var4.getScaleFactor();
        int var6 = var4.getScaledWidth();
        int var7 = var4.getScaledHeight();
        
        if (OpenGlHelper.isFramebufferEnabled())
        {
          field_146588_g.framebufferClear();
        }
        else
        {
          GlStateManager.clear(256);
        }
        
        field_146588_g.bindFramebuffer(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, var4.getScaledWidth_double(), var4.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -200.0F);
        
        if (!OpenGlHelper.isFramebufferEnabled())
        {
          GlStateManager.clear(16640);
        }
        
        Tessellator var8 = Tessellator.getInstance();
        WorldRenderer var9 = var8.getWorldRenderer();
        mc.getTextureManager().bindTexture(Gui.optionsBackground);
        float var10 = 32.0F;
        var9.startDrawingQuads();
        var9.func_178991_c(4210752);
        var9.addVertexWithUV(0.0D, var7, 0.0D, 0.0D, var7 / var10);
        var9.addVertexWithUV(var6, var7, 0.0D, var6 / var10, var7 / var10);
        var9.addVertexWithUV(var6, 0.0D, 0.0D, var6 / var10, 0.0D);
        var9.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        var8.draw();
        
        if (progress >= 0)
        {
          byte var11 = 100;
          byte var12 = 2;
          int var13 = var6 / 2 - var11 / 2;
          int var14 = var7 / 2 + 16;
          GlStateManager.func_179090_x();
          var9.startDrawingQuads();
          var9.func_178991_c(8421504);
          var9.addVertex(var13, var14, 0.0D);
          var9.addVertex(var13, var14 + var12, 0.0D);
          var9.addVertex(var13 + var11, var14 + var12, 0.0D);
          var9.addVertex(var13 + var11, var14, 0.0D);
          var9.func_178991_c(8454016);
          var9.addVertex(var13, var14, 0.0D);
          var9.addVertex(var13, var14 + var12, 0.0D);
          var9.addVertex(var13 + progress, var14 + var12, 0.0D);
          var9.addVertex(var13 + progress, var14, 0.0D);
          var8.draw();
          GlStateManager.func_179098_w();
        }
        
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        mc.fontRendererObj.drawStringWithShadow(currentlyDisplayedText, (var6 - mc.fontRendererObj.getStringWidth(currentlyDisplayedText)) / 2, var7 / 2 - 4 - 16, 16777215);
        mc.fontRendererObj.drawStringWithShadow(field_73727_a, (var6 - mc.fontRendererObj.getStringWidth(field_73727_a)) / 2, var7 / 2 - 4 + 8, 16777215);
        field_146588_g.unbindFramebuffer();
        
        if (OpenGlHelper.isFramebufferEnabled())
        {
          field_146588_g.framebufferRender(var6 * var5, var7 * var5);
        }
        
        mc.func_175601_h();
        
        try
        {
          Thread.yield();
        }
        catch (Exception localException) {}
      }
    }
  }
  
  public void setDoneWorking() {}
}
