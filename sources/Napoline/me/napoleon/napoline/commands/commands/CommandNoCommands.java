package me.napoleon.napoline.commands.commands;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.utils.player.PlayerUtil;

/**
 * @description:
 * @author: Qian_Xia
 * @create: 2020-08-23 21:05
 **/
public class CommandNoCommands extends Command {
    public CommandNoCommands() {
        super("NoCommands");
    }
 
    @Override
    public void run(String[] args) {
        Napoline.noCommands = !Napoline.noCommands;
        PlayerUtil.sendMessage("NoCommands was set to " + Napoline.noCommands);
    }
}
