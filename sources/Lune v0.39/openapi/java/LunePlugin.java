package me.superskidder.lune.openapi.java;

import me.superskidder.lune.Lune;
import me.superskidder.lune.manager.CommandManager;
import me.superskidder.lune.manager.ModuleManager;

/**
 * @description: 需插件继承该类
 * @author: QianXia
 * @create: 2020/10/5 15:54
 **/
public class LunePlugin {
    public String pluginName;
    public String author;
    public float version;

    public LunePlugin(String pluginName, String author, float version){
        this.pluginName = pluginName;
        this.author = author;
        this.version = version;
    }

    public void onModuleManagerLoad(ModuleManager modManager){

    }

    public void onCommandManagerLoad(CommandManager commandManager){

    }

    public void onClientStart(Lune lune){

    }

    public void onClientStop(Lune lune){

    }
}
