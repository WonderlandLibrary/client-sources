package none.command.commands;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.Client;
import none.command.Command;
import none.event.events.EventChat;
import none.module.Module;

public class Bind extends Command{

	@Override
	public String getAlias() {
		return "bind";
	}

	@Override
	public String getDescription() {
		return "Bind Module Key.";
	}

	@Override
	public String getSyntax() {
		return " .bind " + ChatFormatting.GREEN + "[Module] " + ChatFormatting.GRAY +"[Key]" + ChatFormatting.BOLD + " OR " + ChatFormatting.WHITE + ".bind " + ChatFormatting.RED + "del"+ ChatFormatting.GREEN +" [Module]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		for (Module m : Client.instance.moduleManager.getModules()) {
			if (args[0].equalsIgnoreCase(m.getName())) {
	            args[1] = args[1].toUpperCase();
            	int key = Keyboard.getKeyIndex(args[1]);
	            m.setKeyCode(Keyboard.getKeyIndex(Keyboard.getKeyName(key)));
	            EventChat.addchatmessage(ChatFormatting.GREEN + " " + m.getName() + ChatFormatting.WHITE + " has been binded to " + ChatFormatting.AQUA + args[1]);
	        }else
			if (args[0].equalsIgnoreCase("del")) {
				if (args[1].equalsIgnoreCase(m.getName())) {
					m.setKeyCode(Keyboard.KEY_NONE);
					EventChat.addchatmessage(ChatFormatting.GREEN + " " +  m.getName() + ChatFormatting.WHITE + " has been unbinded to " + ChatFormatting.AQUA + ChatFormatting.BOLD +"NONE");
				}
			}
		}
	}

}
