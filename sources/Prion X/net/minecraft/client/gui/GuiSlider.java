package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiSlider extends GuiButton
{
  private float field_175227_p = 1.0F;
  public boolean field_175228_o;
  private String field_175226_q;
  private final float field_175225_r;
  private final float field_175224_s;
  private final GuiPageButtonList.GuiResponder field_175223_t;
  private FormatHelper field_175222_u;
  private static final String __OBFID = "CL_00001954";
  
  public GuiSlider(GuiPageButtonList.GuiResponder p_i45541_1_, int p_i45541_2_, int p_i45541_3_, int p_i45541_4_, String p_i45541_5_, float p_i45541_6_, float p_i45541_7_, float p_i45541_8_, FormatHelper p_i45541_9_)
  {
    super(p_i45541_2_, p_i45541_3_, p_i45541_4_, 150, 20, "");
    field_175226_q = p_i45541_5_;
    field_175225_r = p_i45541_6_;
    field_175224_s = p_i45541_7_;
    field_175227_p = ((p_i45541_8_ - p_i45541_6_) / (p_i45541_7_ - p_i45541_6_));
    field_175222_u = p_i45541_9_;
    field_175223_t = p_i45541_1_;
    displayString = func_175221_e();
  }
  
  public float func_175220_c()
  {
    return field_175225_r + (field_175224_s - field_175225_r) * field_175227_p;
  }
  
  public void func_175218_a(float p_175218_1_, boolean p_175218_2_)
  {
    field_175227_p = ((p_175218_1_ - field_175225_r) / (field_175224_s - field_175225_r));
    displayString = func_175221_e();
    
    if (p_175218_2_)
    {
      field_175223_t.func_175320_a(id, func_175220_c());
    }
  }
  
  public float func_175217_d()
  {
    return field_175227_p;
  }
  
  private String func_175221_e()
  {
    return field_175222_u == null ? I18n.format(field_175226_q, new Object[0]) + ": " + func_175220_c() : field_175222_u.func_175318_a(id, I18n.format(field_175226_q, new Object[0]), func_175220_c());
  }
  




  protected int getHoverState(boolean mouseOver)
  {
    return 0;
  }
  



  protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
  {
    if (visible)
    {
      if (field_175228_o)
      {
        field_175227_p = ((mouseX - (xPosition + 4)) / (width - 8));
        
        if (field_175227_p < 0.0F)
        {
          field_175227_p = 0.0F;
        }
        
        if (field_175227_p > 1.0F)
        {
          field_175227_p = 1.0F;
        }
        
        displayString = func_175221_e();
        field_175223_t.func_175320_a(id, func_175220_c());
      }
      
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      drawTexturedModalRect(xPosition + (int)(field_175227_p * (width - 8)), yPosition, 0, 66, 4, 20);
      drawTexturedModalRect(xPosition + (int)(field_175227_p * (width - 8)) + 4, yPosition, 196, 66, 4, 20);
    }
  }
  
  public void func_175219_a(float p_175219_1_)
  {
    field_175227_p = p_175219_1_;
    displayString = func_175221_e();
    field_175223_t.func_175320_a(id, func_175220_c());
  }
  




  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
  {
    if (super.mousePressed(mc, mouseX, mouseY))
    {
      field_175227_p = ((mouseX - (xPosition + 4)) / (width - 8));
      
      if (field_175227_p < 0.0F)
      {
        field_175227_p = 0.0F;
      }
      
      if (field_175227_p > 1.0F)
      {
        field_175227_p = 1.0F;
      }
      
      displayString = func_175221_e();
      field_175223_t.func_175320_a(id, func_175220_c());
      field_175228_o = true;
      return true;
    }
    

    return false;
  }
  




  public void mouseReleased(int mouseX, int mouseY)
  {
    field_175228_o = false;
  }
  
  public static abstract interface FormatHelper
  {
    public abstract String func_175318_a(int paramInt, String paramString, float paramFloat);
  }
}
