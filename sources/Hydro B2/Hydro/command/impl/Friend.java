package Hydro.command.impl;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import Hydro.Client;
import Hydro.command.CommandExecutor;
import Hydro.util.ChatUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class Friend implements CommandExecutor {

	@Override
	public void execute(EntityPlayerSP sender, List<String> args) {
		if (args.size() == 2) {
            String command = args.get(0);
            String name = args.get(1);
            handleFriend(command, name);
        } else if (args.size() == 3) {
            String command = args.get(0);
            String name = args.get(1);
            String nickname = args.get(2);
            handleFriend(command, name, nickname);
        }
	}
	
	private boolean handleFriend(String command, String name) {
        return handleFriend(command, name, name);
    }

    private boolean handleFriend(String command, String name, String nickname) {
        switch (command.toLowerCase()) {
            case "add": {
                Client.instance.friendManager.addFriend(name, nickname);
                ChatUtils.sendMessageToPlayer("Added \"" + name + "\".");
                return true;
            }
            case "del": {
                Client.instance.friendManager.deleteFriend(name);
                ChatUtils.sendMessageToPlayer("Deleted \"" + name + "\".");
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public String usage() {
        return ChatFormatting.WHITE + "f | friend <add/del> <name>";
    }

}
