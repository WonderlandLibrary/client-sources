package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;

public class GuiDisconnected extends GuiScreen
{
  private String reason;
  private IChatComponent message;
  private List multilineMessage;
  private final GuiScreen parentScreen;
  private int field_175353_i;
  private static final String __OBFID = "CL_00000693";
  
  public GuiDisconnected(GuiScreen p_i45020_1_, String p_i45020_2_, IChatComponent p_i45020_3_)
  {
    parentScreen = p_i45020_1_;
    reason = I18n.format(p_i45020_2_, new Object[0]);
    message = p_i45020_3_;
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {}
  


  public void initGui()
  {
    buttonList.clear();
    multilineMessage = fontRendererObj.listFormattedStringToWidth(message.getFormattedText(), width - 50);
    field_175353_i = (multilineMessage.size() * fontRendererObj.FONT_HEIGHT);
    buttonList.add(new GuiButton(0, width / 2 - 100, height / 2 + field_175353_i / 2 + fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (id == 0)
    {
      mc.displayGuiScreen(parentScreen);
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, reason, width / 2, height / 2 - field_175353_i / 2 - fontRendererObj.FONT_HEIGHT * 2, 11184810);
    int var4 = height / 2 - field_175353_i / 2;
    
    if (multilineMessage != null)
    {
      for (Iterator var5 = multilineMessage.iterator(); var5.hasNext(); var4 += fontRendererObj.FONT_HEIGHT)
      {
        String var6 = (String)var5.next();
        drawCenteredString(fontRendererObj, var6, width / 2, var4, 16777215);
      }
    }
    
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
