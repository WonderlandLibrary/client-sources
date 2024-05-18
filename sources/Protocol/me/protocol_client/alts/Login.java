package me.protocol_client.alts;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.utils.TimerUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class Login
  extends GuiScreen
{
  private GuiScreen parentScreen;
  private GuiTextField userNameField;
  private GuiPasswordField passwordField;
  private String errorMessage;
  private boolean displayError;
  private Alt altObject;
  
  public Login(GuiScreen parentScreen)
  {
    this.parentScreen = parentScreen;
    
  }
  
  public void initGui()
  {
    int startX = this.width / 2 - 100;
    int width = 200;
    
    this.userNameField = new GuiTextField(3, Wrapper.fr(), startX, this.height / 2 - 110, width, 20);
    this.passwordField = new GuiPasswordField(Wrapper.fr(), startX, this.height / 2 - 75, width, 20);
    
    this.buttonList.add(new GuiButton(0, this.width / 2 - 102, this.height - 180, 205, 20, "Cancel"));
    this.buttonList.add(new GuiButton(1, this.width / 2 - 102, this.height - 210, 205, 20, "Login"));
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    this.userNameField.drawTextBox();
    this.passwordField.drawTextBox();
    if (this.displayError)
    {
      drawCenteredString(Wrapper.fr(), this.errorMessage, this.width / 2, 30, 16711680);
    }
    drawCenteredString(Wrapper.fr(), "Login", this.width / 2, 5, 16777215);
    drawCenteredString(Wrapper.fr(), "Current User:", this.width / 2, 205, Protocol.getColor().getRGB());
    drawCenteredString(Wrapper.fr(), Wrapper.mc().session.getUsername(), this.width / 2, 215, Protocol.getColor().getRGB());
    drawCenteredString(Wrapper.fr(), "Username:", this.width / 2 - 74, this.height / 2 - 120, 16777215);
    drawCenteredString(Wrapper.fr(), "Password:", this.width / 2 - 75, this.height / 2 - 85, 16777215);
    
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  
  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
	  if(keyCode == Keyboard.KEY_RETURN){
		  actionPerformed((GuiButton)this.buttonList.get(1));
	  }
    if (keyCode == 15) {
      if (this.userNameField.isFocused())
      {
        this.userNameField.setFocused(false);
        this.passwordField.setFocused(true);
      }
      else if (this.passwordField.isFocused())
      {
        this.userNameField.setFocused(true);
        this.passwordField.setFocused(false);
      }
      else
      {
        this.userNameField.setFocused(true);
        this.passwordField.setFocused(false);
      }
    }
    this.userNameField.textboxKeyTyped(typedChar, keyCode);
    this.passwordField.textboxKeyTyped(typedChar, keyCode);
  }
  
  protected void actionPerformed(GuiButton button)
    throws IOException
  {
    if (button.id == 0)
    {
      Wrapper.mc().displayGuiScreen(this.parentScreen);
    }
    else if (button.id == 1)
    {
      String userName = this.userNameField.getText();
      String password = this.passwordField.getText();
      if (userName.length() == 0)
      {
      }
      else if ((userName.length() == 0) && (password.length() == 0))
      {
      }
      else
      {
        this.displayError = false;
        
        this.altObject = new Alt(userName.contains("@") ? "" : userName, userName.contains("@") ? userName : "", password);
        if (YggdrasilLoginBridge.loginWithAlt(this.altObject) != null) {
          Wrapper.mc().displayGuiScreen(this.parentScreen);
        }
      }
    }
  }
  
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    this.userNameField.mouseClicked(mouseX, mouseY, mouseButton);
    this.passwordField.mouseClicked(mouseX, mouseY, mouseButton);
    
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }
  
  protected void mouseReleased(int mouseX, int mouseY, int mouseButton)
  {
    super.mouseReleased(mouseX, mouseY, mouseButton);
  }
}
