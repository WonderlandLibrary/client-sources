package me.superskidder.lune.commands.commands;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.utils.player.PlayerUtil;

/**
 * @description: 使用命令打开一个功能
 * @author: QianXia
 * @create: 2020/9/12 19-50
 **/
public class CommandToggle extends Command {
    public CommandToggle(){
        super("Toggle", "t");
    }

    @Override
    public void run(String[] args) {
        if(args.length == 0){
            PlayerUtil.sendMessage(".Toggle <功能名称>");
            return;
        }
        String moduleName = args[0];
        Mod mod = ModuleManager.getModsByName(moduleName);
        if(mod == null){
            PlayerUtil.sendMessage("Module \"" + args[0] + "\" Not Found!");
            return;
        }
        mod.toggle();
        PlayerUtil.sendMessage(mod.getName() + " was toggled");
    }
}
