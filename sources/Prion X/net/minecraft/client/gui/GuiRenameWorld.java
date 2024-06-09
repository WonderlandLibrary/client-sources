package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;

public class GuiRenameWorld extends GuiScreen
{
  private GuiScreen field_146585_a;
  private GuiTextField field_146583_f;
  private final String field_146584_g;
  private static final String __OBFID = "CL_00000709";
  
  public GuiRenameWorld(GuiScreen p_i46317_1_, String p_i46317_2_)
  {
    field_146585_a = p_i46317_1_;
    field_146584_g = p_i46317_2_;
  }
  



  public void updateScreen()
  {
    field_146583_f.updateCursorCounter();
  }
  



  public void initGui()
  {
    org.lwjgl.input.Keyboard.enableRepeatEvents(true);
    buttonList.clear();
    buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, I18n.format("selectWorld.renameButton", new Object[0])));
    buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
    ISaveFormat var1 = mc.getSaveLoader();
    net.minecraft.world.storage.WorldInfo var2 = var1.getWorldInfo(field_146584_g);
    String var3 = var2.getWorldName();
    field_146583_f = new GuiTextField(2, fontRendererObj, width / 2 - 100, 60, 200, 20);
    field_146583_f.setFocused(true);
    field_146583_f.setText(var3);
  }
  



  public void onGuiClosed()
  {
    org.lwjgl.input.Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (enabled)
    {
      if (id == 1)
      {
        mc.displayGuiScreen(field_146585_a);
      }
      else if (id == 0)
      {
        ISaveFormat var2 = mc.getSaveLoader();
        var2.renameWorld(field_146584_g, field_146583_f.getText().trim());
        mc.displayGuiScreen(field_146585_a);
      }
    }
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    field_146583_f.textboxKeyTyped(typedChar, keyCode);
    buttonList.get(0)).enabled = (field_146583_f.getText().trim().length() > 0);
    
    if ((keyCode == 28) || (keyCode == 156))
    {
      actionPerformed((GuiButton)buttonList.get(0));
    }
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    field_146583_f.mouseClicked(mouseX, mouseY, mouseButton);
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, I18n.format("selectWorld.renameTitle", new Object[0]), width / 2, 20, 16777215);
    drawString(fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), width / 2 - 100, 47, 10526880);
    field_146583_f.drawTextBox();
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
