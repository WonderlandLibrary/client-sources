package me.napoleon.napoline.commands.commands;

import org.lwjgl.opengl.Display;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.utils.player.PlayerUtil;

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

        Napoline.CLIENT_NAME = args[0];
        Napoline.CLIENT_Ver = args[1];

        switch (args.length){
            case 2:
                Display.setTitle(Napoline.CLIENT_NAME + " " + Napoline.CLIENT_Ver);
                return;
            case 3:
                Display.setTitle(Napoline.CLIENT_NAME + " " + Napoline.CLIENT_Ver + " " + args[2]);
                return;
            default:
                PlayerUtil.sendMessage(".title <客户端名字(必填)> <客户端版本(必填)> <标题后缀>");
        }
    }
}
