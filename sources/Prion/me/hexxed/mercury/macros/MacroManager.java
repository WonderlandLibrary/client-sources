package me.hexxed.mercury.macros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.util.FileUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;



public class MacroManager
{
  public MacroManager() {}
  
  private static MacroManager manager = new MacroManager();
  
  private boolean[] keyStates = new boolean['Ā'];
  
  public static MacroManager getManager() {
    return manager; }
  

  private List<Macro> macros = new ArrayList();
  
  public List<Macro> getMacros() {
    return macros;
  }
  
  public void setupMacros() {
    List<Macro> macro = getMacrosFromFile();
    macros = macro;
  }
  
  public List<Macro> getMacrosFromFile() {
    List<Macro> macros = new ArrayList();
    List<String> file = FileUtils.readFile(Mercury.raidriarDir.getAbsolutePath() + "\\macros.txt");
    if (file.isEmpty()) return macros;
    for (String s : file) {
      String[] parts = s.split(":");
      String key = parts[0];
      List<String> commands = Macro.getCommandsFromString(parts[1]);
      macros.add(new Macro(Keyboard.getKeyIndex(key), commands));
    }
    return macros;
  }
  
  public void writeMacrosToFile() {
    List<String> newfile = new ArrayList();
    for (Macro m : getMacros()) {
      newfile.add(m.toString());
    }
    FileUtils.writeFile(Mercury.raidriarDir.getAbsolutePath() + "\\macros.txt", newfile);
  }
  
  public void addMacro(Macro macro) {
    for (Macro m : getMacros()) {
      if (m.getKeybind() == macro.getKeybind()) {
        List<String> newcommands = new ArrayList();
        newcommands.addAll(m.getCommands());
        newcommands.addAll(macro.getCommands());
        m.setCommands(newcommands);
        return;
      }
    }
    macros.add(macro);
    writeMacrosToFile();
  }
  
  public void removeMacro(Macro macro) {
    macros.remove(macro);
    writeMacrosToFile();
  }
  
  public void checkMacroKeys() {
    int key = -1;
    for (Macro m : getMacros())
    {
      if (((checkKey(m.getKeybind())) || (m.getKeybind() == key)) && (m.getKeybind() != 0)) {
        m.executeCommands();
        key = m.getKeybind();
      }
    }
  }
  
  private boolean checkKey(int i) {
    if (getMinecraftcurrentScreen != null) {
      return false;
    }
    
    if (Keyboard.isKeyDown(i) != keyStates[i]) {
      return keyStates[i] = keyStates[i] != 0 ? 0 : 1;
    }
    return false;
  }
}
