package net.SliceClient.altamanager;

import java.io.IOException;
import net.SliceClient.TTF.TTFManager;
import net.SliceClient.TTF.TTFRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public final class GuiAltManager extends GuiScreen
{
  private GuiPasswordField password;
  private final GuiScreen previousScreen;
  private AltLoginThread thread;
  private GuiTextField username;
  
  public GuiAltManager(GuiScreen previousScreen)
  {
    this.previousScreen = previousScreen;
  }
  
  protected void actionPerformed(GuiButton button) {
    switch (id) {
    case 1: 
      mc.displayGuiScreen(previousScreen);
      break;
    case 0: 
      thread = new AltLoginThread(username.getText(), password.getText());
      thread.start();
    }
  }
  
  public void drawScreen(int x, int y, float z) {
    drawDefaultBackground();
    TTFManager.getInstance().getLemonMilk().drawCenteredString("Alt Login", width / 2 - 10, 20.0F, -1);
    TTFManager.getInstance().getLemonMilk().drawCenteredString(thread == null ? "§7Waiting..." : thread.getStatus(), width / 2 - 9, 29.0F, -1);
    
    TTFManager.getInstance().getLemonMilk().drawStringWithShadow("§7Name: §f" + getMinecraftsession.getUsername(), 1.0F, 1.0F, 0);
    
    username.drawTextBox();
    password.drawTextBox();
    if (username.getText().isEmpty()) {
      TTFManager.getInstance().getLemonMilk().drawString("Username / E-Mail", width / 2 - 96, 66.0F, -7829368);
    }
    if (password.getText().isEmpty()) {
      TTFManager.getInstance().getLemonMilk().drawString("Password", width / 2 - 96, 106.0F, -7829368);
    }
    super.drawScreen(x, y, z);
  }
  
  public void initGui() {
    int var3 = height / 4 + 24;
    buttonList.add(new GuiButton(0, width / 2 - 100, var3 + 72 + 12, "Login"));
    buttonList.add(new GuiButton(1, width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
    username = new GuiTextField(var3, Minecraft.fontRendererObj, width / 2 - 100, 60, 200, 20);
    password = new GuiPasswordField(Minecraft.fontRendererObj, width / 2 - 100, 100, 200, 20);
    username.setFocused(true);
    org.lwjgl.input.Keyboard.enableRepeatEvents(true);
  }
  
  protected void keyTyped(char character, int key) {
    try {
      super.keyTyped(character, key);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (character == '\t') {
      if ((!username.isFocused()) && (!password.isFocused())) {
        username.setFocused(true);
      } else {
        username.setFocused(password.isFocused());
        password.setFocused(!username.isFocused());
      }
    }
    if (character == '\r') {
      actionPerformed((GuiButton)buttonList.get(0));
    }
    username.textboxKeyTyped(character, key);
    password.textboxKeyTyped(character, key);
  }
  
  protected void mouseClicked(int x, int y, int button) {
    try {
      super.mouseClicked(x, y, button);
    } catch (IOException e) {
      e.printStackTrace();
    }
    username.mouseClicked(x, y, button);
    password.mouseClicked(x, y, button);
  }
  
  public void onGuiClosed() {
    org.lwjgl.input.Keyboard.enableRepeatEvents(false);
  }
  
  public void updateScreen() {
    username.updateCursorCounter();
    password.updateCursorCounter();
  }
}
