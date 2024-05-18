package me.hexxed.mercury.util;

import java.io.File;
import java.util.List;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.Module;
import org.lwjgl.input.Keyboard;

public class Keybinds
{
  public Keybinds() {}
  
  public static void bindKeys()
  {
    List<String> file = FileUtils.readFile(Mercury.raidriarDir.getAbsolutePath() + "\\keybinds.txt");
    for (String s : file) {
      String name = s.split(":")[0];
      Module mod = me.hexxed.mercury.modulebase.ModuleManager.getModByName(name);
      if (mod != null) {
        Integer bind = Integer.valueOf(Keyboard.getKeyIndex(s.split(":")[1]));
        mod.setKeybind(bind.intValue());
      }
    }
  }
  
  public static void setupBinds() { List<String> file = FileUtils.readFile(Mercury.raidriarDir.getAbsolutePath() + "\\keybinds.txt");
    List<String> newfile = new java.util.ArrayList();
    for (Module m : me.hexxed.mercury.modulebase.ModuleManager.moduleList) {
      boolean exists = false;
      for (String s : file) {
        String modname = s.split(":")[0];
        if (modname.equalsIgnoreCase(m.getModuleName())) {
          exists = true;
          newfile.add(s);
        }
      }
      if (!exists) {
        newfile.add(m.getModuleName() + ":" + Keyboard.getKeyName(m.getKeybind()));
      }
    }
    FileUtils.writeFile(Mercury.raidriarDir.getAbsolutePath() + "\\keybinds.txt", newfile);
  }
  
  public static void resetBinds() {
    List<String> file = FileUtils.readFile(Mercury.raidriarDir.getAbsolutePath() + "\\keybinds.txt");
    List<String> newfile = new java.util.ArrayList();
    for (Module m : me.hexxed.mercury.modulebase.ModuleManager.moduleList) {
      newfile.add(m.getModuleName() + ":" + Keyboard.getKeyName(m.getKeybind()));
    }
    FileUtils.writeFile(Mercury.raidriarDir.getAbsolutePath() + "\\keybinds.txt", newfile);
  }
  
  public static void bindKey(Module mod, int key) {
    List<String> file = FileUtils.readFile(Mercury.raidriarDir.getAbsolutePath() + "\\keybinds.txt");
    List<String> newfile = new java.util.ArrayList();
    boolean exists = false;
    for (String s : file) {
      String modname = s.split(":")[0];
      if (modname.equalsIgnoreCase(mod.getModuleName())) {
        exists = true;
        s = mod.getModuleName() + ":" + Keyboard.getKeyName(key);
      }
      newfile.add(s);
    }
    if (!exists) {
      newfile.add(mod.getModuleName() + ":" + Keyboard.getKeyName(key));
    }
    FileUtils.writeFile(Mercury.raidriarDir.getAbsolutePath() + "\\keybinds.txt", newfile);
    bindKeys();
  }
}
