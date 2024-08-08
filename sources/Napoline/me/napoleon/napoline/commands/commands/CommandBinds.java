package me.napoleon.napoline.commands.commands;

import org.lwjgl.input.Keyboard;

import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.utils.player.PlayerUtil;

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
