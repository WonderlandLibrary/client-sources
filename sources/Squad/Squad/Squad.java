package Squad;



import java.io.File;

import org.lwjgl.opengl.Display;

import Squad.Modules.Other.GuiHUD;
import Squad.Modules.Other.HUD;
import Squad.base.ModuleManager;
import Squad.commands.CommandManager;
import Squad.commands.KeyBindManager;
import Squad.files.ModuleFile;
import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.SettingsManager;
import net.minecraft.client.Minecraft;


public class Squad
{
  public static Squad instance = new Squad();
  public static final File directory = new File(
    Minecraft.getMinecraft().mcDataDir, "Squad");
  public static KeyBindManager keymgr;
  public static String ClientName = "SQUAD";
  public String ClientVersion = "0.5.2";
  public static SettingsManager setmgr;
  public static ModuleManager moduleManager;
  public static ClickGUI clickgui;
  public static CommandManager cmdmgr;
  
  public void StartClient()
  {
    Display.setTitle(this.ClientName + " " + this.ClientVersion);
    if (!directory.isDirectory()) {
      directory.mkdirs();
    }
    setmgr = new SettingsManager();
    moduleManager = new ModuleManager();
    keymgr = new KeyBindManager();
    keymgr.readKeybinds();
    keymgr.setKeyBinds();
    cmdmgr = new CommandManager();
    clickgui = new ClickGUI();

    
    new GuiHUD();
  }
  
  public static void ShutDown(){
	  ModuleFile.saveModulesState();
  }
}
