package me.hexxed.mercury.gui;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.util.FileUtils;
import me.hexxed.mercury.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

public class GuiDirectLogin
  extends GuiScreen
{
  public GuiScreen parent;
  public GuiTextField usernameBox;
  public GuiTextField passwordBox;
  public GuiTextField sessionBox;
  
  public GuiDirectLogin(GuiScreen paramScreen)
  {
    parent = paramScreen;
  }
  
  public void initGui()
  {
    Keyboard.enableRepeatEvents(true);
    buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12, "Login"));
    buttonList.add(new GuiButton(3, width / 2 - 100, height / 4 + 96 + 36, "Relog"));
    buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 96 + 70, "Random Alt"));
    buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 96 + 106, "Back"));
    usernameBox = new GuiTextField(3, mc.fontRendererObj, width / 2 - 100, 51, 200, 20);
    passwordBox = new GuiTextField(4, mc.fontRendererObj, width / 2 - 100, 91, 200, 20);
  }
  
  public void onGuiClosed()
  {
    Keyboard.enableRepeatEvents(false);
  }
  
  public void updateScreen()
  {
    usernameBox.updateCursorCounter();
    passwordBox.updateCursorCounter();
  }
  
  public void mouseClicked(int x, int y, int b)
  {
    usernameBox.mouseClicked(x, y, b);
    passwordBox.mouseClicked(x, y, b);
    try {
      super.mouseClicked(x, y, b);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static String lastusername = "";
  public static String lastpassword = "";
  
  protected void actionPerformed(GuiButton par1GuiButton)
  {
    if (id == 1)
    {
      if (usernameBox.getText().length() > 0)
      {







        new Thread()
        {
          public void run()
          {
            GuiDirectLogin.lastusername = usernameBox.getText();
            GuiDirectLogin.lastpassword = passwordBox.getText();
            getValuespremium = Util.login(usernameBox.getText(), passwordBox.getText());
          }
        }.start();
      }
    }
    else if (id == 2) {
      Minecraft.getMinecraft().displayGuiScreen(parent);
    } else if (id == 3)
    {





      new Thread()
      {
        public void run()
        {
          getValuespremium = Util.login(GuiDirectLogin.lastusername, GuiDirectLogin.lastpassword);
        }
      }.start();
    } else if (id == 4) {
      final Random r = new Random();
      final List<String> alts = FileUtils.getAlts();
      if (alts.isEmpty()) {
        return;
      }
      












      new Thread()
      {
        public void run()
        {
          getValuespremium = false;
          while ((!alts.isEmpty()) && (!getValuespremium)) {
            int index = r.nextInt(alts.size());
            GuiDirectLogin.lastusername = ((String)alts.get(index)).split(":")[0];
            GuiDirectLogin.lastpassword = ((String)alts.get(index)).split(":")[1];
            getValuespremium = Util.login(((String)alts.get(index)).split(":")[0], ((String)alts.get(index)).split(":")[1]);
            if (!getValuespremium) FileUtils.removeAlt((String)alts.get(index));
          }
        }
      }.start();
    }
  }
  
  public void keyTyped(char ch, int key)
  {
    if (key == 1) {
      Minecraft.getMinecraft().displayGuiScreen(parent);
    }
    usernameBox.textboxKeyTyped(ch, key);
    passwordBox.textboxKeyTyped(ch, key);
    if (key == 15) {
      if (usernameBox.isFocused())
      {
        usernameBox.setFocused(false);
        passwordBox.setFocused(true);
      }
      else
      {
        usernameBox.setFocused(true);
        passwordBox.setFocused(false);
      }
    }
    if (key == 28) {
      actionPerformed((GuiButton)buttonList.get(0));
    }
    if (key == 13) {
      actionPerformed((GuiButton)buttonList.get(0));
    }
    buttonList.get(0)).enabled = (usernameBox.getText().length() > 3);
  }
  
  public void drawScreen(int x, int y, float f)
  {
    drawDefaultBackground();
    drawString(mc.fontRendererObj, "Username", width / 2 - 100, 38, 10526880);
    drawString(mc.fontRendererObj, "§4*", width / 2 - 106, 38, 10526880);
    drawString(mc.fontRendererObj, "Password", width / 2 - 100, 79, 10526880);
    if (getValuespremium) {
      mc.fontRendererObj.drawString("Username: §7" + mc.session.getUsername(), 3, 3, 16777215);
    } else {
      mc.fontRendererObj.drawString("Cracked as: §7" + mc.session.getUsername(), 3, 3, 16777215);
    }
    try
    {
      usernameBox.drawTextBox();
      passwordBox.drawTextBox();
    }
    catch (Exception err)
    {
      err.printStackTrace();
    }
    super.drawScreen(x, y, f);
  }
}
