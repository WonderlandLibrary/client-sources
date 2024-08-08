package me.napoleon.napoline.commands.commands;

import java.lang.reflect.Field;

import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.junk.values.Value;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.utils.player.PlayerUtil;

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
