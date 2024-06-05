package me.superskidder.lune.commands.commands;

import me.superskidder.lune.Lune;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.utils.player.PlayerUtil;

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
        Lune.noCommands = !Lune.noCommands;
        PlayerUtil.sendMessage("NoCommands was set to " + Lune.noCommands);
    }
}
