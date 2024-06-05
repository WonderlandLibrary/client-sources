package me.superskidder.lune.commands.commands;

import me.superskidder.lune.Lune;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.utils.client.FileUtil;
import me.superskidder.lune.utils.player.PlayerUtil;
import net.minecraft.util.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author: QianXia
 * @description: 说明
 * @create: 2021/01/06-16:27
 */
public class CommandHelp extends Command {
    public CommandHelp(){
        super("Help");
    }

    @Override
    public void run(String[] args) {
        PlayerUtil.sendMessage("Loading...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String readmeContext = HttpUtil.get(new URL("https://qian-xia233.coding.net/p/lune/d/Web/git/raw/master/README"));
                    FileUtil.saveFile("README", readmeContext);
                    Runtime.getRuntime().exec("notepad " + Lune.luneDataFolder.getAbsolutePath() + File.separator + "README");
                } catch (IOException e) {
                    e.printStackTrace();
                    PlayerUtil.sendMessage("Failed to get file...");
                }
            }
        }).start();

    }
}
