package me.protocol_client.files.allfiles;

import java.util.ArrayList;
import java.util.List;

import me.protocol_client.Protocol;
import me.protocol_client.files.FileUtils;
import me.protocol_client.module.Module;

import org.lwjgl.input.Keyboard;

public class Keybinds
{
  public static void bindKeys()
  {
    List<String> file = FileUtils.readFile(Protocol.protocolDir + "\\keybinds.txt");
    for (String s : file)
    {
      String name = s.split(":")[0];
      Module mod = Module.getModbyAlias(name);
      if (mod != null)
      {
        Integer bind = Integer.valueOf(Keyboard.getKeyIndex(s.split(":")[1]));
        mod.setKeyCode(bind.intValue());
      }
    }
  }
  
  public static void setupBinds()
  {
    List<String> file = FileUtils.readFile(Protocol.protocolDir + "\\keybinds.txt");
    List<String> newfile = new ArrayList();
    for (Module m : Protocol.getModules())
    {
      boolean exists = false;
      for (String s : file)
      {
        String modname = s.split(":")[0];
        if (modname.equalsIgnoreCase(m.getAlias()))
        {
          exists = true;
          newfile.add(s);
        }
      }
      if (!exists) {
    	  if(m.getAlias().equalsIgnoreCase("clickgui")){
    		  return;
    	  }
        newfile.add(m.getAlias() + ":" + Keyboard.getKeyName(m.getKeyCode()));
      }
    }
    FileUtils.writeFile(Protocol.protocolDir + "\\keybinds.txt", newfile);
  }
  
  public static void resetBinds()
  {
    List<String> file = FileUtils.readFile(Protocol.protocolDir + "\\keybinds.txt");
    List<String> newfile = new ArrayList();
    for (Module m : Protocol.getModules()) {
      newfile.add(m.getAlias() + ":" + Keyboard.getKeyName(m.getKeyCode()));
    }
    FileUtils.writeFile(Protocol.protocolDir + "\\keybinds.txt", newfile);
  }
  
  public static void bindKey(Module mod, int key)
  {
    List<String> file = FileUtils.readFile(Protocol.protocolDir + "\\keybinds.txt");
    List<String> newfile = new ArrayList();
    boolean exists = false;
    for (String s : file)
    {
      String modname = s.split(":")[0];
      if (modname.equalsIgnoreCase(mod.getAlias()))
      {
        exists = true;
        s = mod.getAlias() + ":" + Keyboard.getKeyName(key);
      }
      newfile.add(s);
    }
    if (!exists) {
      newfile.add(mod.getAlias() + ":" + Keyboard.getKeyName(key));
    }
    FileUtils.writeFile(Protocol.protocolDir + "\\keybinds.txt", newfile);
    bindKeys();
  }
}
