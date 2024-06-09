package me.gishreload.yukon.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import net.minecraft.util.Session.Type;

public class GuiFakeParams
  extends GuiScreen
{
  protected GuiTextField ipField;
  protected GuiTextField fakeNickField;
  protected GuiTextField realNickField;
  protected GuiScreen prevScreen;
  
  public GuiFakeParams(GuiScreen screen)
  {
    this.prevScreen = screen;
  }
  
  public void initGui()
  {
    int fieldWidth = 200;
    int fieldHeight = 20;
    
    this.buttonList.clear();
    
    this.buttonList.add(new DarkButtons(1, this.width / 2 - 100, this.height / 4 + 130, fieldWidth, fieldHeight, "\u00a73Save"));
    
    this.buttonList.add(new DarkButtons(2, this.width / 2 - 100, this.height / 4 + 130 + fieldHeight + 8, fieldWidth, fieldHeight, "Cancel"));
    
    this.ipField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 4 + 100, fieldWidth, fieldHeight);
    
    this.fakeNickField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, this.height / 4 + 60, fieldWidth, fieldHeight);
    
    this.realNickField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, this.height / 4 + 20, fieldWidth, fieldHeight);
    
    //this.ipField.setText(this.mc.getFakeIp());
    //this.fakeNickField.setText(this.mc.getFakeNick());
    this.realNickField.setText(this.mc.getSession().getUsername());
  }
  
  public void handleMouseInput()
    throws IOException
  {
    super.handleMouseInput();
  }
  
  protected void actionPerformed(GuiButton button)
    throws IOException
  {
    if (button.id == 1)
    {
      Session realSession = this.mc.getSession();
      
      Session copiedSession = new Session(this.realNickField.getText(), realSession.getPlayerID(), realSession.getToken(), Session.Type.LEGACY.name());
      
      //this.mc.setSession(copiedSession);
      //this.mc.setFakeNick(this.fakeNickField.getText());
      //this.mc.setFakeIp(this.ipField.getText());
      this.mc.displayGuiScreen(this.prevScreen);
    }
    else if (button.id == 2)
    {
      this.mc.displayGuiScreen(this.prevScreen);
    }
  }
  
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    this.ipField.mouseClicked(mouseX, mouseY, mouseButton);
    this.fakeNickField.mouseClicked(mouseX, mouseY, mouseButton);
    this.realNickField.mouseClicked(mouseX, mouseY, mouseButton);
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }
  
  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    if (keyCode == 1)
    {
      this.mc.displayGuiScreen(this.prevScreen);
      return;
    }
    if (keyCode == 15) {
      if (this.realNickField.isFocused())
      {
        this.realNickField.setFocused(false);
        this.fakeNickField.setFocused(true);
      }
      else if (this.fakeNickField.isFocused())
      {
        this.fakeNickField.setFocused(false);
        this.ipField.setFocused(true);
      }
      else if (this.ipField.isFocused())
      {
        this.ipField.setFocused(false);
        this.realNickField.setFocused(true);
      }
    }
    if (this.ipField.isFocused()) {
      this.ipField.textboxKeyTyped(typedChar, keyCode);
    }
    if (this.fakeNickField.isFocused()) {
      this.fakeNickField.textboxKeyTyped(typedChar, keyCode);
    }
    if (this.realNickField.isFocused()) {
      this.realNickField.textboxKeyTyped(typedChar, keyCode);
    }
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, "Set nick / UUID (not working)", this.width / 2, this.height - 300, 16777215);
    
    drawCenteredString(this.fontRendererObj, "nick", this.width / 2, this.realNickField.yPosition - 15, 16777215);
    
    drawCenteredString(this.fontRendererObj, "Fake nick", this.width / 2, this.fakeNickField.yPosition - 15, 16777215);
    
    drawCenteredString(this.fontRendererObj, "Fake IP", this.width / 2, this.ipField.yPosition - 15, 16777215);
    
    this.ipField.drawTextBox();
    this.fakeNickField.drawTextBox();
    this.realNickField.drawTextBox();
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
