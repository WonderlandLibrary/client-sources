package optifine;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiMessage extends GuiScreen
{
  private GuiScreen parentScreen;
  private String messageLine1;
  private String messageLine2;
  private final List listLines2 = com.google.common.collect.Lists.newArrayList();
  protected String confirmButtonText;
  private int ticksUntilEnable;
  
  public GuiMessage(GuiScreen parentScreen, String line1, String line2)
  {
    this.parentScreen = parentScreen;
    messageLine1 = line1;
    messageLine2 = line2;
    confirmButtonText = I18n.format("gui.done", new Object[0]);
  }
  



  public void initGui()
  {
    buttonList.add(new GuiOptionButton(0, width / 2 - 74, height / 6 + 96, confirmButtonText));
    listLines2.clear();
    listLines2.addAll(fontRendererObj.listFormattedStringToWidth(messageLine2, width - 50));
  }
  
  protected void actionPerformed(GuiButton button) throws java.io.IOException
  {
    Config.getMinecraft().displayGuiScreen(parentScreen);
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, messageLine1, width / 2, 70, 16777215);
    int var4 = 90;
    
    for (Iterator var5 = listLines2.iterator(); var5.hasNext(); var4 += fontRendererObj.FONT_HEIGHT)
    {
      String var6 = (String)var5.next();
      drawCenteredString(fontRendererObj, var6, width / 2, var4, 16777215);
    }
    
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  
  public void setButtonDelay(int ticksUntilEnable)
  {
    this.ticksUntilEnable = ticksUntilEnable;
    
    GuiButton var3;
    for (Iterator var2 = buttonList.iterator(); var2.hasNext(); enabled = false)
    {
      var3 = (GuiButton)var2.next();
    }
  }
  



  public void updateScreen()
  {
    super.updateScreen();
    

    if (--ticksUntilEnable == 0) {
      GuiButton var2;
      for (Iterator var1 = buttonList.iterator(); var1.hasNext(); enabled = true)
      {
        var2 = (GuiButton)var1.next();
      }
    }
  }
}
