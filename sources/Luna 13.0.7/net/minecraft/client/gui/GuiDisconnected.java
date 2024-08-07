package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;

public class GuiDisconnected
  extends GuiScreen
{
  private String reason;
  private IChatComponent message;
  private List multilineMessage;
  private final GuiScreen parentScreen;
  private int field_175353_i;
  private static final String __OBFID = "CL_00000693";
  
  public GuiDisconnected(GuiScreen p_i45020_1_, String p_i45020_2_, IChatComponent p_i45020_3_)
  {
    this.parentScreen = p_i45020_1_;
    this.reason = I18n.format(p_i45020_2_, new Object[0]);
    this.message = p_i45020_3_;
  }
  
  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {}
  
  public void initGui()
  {
    this.buttonList.clear();
    this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), width - 50);
    this.field_175353_i = (this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT);
    this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
  }
  
  protected void actionPerformed(GuiButton button)
    throws IOException
  {
    if (button.id == 0) {
      this.mc.displayGuiScreen(this.parentScreen);
    }
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, this.reason, width / 2, height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
    int var4 = height / 2 - this.field_175353_i / 2;
    if (this.multilineMessage != null) {
      for (Iterator var5 = this.multilineMessage.iterator(); var5.hasNext(); var4 += this.fontRendererObj.FONT_HEIGHT)
      {
        String var6 = (String)var5.next();
        drawCenteredString(this.fontRendererObj, var6, width / 2, var4, 16777215);
      }
    }
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
