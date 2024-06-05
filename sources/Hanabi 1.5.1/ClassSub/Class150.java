package ClassSub;

import net.minecraft.client.*;
import cn.Hanabi.*;
import org.lwjgl.input.*;
import cn.Hanabi.value.*;
import java.util.*;
import java.io.*;
import cn.Hanabi.modules.*;

public class Class150
{
    private String fileDir;
    private Minecraft mc;
    
    
    public Class150() {
        this.mc = Minecraft.getMinecraft();
        this.fileDir = this.mc.mcDataDir.getAbsolutePath() + "/" + "Hanabi";
        final File file = new File(this.fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
    public void save() throws Exception {
        final File file = new File(this.fileDir + "/keys.txt");
        final File file2 = new File(this.fileDir + "/mods.txt");
        final File file3 = new File(this.fileDir + "/values.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            final PrintWriter printWriter = new PrintWriter(file);
            for (final Mod mod : Hanabi.INSTANCE.moduleManager.getModules()) {
                final String s = (mod.getKeybind() < 0) ? "None" : Keyboard.getKeyName(mod.getKeybind());
                printWriter.write(mod.getName() + ":" + s + "\n");
                System.out.println("Saving Keys:" + mod.getName() + ":" + s);
            }
            printWriter.close();
            if (!file2.exists()) {
                file2.createNewFile();
            }
            final PrintWriter printWriter2 = new PrintWriter(file2);
            for (final Mod mod2 : Hanabi.INSTANCE.moduleManager.getModules()) {
                printWriter2.print(mod2.getName() + ":" + mod2.isEnabled() + "\n");
                System.out.println("Saving Modules:" + mod2.getName() + ":" + mod2.isEnabled());
            }
            printWriter2.close();
            if (!file3.exists()) {
                file3.createNewFile();
            }
            final PrintWriter printWriter3 = new PrintWriter(file3);
            for (final Value<Object> value : Value.list) {
                final String valueName = value.getValueName();
                if (value.isValueBoolean) {
                    printWriter3.print(valueName + ":b:" + value.getValueState() + "\n");
                }
                else if (value.isValueDouble) {
                    printWriter3.print(valueName + ":d:" + value.getValueState() + "\n");
                }
                else {
                    if (!value.isValueMode) {
                        continue;
                    }
                    printWriter3.print(valueName + ":s:" + value.getModeTitle() + ":" + value.getCurrentMode() + "\n");
                    System.out.println("Saving Values:" + valueName + ":s:" + value.getModeTitle() + ":" + value.getCurrentMode());
                }
            }
            printWriter3.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void load() {
        final File file = new File(this.fileDir + "/keys.txt");
        final File file2 = new File(this.fileDir + "/mods.txt");
        final File file3 = new File(this.fileDir + "/values.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                String line;
                while ((line = new BufferedReader(new FileReader(file)).readLine()) != null) {
                    System.out.println("Loading Keys:" + line);
                    if (!line.contains(":")) {
                        continue;
                    }
                    final String[] array = line.split(":");
                    final ModManager moduleManager = Hanabi.INSTANCE.moduleManager;
                    final Mod module = ModManager.getModule(array[0]);
                    final int keyIndex = Keyboard.getKeyIndex(array[1]);
                    if (module == null) {
                        continue;
                    }
                    if (keyIndex == -1) {
                        continue;
                    }
                    module.setKeybind(keyIndex);
                }
            }
            if (!file2.exists()) {
                file2.createNewFile();
            }
            else {
                String line2;
                while ((line2 = new BufferedReader(new FileReader(file2)).readLine()) != null) {
                    System.out.println("Loading Modules:" + line2);
                    if (!line2.contains(":")) {
                        continue;
                    }
                    final String[] array2 = line2.split(":");
                    final ModManager moduleManager2 = Hanabi.INSTANCE.moduleManager;
                    final Mod module2 = ModManager.getModule(array2[0]);
                    final boolean boolean1 = Boolean.parseBoolean(array2[1]);
                    if (module2 == null) {
                        continue;
                    }
                    try {
                        if (!module2.getName().equals("Fly") && !module2.getName().equals("Blink") && !module2.getName().equals("Scaffold")) {
                            module2.setState(boolean1, false);
                        }
                        else {
                            module2.setState(false, false);
                        }
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if (!file3.exists()) {
                file3.createNewFile();
            }
            else {
                String line3;
                while ((line3 = new BufferedReader(new FileReader(file3)).readLine()) != null) {
                    System.out.println("Loading Values:" + line3);
                    if (!line3.contains(":")) {
                        continue;
                    }
                    final String[] array3 = line3.split(":");
                    for (final Value<Boolean> value : Value.list) {
                        if (!array3[0].equalsIgnoreCase(value.getValueName())) {
                            continue;
                        }
                        if (value.isValueBoolean && array3[1].equalsIgnoreCase("b")) {
                            value.setValueState(Boolean.parseBoolean(array3[2]));
                        }
                        else if (value.isValueDouble && array3[1].equalsIgnoreCase("d")) {
                            value.setValueState((Boolean)(Object)Double.parseDouble(array3[2]));
                        }
                        else {
                            if (!value.isValueMode || !array3[1].equalsIgnoreCase("s")) {
                                continue;
                            }
                            if (!array3[2].equalsIgnoreCase(value.getModeTitle())) {
                                continue;
                            }
                            value.setCurrentMode(Integer.parseInt(array3[3]));
                        }
                    }
                }
            }
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }
}
