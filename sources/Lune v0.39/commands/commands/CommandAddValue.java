package me.superskidder.lune.commands.commands;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.utils.player.PlayerUtil;
import me.superskidder.lune.values.Value;

import java.lang.reflect.Field;

/**
 * @description: 忘记addValue一个参数时可以使用该命令
 * @author: QianXia
 * @create: 2020/10/18 20:13
 **/
public class CommandAddValue extends Command {
    public CommandAddValue(){
        super("AddValue", true);
    }

    @Override
    public void run(String[] args) {
        if(args.length < 2){
            PlayerUtil.sendMessage(".addValue <ModuleName> <ValueName>");
            return;
        }
        Mod mod = ModuleManager.getModsByName(args[0]);
        if(mod == null){
            PlayerUtil.sendMessage("Module \"" + args[0] + "\" Not Found!");
            return;
        }
        try {
            Field valueField = mod.getClass().getDeclaredField(args[1]);
            Value<?> value = (Value<?>) valueField.get(mod);
            if(value == null){
                PlayerUtil.sendMessage("Value \"" + args[1] + "\" Not Found!");
                return;
            }
            mod.addValues(value);
            PlayerUtil.sendMessage("Value Added Successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            PlayerUtil.sendMessage("Failed To Added Value!");
        }
    }
}
