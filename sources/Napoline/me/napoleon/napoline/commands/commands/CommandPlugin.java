package me.napoleon.napoline.commands.commands;

import java.util.List;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.junk.openapi.java.NapolinePlugin;
import me.napoleon.napoline.junk.openapi.script.NapolineScript;
import me.napoleon.napoline.manager.CommandManager;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.utils.player.PlayerUtil;

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
            List<NapolinePlugin> plugins = Napoline.pluginManager.plugins;
            List<NapolineScript> scripts = Napoline.scriptManager.scripts;

            if(!plugins.isEmpty()) {
                PlayerUtil.sendMessage("-Name-------Version------Author-");
                for (NapolinePlugin plugin : plugins) {
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

                for (NapolineScript script : scripts) {
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

            Napoline.pluginManager.plugins.clear();
            Napoline.pluginManager.urlCL.clear();

            // Reload
            Napoline.pluginManager.loadPlugins(true);
            Napoline.scriptManager.loadScripts();
            PlayerUtil.sendMessage("Reload Successfully!");
        }
    }
}
