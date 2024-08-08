package me.napoleon.napoline.commands.commands;

import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.guis.customgui.CustomGuiManager;
import me.napoleon.napoline.guis.customgui.GuiObject;
import me.napoleon.napoline.guis.customgui.objects.StringObject;
import me.napoleon.napoline.utils.player.PlayerUtil;

public class CommandCustomGui extends Command {

    public CommandCustomGui() {
        super("customgui", "gui", "cg");
    }

    // TODO: 2021/2/28 éœ€è¦�æ·»åŠ ä¸€ä¸ªæ·»åŠ guiObjectçš„æŒ‡ä»¤(å†™åœ¨GUIé‡Œä¹Ÿå�¯ä»¥) 
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
