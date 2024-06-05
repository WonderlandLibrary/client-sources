package me.superskidder.lune.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.superskidder.lune.utils.client.FileUtil;
import me.superskidder.lune.Lune;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.values.Value;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.Minecraft;
import net.minecraft.world.gen.structure.StructureStrongholdPieces.ChestCorridor;

import java.io.*;

public class JsonUtil {
    static Modules modules = new Modules();
    
    public static void saveConfig() {
    	saveConfig("");
    }
    
    public static void saveConfig(String prefix) {
        Gson gson = new GsonBuilder().setVersion(1.1).setPrettyPrinting().create();
        modules.init();
        String f = gson.toJson(modules);

        File dir = new File(Lune.luneConfigFolder, (prefix == "" ? "" : prefix + "\\"));
        
        if(!dir.exists()) {
        	dir.mkdir();
        }
        File file = new File(dir, "config.json");
        
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, false);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write(f);
            bf.flush();
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Saved Config");
    }

    public static void load() {
    	load("");
    }
    
    public static void load(String prefix) {
        Gson gson = new Gson();
        
        File dir = new File(Lune.luneConfigFolder, (prefix == "" ? "" : prefix + "\\"));
        
        if(!dir.exists()) {
        	dir.mkdir();
        }
        File file = new File(dir, "config.json");
        
        Modules mods = null;
        mods = gson.fromJson(FileUtil.readFile(dir.getAbsolutePath(), "config.json"), Modules.class);

        for (ModConfig modConfig: mods.modConfigs){
            Mod m = ModuleManager.getModsByName(modConfig.name);
            if(modConfig.enable != m.getState()) {
                m.setStage(modConfig.enable);
            }
            m.setKey(modConfig.bind);

            for (Value value:modConfig.values) {
                if (value instanceof Bool) {
                    for (Value v : m.values){
                        if(v.getName() == value.getName()){
                            v.setValue(value.getValue());
                        }
                    }
                }
                if (value instanceof Num) {
                    for (Value v : m.values){
                        if(v.getName() == value.getName()){
                            v.setValue(value.getValue());
                        }
                    }
                }
                if (value instanceof Mode) {
                    for (Value v : m.values){
                        if(v.getName() == value.getName()){
                            v.setValue(value.getValue());
                        }
                    }
                }
            }
        }
        System.out.println("Loaded Config");

    }

}
