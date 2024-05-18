package net.SliceClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import net.SliceClient.bindmanager.BindManager;
import net.SliceClient.clickgui.ClickGui;
import net.SliceClient.clickgui.PanelManager;
import net.SliceClient.commandbase.Command;
import net.SliceClient.commandbase.CommandManager;
import net.SliceClient.commands.FriendManager;
import net.SliceClient.event.EventPostMotion;
import net.SliceClient.module.Module;
import net.SliceClient.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;
import org.lwjgl.opengl.Display;

public class Slice
{
  public static String ClientName = "Slice";
  public static String v = "15";
  private static String Client_Name = "SliceB" + v + " | Minecraft 1.8";
  public static String Start_Name = "SliceB" + v + " | Minecraft 1.8";
  public static String prefix = "§8[§3Slice§8] §9";
  public static String version = "§bb" + v;
  public static String B = "Slice";
  public static String coder = "Mckc4HMaste7";
  public static String MainMenu = "Slice";
  public static String made = "Cracked by Mckc4HMaste7";
  public static String G = "Slice";
  public static final Slice theClient = new Slice();
  public static boolean teams = false;
  public static ModuleManager moduleManager;
  public static BindManager bindmanager;
  public static ArrayList<String> friends = new ArrayList();
  public static File directory;
  public static FriendManager friendmanager;
  public static final EventPostMotion EVENT_POST_MOTION = new EventPostMotion();
  
  public static CommandManager cm;
  public static ClickGui gui;
  public static PanelManager panelManager;
  private GuiManagerDisplayScreen guiKeybindDisplay;
  private GuiManagerDisplayScreen guiDisplay;
  private FriendManager friendManager = new FriendManager();
  
  public Slice() {}
  
  public static void StartClient() { directory = new File(getMinecraftmcDataDir, B);
    if (!directory.exists())
      directory.mkdirs();
    friendffgg();
    Minecraft.getMinecraft();gameSettingsrenderDistanceChunks = 4;
    moduleManager = new ModuleManager();
    bindmanager = new BindManager();
    Display.setTitle(Client_Name);
    Command.huriostget();
    bindmanager.setKeyBinds();
    moduleManager.setModules();
    gui = new ClickGui();
    panelManager = new PanelManager();
    cm = new CommandManager();
    ModuleManager.getModule(net.SliceClient.modules.Gui.class).setBind(54);
  }
  
  private static void friendffgg() {
    File friends = new File(String.valueOf(directory) + "\\friends.txt");
    if (!friends.exists()) {
      try {
        friends.createNewFile();
      }
      catch (IOException localIOException) {}
    }
    try {
      Throwable t3 = null;
      Throwable t4;
      Throwable t4; try { BufferedReader br = new BufferedReader(new FileReader(friends));
        try {
          String line;
          while ((line = br.readLine()) != null) { String line;
            if ((!line.equals("")) && (!line.equals(" "))) {
              friends.add(line);
            }
          }
        } finally {
          if (br != null) {
            br.close();
          }
        }
      } finally {
        if (t3 == null) {
          Throwable t4 = null;
          t3 = t4;
        } else {
          Throwable t4 = null;
          if (t3 != t4) {
            t3.addSuppressed(t4);
          }
        }
      }
    }
    catch (FileNotFoundException localFileNotFoundException) {}catch (IOException localIOException1) {}
  }
  


  public static Slice getTrap()
  {
    return theClient;
  }
  
  public FriendManager getFriendManager()
  {
    return friendManager;
  }
  

  public static boolean getState(String hack)
  {
    for (Module m : ModuleManager.activeModules) {
      if ((m.getName() == hack) && 
        (m.getState())) {
        return true;
      }
    }
    return false;
  }
  


  public static void addMessage(String s)
  {
    getMinecraftingameGUI.getChatGUI().printChatMessage(new ChatComponentText(prefix + s));
  }
  


  public static void setClient_Name(String client_Name)
  {
    Client_Name = client_Name;
  }
  
  public static ModuleManager getModuleManager() {
    return moduleManager;
  }
}
