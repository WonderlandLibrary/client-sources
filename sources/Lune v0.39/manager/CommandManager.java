package me.superskidder.lune.manager;

import me.superskidder.lune.Lune;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.commands.commands.*;
import me.superskidder.lune.utils.client.DevUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 命令管理者
 * @author: Qian_Xia
 * @create: 2020-08-23 20:45
 **/
public class CommandManager {
    public static List<Command> commands = new ArrayList<>();
    public static Map<Command, Object> pluginCommands = new HashMap<>();
    public static Map<Command, Object> disabledPluginCommands = new HashMap<>();

    public CommandManager() {
        this.addCommand(new CommandBind());
        this.addCommand(new CommandBinds());
        this.addCommand(new CommandNoCommands());
        this.addCommand(new CommandList());
        this.addCommand(new CommandToggle());
        this.addCommand(new CommandTitle());
        this.addCommand(new CommandConfig());
        this.addCommand(new CommandIRC());
        this.addCommand(new CommandSaveConfig());
        this.addCommand(new CommandPlugin());
        this.addCommand(new CommandAddValue());
        this.addCommand(new CommandI18n());
        this.addCommand(new CommandMotionBlur());
        this.addCommand(new CommandHelp());
        this.addCommand(new CommandTp());
        this.addCommand(new CommandHuji());
        this.addCommand(new CommandReload());
        this.addCommand(new CommandCustomGui());
       // this.addCommand(new CommandMusic());
        
        Lune.pluginManager.onCommandManagerLoad(this);

        int number = this.getDevCommandSum();
        System.out.println(number == 0 ? "Loaded " + commands.size() + " Commands!" :
                "Welcome Dev:" + DevUtils.getDevName() + "\n" +
                        "Loaded " + commands.size() + " Commands!" + "\n" +
                        number + " Dev Commands Inside!");
    }

    /**
     * 获取所有的Dev命令数量
     *
     * @return
     */
    private int getDevCommandSum() {
        return (int) commands.stream().filter(Command::isDev).count();
    }

    /**
     * 内置Dev检测
     *
     * @param cmd
     */
    private void addCommand(Command cmd) {
        if (cmd.isDev()) {
            if (!DevUtils.isDev()) {
                return;
            }
        }
        commands.add(cmd);
    }

    /**
     * Functions for plugins
     * Lune developers NOT to call this function
     *
     * @param cmd The command
     * @param plugin The plugin
     */
    public void addCMD(Command cmd, Object plugin) {
        pluginCommands.put(cmd, plugin);
        commands.add(cmd);
    }

    /**
     * Get a command object from the name
     *
     * @param name The name of the command
     * @return May be null
     */
    public static Command getCommandByName(String name) {
        for (Command cmd : commands) {
            if (cmd == null) {
                continue;
            }
            String[] names = cmd.getNames();
            for (String myName : names) {
                if (myName.equalsIgnoreCase(name)) {
                    return cmd;
                }
            }
        }
        return null;
    }

}