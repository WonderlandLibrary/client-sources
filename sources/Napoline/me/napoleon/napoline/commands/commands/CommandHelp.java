package me.napoleon.napoline.commands.commands;

import net.minecraft.util.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.utils.client.FileUtil;
import me.napoleon.napoline.utils.player.PlayerUtil;

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
                    String readmeContext = HttpUtil.get(new URL(""));
                    FileUtil.saveFile("README", readmeContext);
                    Runtime.getRuntime().exec("notepad " + Napoline.NapolineDataFolder.getAbsolutePath() + File.separator + "README");
                } catch (IOException e) {
                    e.printStackTrace();
                    PlayerUtil.sendMessage("Failed to get file...");
                }
            }
        }).start();

    }
}
