package me.superskidder.lune.commands.commands;

import com.sun.xml.internal.bind.v2.TODO;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.customgui.CustomGuiManager;
import me.superskidder.lune.customgui.GuiObject;
import me.superskidder.lune.customgui.objects.StringObject;
import me.superskidder.lune.utils.player.PlayerUtil;

public class CommandCustomGui extends Command {

    public CommandCustomGui() {
        super("customgui", "gui", "cg");
    }

    // TODO: 2021/2/28 需要添加一个添加guiObject的指令(写在GUI里也可以) 
    @Override
    public void run(String[] args) {
        if (args.length == 3) {
            for (GuiObject guiObject : CustomGuiManager.objects) {
                if (args[0].equalsIgnoreCase(guiObject.name)) {
                    if (guiObject instanceof StringObject) {
                        StringObject obj = (StringObject) guiObject;
                        switch (args[1]) {
                            case "content":
                                ((StringObject) guiObject).content = args[2];
                                break;
                            case "red":
                                try {
                                    ((StringObject) guiObject).red = Integer.parseInt(args[2]);
                                } catch (NumberFormatException exception) {
                                    PlayerUtil.sendMessage("Number format error");
                                }
                                break;
                            case "green":
                                try {
                                    ((StringObject) guiObject).green = Integer.parseInt(args[2]);
                                } catch (NumberFormatException exception) {
                                    PlayerUtil.sendMessage("Number format error");
                                }
                                break;
                            case "blue":
                                try {
                                    ((StringObject) guiObject).blue = Integer.parseInt(args[2]);
                                } catch (NumberFormatException exception) {
                                    PlayerUtil.sendMessage("Number format error");
                                }
                                break;
                            default:
                                PlayerUtil.sendMessage("parameter not found");
                                break;
                        }
                    }
                }
            }
        } else {
            PlayerUtil.sendMessage(".customgui <object> <parameter> <value>");
        }
    }
}
