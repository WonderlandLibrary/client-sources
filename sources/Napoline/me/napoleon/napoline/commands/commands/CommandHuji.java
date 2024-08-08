package me.napoleon.napoline.commands.commands;

import net.minecraft.util.HttpUtil;

import java.io.IOException;
import java.net.URL;

import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.utils.player.PlayerUtil;

public class CommandHuji extends Command {

    public static String qq = "";

    public CommandHuji() {
        super("huji");
    }

    @Override
    public void run(String[] args) {
        if (args.length < 1) {
            return;
        }
        qq = args[0];
        new getThread().start();
    }
}

class getThread extends Thread {
    @Override
    public void run() {
        try {
            String s = HttpUtil.get(new URL("http://gaoyusense.buzz/client/Huji.php?qq=" + CommandHuji.qq));
            PlayerUtil.sendMessage("[AutoHuJi] " + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
