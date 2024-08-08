package me.napoleon.napoline.commands.commands;

import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.manager.CommandManager;
import me.napoleon.napoline.utils.player.PlayerUtil;

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
