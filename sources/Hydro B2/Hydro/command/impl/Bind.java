package Hydro.command.impl;

import java.util.List;

import org.lwjgl.input.Keyboard;

import Hydro.Client;
import Hydro.command.CommandExecutor;
import Hydro.module.Module;
import Hydro.util.ChatUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumChatFormatting;

public class Bind implements CommandExecutor {

	@Override
	public void execute(EntityPlayerSP sender, List<String> args) {
		if (args.size() == 3) {
            Module m = Client.instance.moduleManager.getModuleByName(args.get(1));
            if (args.get(0).equalsIgnoreCase("add") && m != null) {
                m.setKey(Keyboard.getKeyIndex(args.get(2).toUpperCase()));
                ChatUtils.sendMessageToPlayer(m.getName() + " has been bound to " + Keyboard.getKeyName(m.getKey()) + ".");
            }else
            	sendSyntax();
        } else if (args.size() == 2) {
            Module m = Client.instance.moduleManager.getModuleByName(args.get(1));
            if (args.get(0).equalsIgnoreCase("del") && m != null) {
                m.setKey(Keyboard.KEY_NONE);
                ChatUtils.sendMessageToPlayer("Unbound " + m.getName() + ".");
            }else
            	sendSyntax();
        } else if (args.size() == 1) {
            if (args.get(0).equalsIgnoreCase("clear")) {
                Client.instance.moduleManager.modules.forEach(module -> module.setKey(Keyboard.KEY_NONE));
                ChatUtils.sendMessageToPlayer("All binds have been cleared.");
            }else
            	sendSyntax();
        }else
        	sendSyntax();
	}
	
	public void sendSyntax() {
		ChatUtils.sendMessageToPlayer(EnumChatFormatting.RED + "Invalid Syntax! " + EnumChatFormatting.WHITE + "- " + EnumChatFormatting.BLUE + ".bind add/del/clear (module) (key)");
		ChatUtils.sendMessageToPlayer(EnumChatFormatting.YELLOW + "Warning: .bind clear will clear all binds!");
	}

}
