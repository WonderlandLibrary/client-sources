package me.hexxed.mercury;

import com.memetix.mst.MicrosoftTranslatorAPI;
import java.io.File;
import me.hexxed.mercury.macros.MacroManager;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.overlay.OverlayManager;
import me.hexxed.mercury.util.FileUtils;
import me.hexxed.mercury.util.Keybinds;
import net.minecraft.client.Minecraft;
import org.darkstorm.minecraft.gui.MercuryGuiManager;
import org.darkstorm.minecraft.gui.theme.mercury.MercuryTheme;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;




public class Mercury
{
  public Mercury() {}
  
  private static Mercury MercuryInstance = new Mercury();
  
  public static Mercury getInstance() {
    return MercuryInstance; }
  

  public static File raidriarDir;
  
  public static Minecraft mc = Minecraft.getMinecraft();
  private static ModuleManager modulemanager;
  private static MercuryGuiManager guimanager;
  private static GuiManagerDisplayScreen gui;
  
  public void initClient() {
    try { modulemanager = new ModuleManager();
      guimanager = new MercuryGuiManager();
      getGuiManager().setTheme(new MercuryTheme());
      getGuiManager().setup();
      MicrosoftTranslatorAPI.setClientId("sCheese-MCClient");
      MicrosoftTranslatorAPI.setClientSecret("4UaoXOyXaPaogjj3L0VBglXny+BxQpXYsq1DMYBtSuU=");
      mcgameSettings.gammaSetting = 0.0F;
      raidriarDir = new File(getMinecraftmcDataDir + File.separator + getName());
      File guiDir = new File(getMinecraftmcDataDir + File.separator + getName() + File.separator + "gui");
      if (!raidriarDir.exists()) {
        raidriarDir.mkdirs();
      }
      if (!guiDir.exists()) {
        guiDir.mkdirs();
      }
      FileUtils.createFile("friends");
      FileUtils.createFile("alts");
      FileUtils.createFile("keybinds");
      FileUtils.createFile("spam");
      FileUtils.createFile("macros");
      FileUtils.createFile("startupmodules");
      FileUtils.createFile("gui\\default");
      OverlayManager.getManager().setup();
      Keybinds.setupBinds();
      Keybinds.bindKeys();
      FileUtils.enableStartupMods();
      ModuleManager.getModByName("Nameprotect").setStateSilent(true);
      MacroManager.getManager().setupMacros();
    }
    catch (Exception localException) {}
  }
  
  public static ModuleManager getModuleManager()
  {
    if (modulemanager == null) modulemanager = new ModuleManager();
    return modulemanager;
  }
  
  public static MercuryGuiManager getGuiManager() {
    if (guimanager == null) guimanager = new MercuryGuiManager();
    return guimanager;
  }
  
  public GuiManagerDisplayScreen getGui() {
    if (gui == null) {
      gui = new GuiManagerDisplayScreen(getGuiManager());
    }
    return gui;
  }
  
  public static Minecraft getMinecraft() {
    if (mc == null) mc = Minecraft.getMinecraft();
    return mc;
  }
  
  public String getName()
  {
    return "Prion";
  }
  
  public String getVersion()
  {
    return "x";
  }
  
  public String getAuthor()
  {
    return "HorizonCode";
  }
  
  public String getShortName()
  {
    return "Prion";
  }
}
