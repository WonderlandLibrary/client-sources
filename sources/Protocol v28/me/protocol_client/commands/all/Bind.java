package me.protocol_client.commands.all;

import org.lwjgl.input.Keyboard;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.commands.Command;
import me.protocol_client.files.allfiles.Keybinds;
import me.protocol_client.module.Module;

public class Bind extends Command {

	@Override
	public String getAlias() {
		return "Bind";
	}

	@Override
	public String getDescription() {
		return "Rebind Modules";
	}

	@Override
	public String getSyntax() {
		return ".Bind <Mod> <Key/Clear> | .Bind clearall";
	}

	@Override
	public void onCommandSent(String command, String[] args) throws Exception {
		String modName = "";
		String keyName = "";
		modName = args[0];
		keyName = args[1];
		Module module = Protocol.module.getModule(modName);
		if (modName.equalsIgnoreCase("clearall")) {
			for (Module mod : Protocol.getModules()) {
				Keybinds.bindKey(mod, 0);
				mod.setKeyCode(0);
			}
			return;
		}
		if (module == null) {
			Wrapper.tellPlayer("\2477Invalid Module.");
			return;
		}
		if (keyName == "none") {
			Keybinds.bindKey(module, Keyboard.KEY_NONE);
			Wrapper.tellPlayer("§9" + module.name + "\2477's bind has been cleared.");
			module.keyCode = 0;
			return;
		}
		module.keyCode = Keyboard.getKeyIndex(keyName.toUpperCase());
		if (Keyboard.getKeyIndex(keyName.toUpperCase()) == 0 && !keyName.equalsIgnoreCase("none")) {
			Wrapper.tellPlayer("§7Couldn't find that key.");
		} else {
			if (keyName.equalsIgnoreCase("none")) {
				module.keyCode = 0;
			}
			Keybinds.bindKey(module, Keyboard.getKeyIndex(keyName.toUpperCase()));
			Wrapper.tellPlayer("§9" + module.name + " §7bound to " + "§9" + keyName);
		}
		return;
	}

}
