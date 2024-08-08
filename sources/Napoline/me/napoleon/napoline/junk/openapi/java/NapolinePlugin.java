package me.napoleon.napoline.junk.openapi.java;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.manager.CommandManager;
import me.napoleon.napoline.manager.ModuleManager;

/**
 * @description: 需插件继承该类
 * @author: QianXia
 * @create: 2020/10/5 15:54
 **/
public class NapolinePlugin {
    public String pluginName;
    public String author;
    public float version;

    public NapolinePlugin(String pluginName, String author, float version){
        this.pluginName = pluginName;
        this.author = author;
        this.version = version;
    }

    public void onModuleManagerLoad(ModuleManager modManager){

    }

    public void onCommandManagerLoad(CommandManager commandManager){

    }

    public void onClientStart(Napoline lune){

    }

    public void onClientStop(Napoline lune){

    }
}
