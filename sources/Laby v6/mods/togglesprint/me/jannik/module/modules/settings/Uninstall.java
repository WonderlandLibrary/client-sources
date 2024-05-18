package mods.togglesprint.me.jannik.module.modules.settings;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import mods.togglesprint.me.jannik.Jannik;
import mods.togglesprint.me.jannik.files.Files;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.module.ModuleManager;
import net.minecraft.client.Minecraft;

public class Uninstall
  extends Module
{
  public Uninstall()
  {
    super("Uninstall", Category.SETTINGS);
  }
  
  public void onEnabled()
  {
    int uninstall = JOptionPane.showConfirmDialog(null, "Uninstall ?");
    if (uninstall == 0)
    {
      Jannik.getModuleManager().getModuleByClass(Uninstall.class).setEnabled(false);
      startUninstalling();
    }
    else
    {
      Jannik.getModuleManager().getModuleByClass(Uninstall.class).setEnabled(false);
    }
  }
  
  private void startUninstalling()
  {
    try
    {
      mc.shutdown();
      
      File folder = new File(Files.folderFile);
      if (folder.isDirectory())
      {
        File[] arrayOfFile;
        int j = (arrayOfFile = folder.listFiles()).length;
        for (int i = 0; i < j; i++)
        {
          File folderFiles = arrayOfFile[i];
          folderFiles.delete();
        }
      }
      if (folder.exists()) {
        folder.delete();
      }
      Runtime.getRuntime().exec("cmd /c start %AppData%//.minecraft//LabyMod//Updater-1.8.8.jar");
    }
    catch (IOException localIOException) {}
  }
}
