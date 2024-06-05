package me.superskidder.lune.commands.commands;

import me.superskidder.lune.Lune;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.utils.player.PlayerUtil;
import org.lwjgl.opengl.Display;

/**
 * @author SuperSkidder
 */
public class CommandTitle extends Command {
    public CommandTitle(){
        super("title");
    }

    @Override
    public void run(String[] args) {
        if(args.length < 2){
            PlayerUtil.sendMessage(".title <客户端名字(必填)> <客户端版本(必填)> <标题后缀>");
            return;
        }

        Lune.CLIENT_NAME = args[0];
        Lune.CLIENT_Ver = args[1];

        switch (args.length){
            case 2:
                Display.setTitle(Lune.CLIENT_NAME + " " + Lune.CLIENT_Ver);
                return;
            case 3:
                Display.setTitle(Lune.CLIENT_NAME + " " + Lune.CLIENT_Ver + " " + args[2]);
                return;
            default:
                PlayerUtil.sendMessage(".title <客户端名字(必填)> <客户端版本(必填)> <标题后缀>");
        }
    }
}
