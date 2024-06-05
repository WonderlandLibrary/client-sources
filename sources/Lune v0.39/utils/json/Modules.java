package me.superskidder.lune.utils.json;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;

import java.util.ArrayList;
import java.util.List;

public class Modules {
    List<ModConfig> modConfigs = new ArrayList<>();

    public void init(){
        for (Mod m : Lune.moduleManager.modList){
            modConfigs.add(new ModConfig(m.getName(),m.getState(),m.getValues(),m.key));
        }
    }
}
