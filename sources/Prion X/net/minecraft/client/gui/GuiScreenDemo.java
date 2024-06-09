package net.minecraft.client.gui;

import java.net.URI;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;

public class GuiScreenDemo extends GuiScreen
{
  private static final Logger logger = ;
  private static final ResourceLocation field_146348_f = new ResourceLocation("textures/gui/demo_background.png");
  
  private static final String __OBFID = "CL_00000691";
  
  public GuiScreenDemo() {}
  
  public void initGui()
  {
    buttonList.clear();
    byte var1 = -16;
    buttonList.add(new GuiButton(1, width / 2 - 116, height / 2 + 62 + var1, 114, 20, I18n.format("demo.help.buy", new Object[0])));
    buttonList.add(new GuiButton(2, width / 2 + 2, height / 2 + 62 + var1, 114, 20, I18n.format("demo.help.later", new Object[0])));
  }
  
  protected void actionPerformed(GuiButton button) throws java.io.IOException
  {
    switch (id)
    {
    case 1: 
      enabled = false;
      
      try
      {
        Class var2 = Class.forName("java.awt.Desktop");
        Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
        var2.getMethod("browse", new Class[] { URI.class }).invoke(var3, new Object[] { new URI("http://www.minecraft.net/store?source=demo") });
      }
      catch (Throwable var4)
      {
        logger.error("Couldn't open link", var4);
      }
    


    case 2: 
      mc.displayGuiScreen(null);
      mc.setIngameFocus();
    }
    
  }
  


  public void updateScreen()
  {
    super.updateScreen();
  }
  



  public void drawDefaultBackground()
  {
    super.drawDefaultBackground();
    net.minecraft.client.renderer.GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(field_146348_f);
    int var1 = (width - 248) / 2;
    int var2 = (height - 166) / 2;
    drawTexturedModalRect(var1, var2, 0, 0, 248, 166);
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    int var4 = (width - 248) / 2 + 10;
    int var5 = (height - 166) / 2 + 8;
    fontRendererObj.drawString(I18n.format("demo.help.title", new Object[0]), var4, var5, 2039583);
    var5 += 12;
    GameSettings var6 = mc.gameSettings;
    fontRendererObj.drawString(I18n.format("demo.help.movementShort", new Object[] { GameSettings.getKeyDisplayString(keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(keyBindRight.getKeyCode()) }), var4, var5, 5197647);
    fontRendererObj.drawString(I18n.format("demo.help.movementMouse", new Object[0]), var4, var5 + 12, 5197647);
    fontRendererObj.drawString(I18n.format("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(keyBindJump.getKeyCode()) }), var4, var5 + 24, 5197647);
    fontRendererObj.drawString(I18n.format("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(keyBindInventory.getKeyCode()) }), var4, var5 + 36, 5197647);
    fontRendererObj.drawSplitString(I18n.format("demo.help.fullWrapped", new Object[0]), var4, var5 + 68, 218, 2039583);
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
