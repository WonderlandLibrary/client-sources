package Focus.Beta.API.commands.impl;

import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Keyboard;

import Focus.Beta.Client;
import Focus.Beta.API.commands.Command;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.UTILS.helper.Helper;

public class Bind extends Command {
	public Bind() {
		super("Bind", new String[] { "b" }, "", "");
	}

	@Override
	public String execute(String[] args) {
		if (args.length >= 2) {
			Module m = Client.instance.getModuleManager().getAlias(args[0]);
			if (m != null) {
				int k = Keyboard.getKeyIndex((String) args[1].toUpperCase());
				m.setKey(k);
				Object[] arrobject = new Object[2];
				arrobject[0] = m.getName();
				arrobject[1] = k == 0 ? "none" : args[1].toUpperCase();
				Helper.sendMessage(String.format("Bound %s to %s", arrobject));
			} else {
				Helper.sendMessage("Module name " + (Object) ((Object) EnumChatFormatting.RED) + args[0]
						+ (Object) ((Object) EnumChatFormatting.GRAY) + " is invalid");
			}
		} else {
			Helper.sendMessage("Correct usage .bind <module> <key>");
		}
		return null;
	}
}
