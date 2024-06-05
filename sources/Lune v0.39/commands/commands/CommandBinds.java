package me.superskidder.lune.commands.commands;

import me.superskidder.lune.commands.Command;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.utils.player.PlayerUtil;
import org.lwjgl.input.Keyboard;

/**
 * @description: 输出所有绑定的按键
 * @author: QianXia
 * @create: 2021/06/25 20:06
 **/
public class CommandBinds extends Command {
    public CommandBinds(){
        super("binds");
    }

    @Override
    public void run(String[] args) {
        for (Mod mod : ModuleManager.modList) {
            String keyName = Keyboard.getKeyName(mod.getKey());
            PlayerUtil.sendMessage(mod.getName() + ":" + keyName);
        }
    }
}
