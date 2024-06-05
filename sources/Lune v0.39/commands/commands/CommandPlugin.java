package me.superskidder.lune.commands.commands;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.manager.CommandManager;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.openapi.java.LunePlugin;
import me.superskidder.lune.openapi.script.LuneScript;
import me.superskidder.lune.utils.player.PlayerUtil;

import java.util.List;

/**
 * @description: 功能相关的
 * @author: QianXia
 * @create: 2020/10/5 18:54
 **/
public class CommandPlugin extends Command {
    public CommandPlugin() {
        super("Plugin", "mod");
    }

    @Override
    public void run(String[] args) {
        if(args.length < 1){
            PlayerUtil.sendMessage(".plugin list 输出插件列表");
            PlayerUtil.sendMessage(".plugin reload 重载插件");
            return;
        }
        if("list".equalsIgnoreCase(args[0])){
            List<LunePlugin> plugins = Lune.pluginManager.plugins;
            List<LuneScript> scripts = Lune.scriptManager.scripts;

            if(!plugins.isEmpty()) {
                PlayerUtil.sendMessage("-Name-------Version------Author-");
                for (LunePlugin plugin : plugins) {
                    int spaceTimes = plugin.pluginName.length() <= 5 ? plugin.pluginName.length() + 4 : plugin.pluginName.length();
                    StringBuilder msg = new StringBuilder(plugin.pluginName);
                    for (int j = 0; j < spaceTimes; j++) {
                        msg.append(" ");
                    }
                    msg.append(plugin.version);
                    for (int j = 0; j < spaceTimes; j++) {
                        msg.append(" ");
                    }
                    msg.append(plugin.author);
                    PlayerUtil.sendMessage(msg.toString());
                }
            }
            if(!scripts.isEmpty()) {
                PlayerUtil.sendMessage("-------------Script-------------");

                for (LuneScript script : scripts) {
                    int spaceTimes = script.name.length() <= 5 ? script.name.length() + 4 : script.name.length();
                    StringBuilder msg = new StringBuilder(script.name + (script.scriptCommand != null && !script.name.endsWith("Command") ? "Command" : ""));
                    for (int j = 0; j < spaceTimes; j++) {
                        msg.append(" ");
                    }
                    msg.append(script.version);
                    for (int j = 0; j < spaceTimes; j++) {
                        msg.append(" ");
                    }
                    msg.append(script.author);
                    PlayerUtil.sendMessage(msg.toString());
                }

                PlayerUtil.sendMessage("--------------------------------");
            }
            if (plugins.isEmpty() && scripts.isEmpty()) {
                PlayerUtil.sendMessage("Nothing");
            }
        }
        if ("reload".equalsIgnoreCase(args[0])) {
            // Clean Module Manager
            for (Mod mod : ModuleManager.pluginModsList.keySet()) {
                mod.setStageWithoutNotification(false);
                ModuleManager.modList.remove(mod);
            }
            ModuleManager.pluginModsList.clear();

            // Clean Command Manager
            for(Command cmd : CommandManager.pluginCommands.keySet()){
                CommandManager.commands.remove(cmd);
            }
            CommandManager.pluginCommands.clear();

            Lune.pluginManager.plugins.clear();
            Lune.pluginManager.urlCL.clear();

            // Reload
            Lune.pluginManager.loadPlugins(true);
            Lune.scriptManager.loadScripts();
            PlayerUtil.sendMessage("Reload Successfully!");
        }
    }
}
