package me.hexxed.mercury.macros;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;

public class Macro
{
  private List<String> commands;
  private int key;
  
  public Macro(int key, List<String> commands)
  {
    this.key = key;
    this.commands = commands;
  }
  
  public List<String> getCommands() {
    return commands;
  }
  
  public void setCommands(List<String> newcommands) {
    commands = newcommands;
  }
  
  public int getKeybind() {
    return key;
  }
  
  public String getKeyName() {
    return Keyboard.getKeyName(key);
  }
  
  public void executeCommands() {
    for (String cmd : getCommands()) {
      getMinecraftthePlayer.sendChatMessage("." + cmd);
    }
  }
  
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    for (String cmd : getCommands()) {
      sb.append(";" + cmd);
    }
    String commands = sb.toString().substring(1);
    return Keyboard.getKeyName(getKeybind()) + ":" + commands;
  }
  
  public static List<String> getCommandsFromString(String s)
  {
    if (!s.contains(";")) {
      List<String> cmd = new ArrayList();
      cmd.add(s);
      return cmd;
    }
    List<String> commands = Arrays.asList(s.split(";"));
    return commands;
  }
}
