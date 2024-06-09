package me.protocol_client.files.allfiles;

import java.util.ArrayList;
import java.util.List;

import me.protocol_client.Protocol;
import me.protocol_client.files.FileUtils;
import me.protocol_client.module.Module;

import org.lwjgl.input.Keyboard;

public class ToggledMods
{
  public static void bindKeys()
  {
    List<String> file = FileUtils.readFile(Protocol.protocolDir + "\\toggledmods.txt");
    for (String s : file)
    {
      String name = s.split(":")[0];
      Module mod = Module.getModbyAlias(name);
      if (mod != null)
      {
        boolean toggle = Boolean.valueOf(s.split(":")[1]);
        if(toggle && !mod.isToggled()){
        	mod.setToggled(true);
        	mod.onEnable();
        }
      }
    }
  }
  
  public static void setupBinds()
  {
    List<String> file = FileUtils.readFile(Protocol.protocolDir + "\\toggledmods.txt");
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
    	  if(m.getAlias().equalsIgnoreCase("blink")){
    		  return;
    	  }
        newfile.add(m.getAlias() + ":" + m.isToggled());
      }
    }
    FileUtils.writeFile(Protocol.protocolDir + "\\toggledmods.txt", newfile);
  }
  
  public static void resetBinds()
  {
    List<String> file = FileUtils.readFile(Protocol.protocolDir + "\\toggledmods.txt");
    List<String> newfile = new ArrayList();
    for (Module m : Protocol.getModules()) {
      newfile.add(m.getAlias() + ":" + m.isToggled());
    }
    FileUtils.writeFile(Protocol.protocolDir + "\\toggledmods.txt", newfile);
  }
  
  public static void bindKey(Module mod, boolean toggle)
  {
    List<String> file = FileUtils.readFile(Protocol.protocolDir + "\\toggledmods.txt");
    List<String> newfile = new ArrayList();
    boolean exists = false;
    for (String s : file)
    {
      String modname = s.split(":")[0];
      if (modname.equalsIgnoreCase(mod.getAlias()))
      {
        exists = true;
        s = mod.getAlias() + ":" + toggle;
      }
      newfile.add(s);
    }
    if (!exists) {
      newfile.add(mod.getAlias() + ":" + toggle);
    }
    FileUtils.writeFile(Protocol.protocolDir + "\\toggledmods.txt", newfile);
    bindKeys();
  }
}
