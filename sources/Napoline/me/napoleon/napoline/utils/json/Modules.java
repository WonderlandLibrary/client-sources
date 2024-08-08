package me.napoleon.napoline.utils.json;

import java.util.ArrayList;
import java.util.List;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.modules.Mod;

public class Modules {
    List<ModConfig> modConfigs = new ArrayList<>();

    public void init(){
        for (Mod m : Napoline.moduleManager.modList){
            modConfigs.add(new ModConfig(m.getName(),m.getState(),m.getValues(),m.key));
        }
    }
}
