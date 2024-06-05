package me.superskidder.lune.commands.commands;

import me.superskidder.lune.guis.notification.Notification;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.utils.client.ClientUtils;
import me.superskidder.lune.utils.player.PlayerUtil;
import org.lwjgl.input.Keyboard;

/**
 * @description: 绑定功能
 * @author: Qian_Xia
 * @create: 2020-08-23 20:41
 **/
public class CommandBind extends Command {
    public CommandBind() {
        super("bind");
    }

    @Override
    public void run(String[] args) {
        if (args.length < 2) {
            PlayerUtil.sendMessage(".bind <Module> <Key>");
            return;
        }

        Mod mod = ModuleManager.getModsByName(args[0]);
        if (mod == null) {
            ClientUtils.sendClientMessage("Module \"" + args[0] + "\" Not Found!", Notification.Type.INFO);
            return;
        }

        int keyNum;
        mod.setKey(keyNum = Keyboard.getKeyIndex(args[1].toUpperCase()));
        args[1] = keyNum == 0 ? "None" : args[1].toUpperCase();
        ClientUtils.sendClientMessage("Module \"" + mod.getName() + "\" Was Bound to " + args[1], Notification.Type.INFO);
    }
}
