package zamorozka.gui;

import java.io.IOException;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiProxy
  extends GuiScreen
{
  private GuiScreen parentScreen;
  private GuiTextField ipPortTextField;
  public static String strIpPort = "";
  
  public GuiProxy(GuiScreen guiscreen)
  {
    this.parentScreen = guiscreen;
  }
  
  public void updateScreen()
  {
    this.ipPortTextField.updateCursorCounter();
  }
  
  public void onGuiClosed()
  {
    Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton guibutton)
  {
    if (!guibutton.enabled) {
      return;
    }
    if (guibutton.id == 1) {
      this.mc.displayGuiScreen(this.parentScreen);
    } else if (guibutton.id == 0) {
      try
      {
        strIpPort = this.ipPortTextField.getText();
        if (strIpPort.equals(""))
        {
          System.setProperty("proxySet", "false");
        }
        else
        {
          System.setProperty("proxySet", "true");
          System.setProperty("socksProxyHost", strIpPort.split(":")[0]);
          System.setProperty("socksProxyPort", strIpPort.split(":")[1]);
        }
      }
      catch (Exception e)
      {
        System.setProperty("proxySet", "false");
        strIpPort = "";
      }
    }
    this.mc.displayGuiScreen(this.parentScreen);
  }
  
  protected void keyTyped(char c, int i)
  {
    this.ipPortTextField.textboxKeyTyped(c, i);
    if ((c == '\t') && 
      (this.ipPortTextField.isFocused())) {
      this.ipPortTextField.setFocused(false);
    }
    if (c == '\r') {
      actionPerformed((GuiButton)this.buttonList.get(0));
    }
  }
  
  protected void mouseClicked(int i, int j, int k)
    throws IOException
  {
    super.mouseClicked(i, j, k);
    this.ipPortTextField.mouseClicked(i, j, k);
  }
  
  public void initGui()
  {
    Keyboard.enableRepeatEvents(true);
    this.buttonList.clear();
    this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 50 + 12, "Done"));
    this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 75 + 12, "Cancel"));
    this.ipPortTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
  }
  
  public void drawScreen(int i, int j, float f)
  {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, "Proxy", this.width / 2, this.height / 4 - 60 + 20, 16777215);
    drawString(this.fontRendererObj, "IP:Port (Socks 4/5)", this.width / 2 - 100, 104, 10526880);
    this.ipPortTextField.drawTextBox();
    super.drawScreen(i, j, f);
  }
}

