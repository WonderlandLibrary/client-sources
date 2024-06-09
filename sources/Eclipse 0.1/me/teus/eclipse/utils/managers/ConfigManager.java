package me.teus.eclipse.utils.managers;

import me.teus.eclipse.Client;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.value.Value;
import me.teus.eclipse.modules.value.impl.BooleanValue;
import me.teus.eclipse.modules.value.impl.ModeValue;
import me.teus.eclipse.modules.value.impl.NumberValue;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;

public class ConfigManager {

    private File dataFile;

    private File dir;

    public File folder;

    public File configDir;

    public void init(){
        this.folder = new File((Minecraft.getMinecraft()).mcDataDir, Client.getInstance().CLIENT_NAME);
        if (!this.folder.exists())
            this.folder.mkdir();
        this.configDir = new File(this.folder + "/configs");
        if (!this.configDir.exists())
            this.configDir.mkdir();
    }

    public void getList(){
        if(!configDir.exists())
            return;
        Client.getInstance().addChatMessage("Config List:");

        if(configDir.listFiles() == null || configDir.listFiles().length < 1){
            Client.getInstance().addChatMessage("No Configs found.");
            return;
        }else{
            for(final File file : configDir.listFiles()){
                Client.getInstance().addChatMessage("  " + file.getName().replace(".file", ""));
            }
        }

    }

    public void save(String name, boolean configFolder){
        this.dir = new File(configFolder ? String.valueOf(configDir) : String.valueOf(folder));
        if (!this.dir.exists())
            this.dir.mkdir();
        this.dataFile = new File(this.dir, name + ".file");
        if (!this.dataFile.exists()) {
            try {
                this.dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> toSave = new ArrayList<>();
        for (Module m : ModuleManager.modules){
            toSave.add("Module_" + m.getName() + "_" + m.toggled + "_" + m.keybind);
            for(Value v : m.values){
                if(v instanceof NumberValue){
                    NumberValue value = (NumberValue)v;
                    toSave.add("Number_" + m.getName() + "_" + value.name + "_" + value.getValue());
                    continue;
                }
                if(v instanceof BooleanValue){
                    toSave.add("Boolean_" + m.getName() + "_" + v.name + "_" + ((BooleanValue) v).enabled);
                    continue;
                }
                if(v instanceof ModeValue){
                    toSave.add("Mode_" + m.getName() + "_" + v.name + "_" + ((ModeValue) v).getMode());
                }
            }
        }
        try {
            PrintWriter pw = new PrintWriter(this.dataFile);
            for (String str : toSave)
                pw.println(str);
            pw.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public void load(String name, boolean configFolder){
        if(!configDir.exists())
            return;
        this.dir = new File(configFolder ? String.valueOf(configDir) : String.valueOf(folder));
        if (!this.dir.exists())
            this.dir.mkdir();
        this.dataFile = new File(this.dir, name + ".file");
        if(!dataFile.exists()) {
            System.out.println("Can't find");
            if(configFolder)
                Client.getInstance().addChatMessage("Failed");
            return;
        }
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for (String s : lines) {
                String[] args = s.split("_");
                if (s.toLowerCase().startsWith("module_")) {
                    for (Module m : ModuleManager.modules) {
                        if (m.getName().equalsIgnoreCase(args[1])) {
                            boolean shouldEnable = Boolean.parseBoolean(args[2]);
                            if(!configFolder)
                                m.setKey(Integer.parseInt(args[3]));
                            m.toggle2(shouldEnable);
                        }
                    }
                }
                if (s.toLowerCase().startsWith("number_")) {
                    for (Module m : ModuleManager.modules) {
                        if (m.getName().equalsIgnoreCase(args[1])) {
                            for (Value v : m.values) {
                                if (!(v instanceof NumberValue))
                                    continue;
                                if (!v.name.equalsIgnoreCase(args[2]))
                                    continue;
                                NumberValue setting2 = (NumberValue) v;
                                setting2.setValue(Double.parseDouble(args[3]));
                            }
                        }
                    }
                }
                if (s.toLowerCase().startsWith("boolean_")) {
                    for (Module m : ModuleManager.modules) {
                        if (m.getName().equalsIgnoreCase(args[1])) {
                            for (Value v : m.values) {
                                if (!(v instanceof BooleanValue))
                                    continue;
                                if (!v.name.equalsIgnoreCase(args[2]))
                                    continue;
                                BooleanValue setting3 = (BooleanValue) v;
                                setting3.setEnabled(Boolean.parseBoolean(args[3]));
                            }
                        }
                    }
                }
                if (s.toLowerCase().startsWith("mode_")) {
                    for (Module m : ModuleManager.modules) {
                        if (m.getName().equalsIgnoreCase(args[1])) {
                            for (Value v : m.values) {
                                if (!(v instanceof ModeValue))
                                    continue;
                                for (String str : ((ModeValue) v).modes) {
                                    if (v.name.equalsIgnoreCase(args[2]) && args[3].equalsIgnoreCase(str)) {
                                        ModeValue setting4 = (ModeValue) v;
                                        setting4.setSelected(args[3]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("Loaded");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
