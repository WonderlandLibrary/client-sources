package me.napoleon.napoline.utils.json;

import java.util.List;

import me.napoleon.napoline.junk.values.Value;

public class ModConfig {
    String name;
    Boolean enable;
    List<Value> values;
    int bind;

    public ModConfig(String name, boolean enable, List<Value> values,int bind){
        this.name = name;
        this.enable = enable;
        this.values = values;
        this.bind = bind;
    }
}
