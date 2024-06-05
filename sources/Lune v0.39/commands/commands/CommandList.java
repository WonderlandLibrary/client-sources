package me.superskidder.lune.commands.commands;

import me.superskidder.lune.commands.Command;
import me.superskidder.lune.manager.CommandManager;
import me.superskidder.lune.utils.player.PlayerUtil;

/**
 * @description: 枚举所有的命令
 * @author: QianXia
 * @create: 2020/9/10 19-11
 **/
public class CommandList extends Command {
    public CommandList(){
        super("List");
    }

    @Override
    public void run(String[] args) {
        for(Command cmd : CommandManager.commands){
            String commandName = cmd.getName();
            PlayerUtil.sendMessage(commandName);
        }
    }
}
