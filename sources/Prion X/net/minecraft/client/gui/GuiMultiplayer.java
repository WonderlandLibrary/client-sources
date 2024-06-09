package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector.LanServer;
import net.minecraft.client.network.LanServerDetector.LanServerList;
import net.minecraft.client.network.LanServerDetector.ThreadLanServerFind;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.resources.I18n;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiMultiplayer extends GuiScreen implements GuiYesNoCallback
{
  private static final Logger logger = ;
  private final OldServerPinger oldServerPinger = new OldServerPinger();
  private GuiScreen parentScreen;
  private ServerSelectionList serverListSelector;
  private ServerList savedServerList;
  private GuiButton btnEditServer;
  private GuiButton btnSelectServer;
  private GuiButton btnDeleteServer;
  private boolean deletingServer;
  private boolean addingServer;
  private boolean editingServer;
  private boolean directConnect;
  private String field_146812_y;
  private ServerData selectedServer;
  private LanServerDetector.LanServerList lanServerList;
  private LanServerDetector.ThreadLanServerFind lanServerDetector;
  private boolean initialized;
  private static final String __OBFID = "CL_00000814";
  
  public GuiMultiplayer(GuiScreen parentScreen)
  {
    this.parentScreen = parentScreen;
  }
  



  public void initGui()
  {
    Keyboard.enableRepeatEvents(true);
    buttonList.clear();
    
    if (!initialized)
    {
      initialized = true;
      savedServerList = new ServerList(mc);
      savedServerList.loadServerList();
      lanServerList = new LanServerDetector.LanServerList();
      
      try
      {
        lanServerDetector = new LanServerDetector.ThreadLanServerFind(lanServerList);
        lanServerDetector.start();
      }
      catch (Exception var2)
      {
        logger.warn("Unable to start LAN server detection: " + var2.getMessage());
      }
      
      serverListSelector = new ServerSelectionList(this, mc, width, height, 32, height - 64, 36);
      serverListSelector.func_148195_a(savedServerList);
    }
    else
    {
      serverListSelector.setDimensions(width, height, 32, height - 64);
    }
    
    createButtons();
  }
  


  public void handleMouseInput()
    throws IOException
  {
    super.handleMouseInput();
    serverListSelector.func_178039_p();
  }
  
  public void createButtons()
  {
    buttonList.add(this.btnEditServer = new GuiButton(7, width / 2 - 154, height - 28, 70, 20, I18n.format("selectServer.edit", new Object[0])));
    buttonList.add(this.btnDeleteServer = new GuiButton(2, width / 2 - 74, height - 28, 70, 20, I18n.format("selectServer.delete", new Object[0])));
    buttonList.add(this.btnSelectServer = new GuiButton(1, width / 2 - 154, height - 52, 100, 20, I18n.format("selectServer.select", new Object[0])));
    buttonList.add(new GuiButton(4, width / 2 - 50, height - 52, 100, 20, I18n.format("selectServer.direct", new Object[0])));
    buttonList.add(new GuiButton(3, width / 2 + 4 + 50, height - 52, 100, 20, I18n.format("selectServer.add", new Object[0])));
    buttonList.add(new GuiButton(8, width / 2 + 4, height - 28, 70, 20, I18n.format("selectServer.refresh", new Object[0])));
    buttonList.add(new GuiButton(0, width / 2 + 4 + 76, height - 28, 75, 20, I18n.format("gui.cancel", new Object[0])));
    selectServer(serverListSelector.func_148193_k());
  }
  



  public void updateScreen()
  {
    super.updateScreen();
    
    if (lanServerList.getWasUpdated())
    {
      List var1 = lanServerList.getLanServers();
      lanServerList.setWasNotUpdated();
      serverListSelector.func_148194_a(var1);
    }
    
    oldServerPinger.pingPendingNetworks();
  }
  



  public void onGuiClosed()
  {
    Keyboard.enableRepeatEvents(false);
    
    if (lanServerDetector != null)
    {
      lanServerDetector.interrupt();
      lanServerDetector = null;
    }
    
    oldServerPinger.clearPendingNetworks();
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (enabled)
    {
      GuiListExtended.IGuiListEntry var2 = serverListSelector.func_148193_k() < 0 ? null : serverListSelector.getListEntry(serverListSelector.func_148193_k());
      
      if ((id == 2) && ((var2 instanceof ServerListEntryNormal)))
      {
        String var9 = getServerDataserverName;
        
        if (var9 != null)
        {
          deletingServer = true;
          String var4 = I18n.format("selectServer.deleteQuestion", new Object[0]);
          String var5 = "'" + var9 + "' " + I18n.format("selectServer.deleteWarning", new Object[0]);
          String var6 = I18n.format("selectServer.deleteButton", new Object[0]);
          String var7 = I18n.format("gui.cancel", new Object[0]);
          GuiYesNo var8 = new GuiYesNo(this, var4, var5, var6, var7, serverListSelector.func_148193_k());
          mc.displayGuiScreen(var8);
        }
      }
      else if (id == 1)
      {
        connectToSelected();
      }
      else if (id == 4)
      {
        directConnect = true;
        mc.displayGuiScreen(new GuiScreenServerList(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "")));
      }
      else if (id == 3)
      {
        addingServer = true;
        mc.displayGuiScreen(new GuiScreenAddServer(this, this.selectedServer = new ServerData(I18n.format("selectServer.defaultName", new Object[0]), "")));
      }
      else if ((id == 7) && ((var2 instanceof ServerListEntryNormal)))
      {
        editingServer = true;
        ServerData var3 = ((ServerListEntryNormal)var2).getServerData();
        selectedServer = new ServerData(serverName, serverIP);
        selectedServer.copyFrom(var3);
        mc.displayGuiScreen(new GuiScreenAddServer(this, selectedServer));
      }
      else if (id == 0)
      {
        mc.displayGuiScreen(parentScreen);
      }
      else if (id == 8)
      {
        refreshServerList();
      }
    }
  }
  
  private void refreshServerList()
  {
    mc.displayGuiScreen(new GuiMultiplayer(parentScreen));
  }
  
  public void confirmClicked(boolean result, int id)
  {
    GuiListExtended.IGuiListEntry var3 = serverListSelector.func_148193_k() < 0 ? null : serverListSelector.getListEntry(serverListSelector.func_148193_k());
    
    if (deletingServer)
    {
      deletingServer = false;
      
      if ((result) && ((var3 instanceof ServerListEntryNormal)))
      {
        savedServerList.removeServerData(serverListSelector.func_148193_k());
        savedServerList.saveServerList();
        serverListSelector.func_148192_c(-1);
        serverListSelector.func_148195_a(savedServerList);
      }
      
      mc.displayGuiScreen(this);
    }
    else if (directConnect)
    {
      directConnect = false;
      
      if (result)
      {
        connectToServer(selectedServer);
      }
      else
      {
        mc.displayGuiScreen(this);
      }
    }
    else if (addingServer)
    {
      addingServer = false;
      
      if (result)
      {
        savedServerList.addServerData(selectedServer);
        savedServerList.saveServerList();
        serverListSelector.func_148192_c(-1);
        serverListSelector.func_148195_a(savedServerList);
      }
      
      mc.displayGuiScreen(this);
    }
    else if (editingServer)
    {
      editingServer = false;
      
      if ((result) && ((var3 instanceof ServerListEntryNormal)))
      {
        ServerData var4 = ((ServerListEntryNormal)var3).getServerData();
        serverName = selectedServer.serverName;
        serverIP = selectedServer.serverIP;
        var4.copyFrom(selectedServer);
        savedServerList.saveServerList();
        serverListSelector.func_148195_a(savedServerList);
      }
      
      mc.displayGuiScreen(this);
    }
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    int var3 = serverListSelector.func_148193_k();
    GuiListExtended.IGuiListEntry var4 = var3 < 0 ? null : serverListSelector.getListEntry(var3);
    
    if (keyCode == 63)
    {
      refreshServerList();


    }
    else if (var3 >= 0)
    {
      if (keyCode == 200)
      {
        if (isShiftKeyDown())
        {
          if ((var3 > 0) && ((var4 instanceof ServerListEntryNormal)))
          {
            savedServerList.swapServers(var3, var3 - 1);
            selectServer(serverListSelector.func_148193_k() - 1);
            serverListSelector.scrollBy(-serverListSelector.getSlotHeight());
            serverListSelector.func_148195_a(savedServerList);
          }
        }
        else if (var3 > 0)
        {
          selectServer(serverListSelector.func_148193_k() - 1);
          serverListSelector.scrollBy(-serverListSelector.getSlotHeight());
          
          if ((serverListSelector.getListEntry(serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan))
          {
            if (serverListSelector.func_148193_k() > 0)
            {
              selectServer(serverListSelector.getSize() - 1);
              serverListSelector.scrollBy(-serverListSelector.getSlotHeight());
            }
            else
            {
              selectServer(-1);
            }
          }
        }
        else
        {
          selectServer(-1);
        }
      }
      else if (keyCode == 208)
      {
        if (isShiftKeyDown())
        {
          if (var3 < savedServerList.countServers() - 1)
          {
            savedServerList.swapServers(var3, var3 + 1);
            selectServer(var3 + 1);
            serverListSelector.scrollBy(serverListSelector.getSlotHeight());
            serverListSelector.func_148195_a(savedServerList);
          }
        }
        else if (var3 < serverListSelector.getSize())
        {
          selectServer(serverListSelector.func_148193_k() + 1);
          serverListSelector.scrollBy(serverListSelector.getSlotHeight());
          
          if ((serverListSelector.getListEntry(serverListSelector.func_148193_k()) instanceof ServerListEntryLanScan))
          {
            if (serverListSelector.func_148193_k() < serverListSelector.getSize() - 1)
            {
              selectServer(serverListSelector.getSize() + 1);
              serverListSelector.scrollBy(serverListSelector.getSlotHeight());
            }
            else
            {
              selectServer(-1);
            }
          }
        }
        else
        {
          selectServer(-1);
        }
      }
      else if ((keyCode != 28) && (keyCode != 156))
      {
        super.keyTyped(typedChar, keyCode);
      }
      else
      {
        actionPerformed((GuiButton)buttonList.get(2));
      }
      
    }
    else {
      super.keyTyped(typedChar, keyCode);
    }
  }
  




  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    field_146812_y = null;
    drawDefaultBackground();
    serverListSelector.drawScreen(mouseX, mouseY, partialTicks);
    drawCenteredString(fontRendererObj, I18n.format("multiplayer.title", new Object[0]), width / 2, 20, 16777215);
    super.drawScreen(mouseX, mouseY, partialTicks);
    
    if (field_146812_y != null)
    {
      drawHoveringText(com.google.common.collect.Lists.newArrayList(Splitter.on("\n").split(field_146812_y)), mouseX, mouseY);
    }
  }
  
  public void connectToSelected()
  {
    GuiListExtended.IGuiListEntry var1 = serverListSelector.func_148193_k() < 0 ? null : serverListSelector.getListEntry(serverListSelector.func_148193_k());
    
    if ((var1 instanceof ServerListEntryNormal))
    {
      connectToServer(((ServerListEntryNormal)var1).getServerData());
    }
    else if ((var1 instanceof ServerListEntryLanDetected))
    {
      LanServerDetector.LanServer var2 = ((ServerListEntryLanDetected)var1).getLanServer();
      connectToServer(new ServerData(var2.getServerMotd(), var2.getServerIpPort()));
    }
  }
  
  private void connectToServer(ServerData server)
  {
    mc.displayGuiScreen(new net.minecraft.client.multiplayer.GuiConnecting(this, mc, server));
  }
  
  public void selectServer(int index)
  {
    serverListSelector.func_148192_c(index);
    GuiListExtended.IGuiListEntry var2 = index < 0 ? null : serverListSelector.getListEntry(index);
    btnSelectServer.enabled = false;
    btnEditServer.enabled = false;
    btnDeleteServer.enabled = false;
    
    if ((var2 != null) && (!(var2 instanceof ServerListEntryLanScan)))
    {
      btnSelectServer.enabled = true;
      
      if ((var2 instanceof ServerListEntryNormal))
      {
        btnEditServer.enabled = true;
        btnDeleteServer.enabled = true;
      }
    }
  }
  
  public OldServerPinger getOldServerPinger()
  {
    return oldServerPinger;
  }
  
  public void func_146793_a(String p_146793_1_)
  {
    field_146812_y = p_146793_1_;
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    super.mouseClicked(mouseX, mouseY, mouseButton);
    serverListSelector.func_148179_a(mouseX, mouseY, mouseButton);
  }
  



  protected void mouseReleased(int mouseX, int mouseY, int state)
  {
    super.mouseReleased(mouseX, mouseY, state);
    serverListSelector.func_148181_b(mouseX, mouseY, state);
  }
  
  public ServerList getServerList()
  {
    return savedServerList;
  }
  
  public boolean func_175392_a(ServerListEntryNormal p_175392_1_, int p_175392_2_)
  {
    return p_175392_2_ > 0;
  }
  
  public boolean func_175394_b(ServerListEntryNormal p_175394_1_, int p_175394_2_)
  {
    return p_175394_2_ < savedServerList.countServers() - 1;
  }
  
  public void func_175391_a(ServerListEntryNormal p_175391_1_, int p_175391_2_, boolean p_175391_3_)
  {
    int var4 = p_175391_3_ ? 0 : p_175391_2_ - 1;
    savedServerList.swapServers(p_175391_2_, var4);
    
    if (serverListSelector.func_148193_k() == p_175391_2_)
    {
      selectServer(var4);
    }
    
    serverListSelector.func_148195_a(savedServerList);
  }
  
  public void func_175393_b(ServerListEntryNormal p_175393_1_, int p_175393_2_, boolean p_175393_3_)
  {
    int var4 = p_175393_3_ ? savedServerList.countServers() - 1 : p_175393_2_ + 1;
    savedServerList.swapServers(p_175393_2_, var4);
    
    if (serverListSelector.func_148193_k() == p_175393_2_)
    {
      selectServer(var4);
    }
    
    serverListSelector.func_148195_a(savedServerList);
  }
}
