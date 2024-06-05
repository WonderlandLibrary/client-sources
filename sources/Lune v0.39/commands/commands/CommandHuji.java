package me.superskidder.lune.commands.commands;

import me.superskidder.lune.commands.Command;
import me.superskidder.lune.utils.player.PlayerUtil;
import net.minecraft.util.HttpUtil;

import java.io.IOException;
import java.net.URL;

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
