package Ascii4UwUWareClient.Command.Commands;

import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Command.Command;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Util.Helper;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Keyboard;

public class Bind extends Command {
	public Bind() {
		super("Bind", new String[] { "b" }, "", "sketit");
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
