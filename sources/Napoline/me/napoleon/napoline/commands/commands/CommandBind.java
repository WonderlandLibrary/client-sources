package me.napoleon.napoline.commands.commands;

import org.lwjgl.input.Keyboard;

import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.guis.notification.Notification;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.utils.client.ClientUtils;
import me.napoleon.napoline.utils.player.PlayerUtil;

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
