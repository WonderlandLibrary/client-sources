package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiSlider
  extends GuiButton
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
    this.field_175226_q = p_i45541_5_;
    this.field_175225_r = p_i45541_6_;
    this.field_175224_s = p_i45541_7_;
    this.field_175227_p = ((p_i45541_8_ - p_i45541_6_) / (p_i45541_7_ - p_i45541_6_));
    this.field_175222_u = p_i45541_9_;
    this.field_175223_t = p_i45541_1_;
    this.displayString = func_175221_e();
  }
  
  public float func_175220_c()
  {
    return this.field_175225_r + (this.field_175224_s - this.field_175225_r) * this.field_175227_p;
  }
  
  public void func_175218_a(float p_175218_1_, boolean p_175218_2_)
  {
    this.field_175227_p = ((p_175218_1_ - this.field_175225_r) / (this.field_175224_s - this.field_175225_r));
    this.displayString = func_175221_e();
    if (p_175218_2_) {
      this.field_175223_t.func_175320_a(this.id, func_175220_c());
    }
  }
  
  public float func_175217_d()
  {
    return this.field_175227_p;
  }
  
  private String func_175221_e()
  {
    return this.field_175222_u == null ? I18n.format(this.field_175226_q, new Object[0]) + ": " + func_175220_c() : this.field_175222_u.func_175318_a(this.id, I18n.format(this.field_175226_q, new Object[0]), func_175220_c());
  }
  
  protected int getHoverState(boolean mouseOver)
  {
    return 0;
  }
  
  protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
  {
    if (this.visible)
    {
      if (this.field_175228_o)
      {
        this.field_175227_p = ((mouseX - (this.xPosition + 4)) / (this.width - 8));
        if (this.field_175227_p < 0.0F) {
          this.field_175227_p = 0.0F;
        }
        if (this.field_175227_p > 1.0F) {
          this.field_175227_p = 1.0F;
        }
        this.displayString = func_175221_e();
        this.field_175223_t.func_175320_a(this.id, func_175220_c());
      }
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      drawTexturedModalRect(this.xPosition + (int)(this.field_175227_p * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
      drawTexturedModalRect(this.xPosition + (int)(this.field_175227_p * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
    }
  }
  
  public void func_175219_a(float p_175219_1_)
  {
    this.field_175227_p = p_175219_1_;
    this.displayString = func_175221_e();
    this.field_175223_t.func_175320_a(this.id, func_175220_c());
  }
  
  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
  {
    if (super.mousePressed(mc, mouseX, mouseY))
    {
      this.field_175227_p = ((mouseX - (this.xPosition + 4)) / (this.width - 8));
      if (this.field_175227_p < 0.0F) {
        this.field_175227_p = 0.0F;
      }
      if (this.field_175227_p > 1.0F) {
        this.field_175227_p = 1.0F;
      }
      this.displayString = func_175221_e();
      this.field_175223_t.func_175320_a(this.id, func_175220_c());
      this.field_175228_o = true;
      return true;
    }
    return false;
  }
  
  public void mouseReleased(int mouseX, int mouseY)
  {
    this.field_175228_o = false;
  }
  
  public static abstract interface FormatHelper
  {
    public abstract String func_175318_a(int paramInt, String paramString, float paramFloat);
  }
}
