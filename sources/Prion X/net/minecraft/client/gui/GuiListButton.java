package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class GuiListButton extends GuiButton
{
  private boolean field_175216_o;
  private String field_175215_p;
  private final GuiPageButtonList.GuiResponder field_175214_q;
  private static final String __OBFID = "CL_00001953";
  
  public GuiListButton(GuiPageButtonList.GuiResponder p_i45539_1_, int p_i45539_2_, int p_i45539_3_, int p_i45539_4_, String p_i45539_5_, boolean p_i45539_6_)
  {
    super(p_i45539_2_, p_i45539_3_, p_i45539_4_, 150, 20, "");
    field_175215_p = p_i45539_5_;
    field_175216_o = p_i45539_6_;
    displayString = func_175213_c();
    field_175214_q = p_i45539_1_;
  }
  
  private String func_175213_c()
  {
    return I18n.format(field_175215_p, new Object[0]) + ": " + (field_175216_o ? I18n.format("gui.yes", new Object[0]) : I18n.format("gui.no", new Object[0]));
  }
  
  public void func_175212_b(boolean p_175212_1_)
  {
    field_175216_o = p_175212_1_;
    displayString = func_175213_c();
    field_175214_q.func_175321_a(id, p_175212_1_);
  }
  




  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
  {
    if (super.mousePressed(mc, mouseX, mouseY))
    {
      field_175216_o = (!field_175216_o);
      displayString = func_175213_c();
      field_175214_q.func_175321_a(id, field_175216_o);
      return true;
    }
    

    return false;
  }
}
