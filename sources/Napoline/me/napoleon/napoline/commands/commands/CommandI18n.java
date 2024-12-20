package me.napoleon.napoline.commands.commands;

import net.minecraft.util.HttpUtil;

import java.io.IOException;
import java.net.URL;

import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.utils.client.FileUtil;
import me.napoleon.napoline.utils.client.I18n;
import me.napoleon.napoline.utils.player.PlayerUtil;

/**
 * @description: 国际化的一步
 * @author: QianXia
 * @create: 2020/10/31 19:56
 **/
public class CommandI18n extends Command {
    public CommandI18n(){
        super("I18n");
    }

    @Override
    public void run(String[] args) {
        if(args.length < 1) {
            PlayerUtil.sendMessage(".I18n <reload>");
            PlayerUtil.sendMessage(".I18n <language>");
            return;
        }
        switch (args[0].toLowerCase()) {
            case "reload":
                String zh_CN = null;
                try {
                    zh_CN = HttpUtil.get(new URL("https://qian-xia233.coding.net/p/lune/d/Web/git/raw/master/Translate/zh-CN.lang"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileUtil.saveFile("zh_CN.lang", zh_CN);
                I18n.load();
                PlayerUtil.sendMessage("OK");
                return;
            case "chinese":
                I18n.isChinese = true;
                return;
            default:
                I18n.isChinese = false;
                return;
        }
    }
}
