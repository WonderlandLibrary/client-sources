package me.superskidder.lune.utils.json;

import me.superskidder.lune.values.Value;

import java.util.List;

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
