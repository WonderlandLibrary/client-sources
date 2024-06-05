package zamorozka.gui;

import java.io.IOException;
import java.util.UUID;

import org.lwjgl.input.Keyboard;

import com.google.common.base.Charsets;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import zamorozka.main.Zamorozka;

public class GuiBungeeIpNick
extends GuiScreen
{
private GuiScreen parentScreen;
private GuiTextField usernameTextField;
private GuiTextField usernameTextField2;
private String error;

public GuiBungeeIpNick(GuiScreen parentScreen2) { this.parentScreen = parentScreen2; }


public void updateScreen() {
  this.usernameTextField.updateCursorCounter();
  this.usernameTextField2.updateCursorCounter();
}


public void onGuiClosed() { Keyboard.enableRepeatEvents(false); }


protected void actionPerformed(GuiButton guibutton) {
  if (!guibutton.enabled) {
    return;
  }
  if (guibutton.id == 1) {
    this.mc.displayGuiScreen(this.parentScreen);
  } else if (guibutton.id == 0) {
	  Zamorozka.getHackPack().setFakeUUID(UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.usernameTextField2.getText()).getBytes(Charsets.UTF_8)).toString());
    Zamorozka.getHackPack().setFakeIP(this.usernameTextField.getText());
  } 
  this.mc.displayGuiScreen(this.parentScreen);
}

protected void keyTyped(char c, int i) {
  this.usernameTextField.textboxKeyTyped(c, i);
  this.usernameTextField2.textboxKeyTyped(c, i);
  if (c == '\t' && 
    this.usernameTextField.isFocused()) {
    this.usernameTextField.setFocused(false);
  }
  
  if (c == '\r') {
    actionPerformed((GuiButton)this.buttonList.get(0));
  }
}

protected void mouseClicked(int i, int j, int k) throws IOException {
  super.mouseClicked(i, j, k);
  this.usernameTextField.mouseClicked(i, j, k);
  this.usernameTextField2.mouseClicked(i, j, k);
}

public void initGui() {
  Keyboard.enableRepeatEvents(true);
  this.buttonList.clear();
  this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Done"));
  this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
  this.usernameTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
  this.usernameTextField2 = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 100, 96, 200, 20);
  this.usernameTextField.setMaxStringLength(500);
  this.usernameTextField2.setMaxStringLength(500);
}

public void drawScreen(int i, int j, float f) {
  drawDefaultBackground();
  this.usernameTextField.drawTextBox();
  drawCenteredString(this.fontRendererObj, "↑  Nick, ↓ IP.", this.width / 2, this.height / 4 - 60 + 20, 16777215);
  this.usernameTextField2.drawTextBox();
  super.drawScreen(i, j, f);
}
}
