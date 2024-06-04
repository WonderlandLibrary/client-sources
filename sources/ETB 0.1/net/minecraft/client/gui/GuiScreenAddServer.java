package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;

public class GuiScreenAddServer extends GuiScreen
{
  private final GuiScreen parentScreen;
  private final ServerData serverData;
  private GuiTextField serverIPField;
  private GuiTextField serverNameField;
  private GuiButton serverResourcePacks;
  private static final String __OBFID = "CL_00000695";
  
  public GuiScreenAddServer(GuiScreen p_i1033_1_, ServerData p_i1033_2_)
  {
    parentScreen = p_i1033_1_;
    serverData = p_i1033_2_;
  }
  



  public void updateScreen()
  {
    serverNameField.updateCursorCounter();
    serverIPField.updateCursorCounter();
  }
  



  public void initGui()
  {
    org.lwjgl.input.Keyboard.enableRepeatEvents(true);
    buttonList.clear();
    buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 18, I18n.format("addServer.add", new Object[0])));
    buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 18, I18n.format("gui.cancel", new Object[0])));
    buttonList.add(this.serverResourcePacks = new GuiButton(2, width / 2 - 100, height / 4 + 72, I18n.format("addServer.resourcePack", new Object[0]) + ": " + serverData.getResourceMode().getMotd().getFormattedText()));
    serverNameField = new GuiTextField(0, fontRendererObj, width / 2 - 100, 66, 200, 20);
    serverNameField.setFocused(true);
    serverNameField.setText(serverData.serverName);
    serverIPField = new GuiTextField(1, fontRendererObj, width / 2 - 100, 106, 200, 20);
    serverIPField.setMaxStringLength(128);
    serverIPField.setText(serverData.serverIP);
    buttonList.get(0)).enabled = ((serverIPField.getText().length() > 0) && (serverIPField.getText().split(":").length > 0) && (serverNameField.getText().length() > 0));
  }
  



  public void onGuiClosed()
  {
    org.lwjgl.input.Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (enabled)
    {
      if (id == 2)
      {
        serverData.setResourceMode(net.minecraft.client.multiplayer.ServerData.ServerResourceMode.values()[((serverData.getResourceMode().ordinal() + 1) % net.minecraft.client.multiplayer.ServerData.ServerResourceMode.values().length)]);
        serverResourcePacks.displayString = (I18n.format("addServer.resourcePack", new Object[0]) + ": " + serverData.getResourceMode().getMotd().getFormattedText());
      }
      else if (id == 1)
      {
        parentScreen.confirmClicked(false, 0);
      }
      else if (id == 0)
      {
        serverData.serverName = serverNameField.getText();
        serverData.serverIP = serverIPField.getText();
        parentScreen.confirmClicked(true, 0);
      }
    }
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    serverNameField.textboxKeyTyped(typedChar, keyCode);
    serverIPField.textboxKeyTyped(typedChar, keyCode);
    
    if (keyCode == 15)
    {
      serverNameField.setFocused(!serverNameField.isFocused());
      serverIPField.setFocused(!serverIPField.isFocused());
    }
    
    if ((keyCode == 28) || (keyCode == 156))
    {
      actionPerformed((GuiButton)buttonList.get(0));
    }
    
    buttonList.get(0)).enabled = ((serverIPField.getText().length() > 0) && (serverIPField.getText().split(":").length > 0) && (serverNameField.getText().length() > 0));
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    serverIPField.mouseClicked(mouseX, mouseY, mouseButton);
    serverNameField.mouseClicked(mouseX, mouseY, mouseButton);
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    drawDefaultBackground();
    drawCenteredString(fontRendererObj, I18n.format("addServer.title", new Object[0]), width / 2, 17, 16777215);
    drawString(fontRendererObj, I18n.format("addServer.enterName", new Object[0]), width / 2 - 100, 53, 10526880);
    drawString(fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), width / 2 - 100, 94, 10526880);
    serverNameField.drawTextBox();
    serverIPField.drawTextBox();
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
