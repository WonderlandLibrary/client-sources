package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;

public class GuiScreenWorking extends GuiScreen implements net.minecraft.util.IProgressUpdate
{
  private String field_146591_a = "";
  private String field_146589_f = "";
  
  private int field_146590_g;
  private boolean field_146592_h;
  private static final String __OBFID = "CL_00000707";
  
  public GuiScreenWorking() {}
  
  public void displaySavingString(String message)
  {
    resetProgressAndMessage(message);
  }
  




  public void resetProgressAndMessage(String p_73721_1_)
  {
    field_146591_a = p_73721_1_;
    displayLoadingString("Working...");
  }
  



  public void displayLoadingString(String message)
  {
    field_146589_f = message;
    setLoadingProgress(0);
  }
  



  public void setLoadingProgress(int progress)
  {
    field_146590_g = progress;
  }
  
  public void setDoneWorking()
  {
    field_146592_h = true;
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    if (field_146592_h)
    {
      mc.displayGuiScreen(null);
    }
    else
    {
      drawDefaultBackground();
      drawCenteredString(fontRendererObj, field_146591_a, width / 2, 70, 16777215);
      drawCenteredString(fontRendererObj, field_146589_f + " " + field_146590_g + "%", width / 2, 90, 16777215);
      super.drawScreen(mouseX, mouseY, partialTicks);
    }
  }
}
