package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;










public abstract class GuiSlot
{
  protected final Minecraft mc;
  protected int width;
  protected int height;
  protected int top;
  protected int bottom;
  protected int right;
  protected int left;
  protected final int slotHeight;
  private int scrollUpButtonID;
  private int scrollDownButtonID;
  protected int mouseX;
  protected int mouseY;
  protected boolean field_148163_i = true;
  

  protected float initialClickY = -2.0F;
  


  protected float scrollMultiplier;
  


  protected float amountScrolled;
  


  protected int selectedElement = -1;
  
  protected long lastClicked;
  
  protected boolean field_178041_q = true;
  



  protected boolean showSelectionBox = true;
  protected boolean hasListHeader;
  protected int headerPadding;
  private boolean enabled = true;
  private static final String __OBFID = "CL_00000679";
  
  public GuiSlot(Minecraft mcIn, int width, int height, int p_i1052_4_, int p_i1052_5_, int p_i1052_6_)
  {
    mc = mcIn;
    this.width = width;
    this.height = height;
    top = p_i1052_4_;
    bottom = p_i1052_5_;
    slotHeight = p_i1052_6_;
    left = 0;
    right = width;
  }
  
  public void setDimensions(int p_148122_1_, int p_148122_2_, int p_148122_3_, int p_148122_4_)
  {
    width = p_148122_1_;
    height = p_148122_2_;
    top = p_148122_3_;
    bottom = p_148122_4_;
    left = 0;
    right = p_148122_1_;
  }
  
  public void setShowSelectionBox(boolean p_148130_1_)
  {
    showSelectionBox = p_148130_1_;
  }
  




  protected void setHasListHeader(boolean p_148133_1_, int p_148133_2_)
  {
    hasListHeader = p_148133_1_;
    headerPadding = p_148133_2_;
    
    if (!p_148133_1_)
    {
      headerPadding = 0;
    }
  }
  



  protected abstract int getSize();
  


  protected abstract void elementClicked(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
  


  protected abstract boolean isSelected(int paramInt);
  


  protected int getContentHeight()
  {
    return getSize() * slotHeight + headerPadding;
  }
  

  protected abstract void drawBackground();
  

  protected void func_178040_a(int p_178040_1_, int p_178040_2_, int p_178040_3_) {}
  

  protected abstract void drawSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {}
  
  protected void func_148132_a(int p_148132_1_, int p_148132_2_) {}
  
  protected void func_148142_b(int p_148142_1_, int p_148142_2_) {}
  
  public int getSlotIndexFromScreenCoords(int p_148124_1_, int p_148124_2_)
  {
    int var3 = left + width / 2 - getListWidth() / 2;
    int var4 = left + width / 2 + getListWidth() / 2;
    int var5 = p_148124_2_ - top - headerPadding + (int)amountScrolled - 4;
    int var6 = var5 / slotHeight;
    return (p_148124_1_ < getScrollBarX()) && (p_148124_1_ >= var3) && (p_148124_1_ <= var4) && (var6 >= 0) && (var5 >= 0) && (var6 < getSize()) ? var6 : -1;
  }
  



  public void registerScrollButtons(int p_148134_1_, int p_148134_2_)
  {
    scrollUpButtonID = p_148134_1_;
    scrollDownButtonID = p_148134_2_;
  }
  



  protected void bindAmountScrolled()
  {
    int var1 = func_148135_f();
    
    if (var1 < 0)
    {
      var1 /= 2;
    }
    
    if ((!field_148163_i) && (var1 < 0))
    {
      var1 = 0;
    }
    
    amountScrolled = MathHelper.clamp_float(amountScrolled, 0.0F, var1);
  }
  
  public int func_148135_f()
  {
    return Math.max(0, getContentHeight() - (bottom - top - 4));
  }
  



  public int getAmountScrolled()
  {
    return (int)amountScrolled;
  }
  
  public boolean isMouseYWithinSlotBounds(int p_148141_1_)
  {
    return (p_148141_1_ >= top) && (p_148141_1_ <= bottom) && (mouseX >= left) && (mouseX <= right);
  }
  



  public void scrollBy(int p_148145_1_)
  {
    amountScrolled += p_148145_1_;
    bindAmountScrolled();
    initialClickY = -2.0F;
  }
  
  public void actionPerformed(GuiButton p_148147_1_)
  {
    if (enabled)
    {
      if (id == scrollUpButtonID)
      {
        amountScrolled -= slotHeight * 2 / 3;
        initialClickY = -2.0F;
        bindAmountScrolled();
      }
      else if (id == scrollDownButtonID)
      {
        amountScrolled += slotHeight * 2 / 3;
        initialClickY = -2.0F;
        bindAmountScrolled();
      }
    }
  }
  
  public void drawScreen(int p_148128_1_, int p_148128_2_, float p_148128_3_)
  {
    if (field_178041_q)
    {
      mouseX = p_148128_1_;
      mouseY = p_148128_2_;
      drawBackground();
      int var4 = getScrollBarX();
      int var5 = var4 + 6;
      bindAmountScrolled();
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      mc.getTextureManager().bindTexture(Gui.optionsBackground);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      float var8 = 32.0F;
      var7.startDrawingQuads();
      var7.func_178991_c(2105376);
      var7.addVertexWithUV(left, bottom, 0.0D, left / var8, (bottom + (int)amountScrolled) / var8);
      var7.addVertexWithUV(right, bottom, 0.0D, right / var8, (bottom + (int)amountScrolled) / var8);
      var7.addVertexWithUV(right, top, 0.0D, right / var8, (top + (int)amountScrolled) / var8);
      var7.addVertexWithUV(left, top, 0.0D, left / var8, (top + (int)amountScrolled) / var8);
      var6.draw();
      int var9 = left + width / 2 - getListWidth() / 2 + 2;
      int var10 = top + 4 - (int)amountScrolled;
      
      if (hasListHeader)
      {
        drawListHeader(var9, var10, var6);
      }
      
      drawSelectionBox(var9, var10, p_148128_1_, p_148128_2_);
      GlStateManager.disableDepth();
      byte var11 = 4;
      overlayBackground(0, top, 255, 255);
      overlayBackground(bottom, height, 255, 255);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
      GlStateManager.disableAlpha();
      GlStateManager.shadeModel(7425);
      GlStateManager.func_179090_x();
      var7.startDrawingQuads();
      var7.func_178974_a(0, 0);
      var7.addVertexWithUV(left, top + var11, 0.0D, 0.0D, 1.0D);
      var7.addVertexWithUV(right, top + var11, 0.0D, 1.0D, 1.0D);
      var7.func_178974_a(0, 255);
      var7.addVertexWithUV(right, top, 0.0D, 1.0D, 0.0D);
      var7.addVertexWithUV(left, top, 0.0D, 0.0D, 0.0D);
      var6.draw();
      var7.startDrawingQuads();
      var7.func_178974_a(0, 255);
      var7.addVertexWithUV(left, bottom, 0.0D, 0.0D, 1.0D);
      var7.addVertexWithUV(right, bottom, 0.0D, 1.0D, 1.0D);
      var7.func_178974_a(0, 0);
      var7.addVertexWithUV(right, bottom - var11, 0.0D, 1.0D, 0.0D);
      var7.addVertexWithUV(left, bottom - var11, 0.0D, 0.0D, 0.0D);
      var6.draw();
      int var12 = func_148135_f();
      
      if (var12 > 0)
      {
        int var13 = (bottom - top) * (bottom - top) / getContentHeight();
        var13 = MathHelper.clamp_int(var13, 32, bottom - top - 8);
        int var14 = (int)amountScrolled * (bottom - top - var13) / var12 + top;
        
        if (var14 < top)
        {
          var14 = top;
        }
        
        var7.startDrawingQuads();
        var7.func_178974_a(0, 255);
        var7.addVertexWithUV(var4, bottom, 0.0D, 0.0D, 1.0D);
        var7.addVertexWithUV(var5, bottom, 0.0D, 1.0D, 1.0D);
        var7.addVertexWithUV(var5, top, 0.0D, 1.0D, 0.0D);
        var7.addVertexWithUV(var4, top, 0.0D, 0.0D, 0.0D);
        var6.draw();
        var7.startDrawingQuads();
        var7.func_178974_a(8421504, 255);
        var7.addVertexWithUV(var4, var14 + var13, 0.0D, 0.0D, 1.0D);
        var7.addVertexWithUV(var5, var14 + var13, 0.0D, 1.0D, 1.0D);
        var7.addVertexWithUV(var5, var14, 0.0D, 1.0D, 0.0D);
        var7.addVertexWithUV(var4, var14, 0.0D, 0.0D, 0.0D);
        var6.draw();
        var7.startDrawingQuads();
        var7.func_178974_a(12632256, 255);
        var7.addVertexWithUV(var4, var14 + var13 - 1, 0.0D, 0.0D, 1.0D);
        var7.addVertexWithUV(var5 - 1, var14 + var13 - 1, 0.0D, 1.0D, 1.0D);
        var7.addVertexWithUV(var5 - 1, var14, 0.0D, 1.0D, 0.0D);
        var7.addVertexWithUV(var4, var14, 0.0D, 0.0D, 0.0D);
        var6.draw();
      }
      
      func_148142_b(p_148128_1_, p_148128_2_);
      GlStateManager.func_179098_w();
      GlStateManager.shadeModel(7424);
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
    }
  }
  
  public void func_178039_p()
  {
    if (isMouseYWithinSlotBounds(mouseY))
    {
      if ((Mouse.isButtonDown(0)) && (getEnabled()))
      {
        if (initialClickY == -1.0F)
        {
          boolean var1 = true;
          
          if ((mouseY >= top) && (mouseY <= bottom))
          {
            int var2 = width / 2 - getListWidth() / 2;
            int var3 = width / 2 + getListWidth() / 2;
            int var4 = mouseY - top - headerPadding + (int)amountScrolled - 4;
            int var5 = var4 / slotHeight;
            
            if ((mouseX >= var2) && (mouseX <= var3) && (var5 >= 0) && (var4 >= 0) && (var5 < getSize()))
            {
              boolean var6 = (var5 == selectedElement) && (Minecraft.getSystemTime() - lastClicked < 250L);
              elementClicked(var5, var6, mouseX, mouseY);
              selectedElement = var5;
              lastClicked = Minecraft.getSystemTime();
            }
            else if ((mouseX >= var2) && (mouseX <= var3) && (var4 < 0))
            {
              func_148132_a(mouseX - var2, mouseY - top + (int)amountScrolled - 4);
              var1 = false;
            }
            
            int var11 = getScrollBarX();
            int var7 = var11 + 6;
            
            if ((mouseX >= var11) && (mouseX <= var7))
            {
              scrollMultiplier = -1.0F;
              int var8 = func_148135_f();
              
              if (var8 < 1)
              {
                var8 = 1;
              }
              
              int var9 = (int)((bottom - top) * (bottom - top) / getContentHeight());
              var9 = MathHelper.clamp_int(var9, 32, bottom - top - 8);
              scrollMultiplier /= (bottom - top - var9) / var8;
            }
            else
            {
              scrollMultiplier = 1.0F;
            }
            
            if (var1)
            {
              initialClickY = mouseY;
            }
            else
            {
              initialClickY = -2.0F;
            }
          }
          else
          {
            initialClickY = -2.0F;
          }
        }
        else if (initialClickY >= 0.0F)
        {
          amountScrolled -= (mouseY - initialClickY) * scrollMultiplier;
          initialClickY = mouseY;
        }
        
      }
      else {
        initialClickY = -1.0F;
      }
      
      int var10 = Mouse.getEventDWheel();
      
      if (var10 != 0)
      {
        if (var10 > 0)
        {
          var10 = -1;
        }
        else if (var10 < 0)
        {
          var10 = 1;
        }
        
        amountScrolled += var10 * slotHeight / 2;
      }
    }
  }
  
  public void setEnabled(boolean p_148143_1_)
  {
    enabled = p_148143_1_;
  }
  
  public boolean getEnabled()
  {
    return enabled;
  }
  



  public int getListWidth()
  {
    return 220;
  }
  



  protected void drawSelectionBox(int p_148120_1_, int p_148120_2_, int p_148120_3_, int p_148120_4_)
  {
    int var5 = getSize();
    Tessellator var6 = Tessellator.getInstance();
    WorldRenderer var7 = var6.getWorldRenderer();
    
    for (int var8 = 0; var8 < var5; var8++)
    {
      int var9 = p_148120_2_ + var8 * slotHeight + headerPadding;
      int var10 = slotHeight - 4;
      
      if ((var9 > bottom) || (var9 + var10 < top))
      {
        func_178040_a(var8, p_148120_1_, var9);
      }
      
      if ((showSelectionBox) && (isSelected(var8)))
      {
        int var11 = left + (width / 2 - getListWidth() / 2);
        int var12 = left + width / 2 + getListWidth() / 2;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.func_179090_x();
        var7.startDrawingQuads();
        var7.func_178991_c(8421504);
        var7.addVertexWithUV(var11, var9 + var10 + 2, 0.0D, 0.0D, 1.0D);
        var7.addVertexWithUV(var12, var9 + var10 + 2, 0.0D, 1.0D, 1.0D);
        var7.addVertexWithUV(var12, var9 - 2, 0.0D, 1.0D, 0.0D);
        var7.addVertexWithUV(var11, var9 - 2, 0.0D, 0.0D, 0.0D);
        var7.func_178991_c(0);
        var7.addVertexWithUV(var11 + 1, var9 + var10 + 1, 0.0D, 0.0D, 1.0D);
        var7.addVertexWithUV(var12 - 1, var9 + var10 + 1, 0.0D, 1.0D, 1.0D);
        var7.addVertexWithUV(var12 - 1, var9 - 1, 0.0D, 1.0D, 0.0D);
        var7.addVertexWithUV(var11 + 1, var9 - 1, 0.0D, 0.0D, 0.0D);
        var6.draw();
        GlStateManager.func_179098_w();
      }
      
      drawSlot(var8, p_148120_1_, var9, var10, p_148120_3_, p_148120_4_);
    }
  }
  
  protected int getScrollBarX()
  {
    return width / 2 + 124;
  }
  



  protected void overlayBackground(int p_148136_1_, int p_148136_2_, int p_148136_3_, int p_148136_4_)
  {
    Tessellator var5 = Tessellator.getInstance();
    WorldRenderer var6 = var5.getWorldRenderer();
    mc.getTextureManager().bindTexture(Gui.optionsBackground);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    float var7 = 32.0F;
    var6.startDrawingQuads();
    var6.func_178974_a(4210752, p_148136_4_);
    var6.addVertexWithUV(left, p_148136_2_, 0.0D, 0.0D, p_148136_2_ / var7);
    var6.addVertexWithUV(left + width, p_148136_2_, 0.0D, width / var7, p_148136_2_ / var7);
    var6.func_178974_a(4210752, p_148136_3_);
    var6.addVertexWithUV(left + width, p_148136_1_, 0.0D, width / var7, p_148136_1_ / var7);
    var6.addVertexWithUV(left, p_148136_1_, 0.0D, 0.0D, p_148136_1_ / var7);
    var5.draw();
  }
  



  public void setSlotXBoundsFromLeft(int p_148140_1_)
  {
    left = p_148140_1_;
    right = (p_148140_1_ + width);
  }
  
  public int getSlotHeight()
  {
    return slotHeight;
  }
}
