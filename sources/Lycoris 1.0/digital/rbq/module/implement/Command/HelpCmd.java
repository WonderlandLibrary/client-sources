package digital.rbq.module.implement.Command;

import net.minecraft.util.EnumChatFormatting;
import digital.rbq.Lycoris;
import digital.rbq.module.Command;
import digital.rbq.utility.ChatUtils;

@Command.Info(name = "help", syntax = { "" }, help = "Display help for all commands.")
public class HelpCmd extends Command {
	@Override
	public void execute(String[] args) throws Error {

		for (final Command cmd : Lycoris.INSTANCE.getCommandManager().getCommands()) {
			String output = "." + cmd.getCmdName() + "";

			String[] split;
			for (int length = (split = output.split("\n")).length, j = 0; j < length; ++j) {
				String line = split[j];
				ChatUtils.sendMessageToPlayer(line + EnumChatFormatting.GOLD + " " + cmd.getHelp());
			}
		}

		ChatUtils.sendMessageToPlayer(".<module> <setting> <value>" + EnumChatFormatting.GOLD
				+ " Edit setting of specified  " + EnumChatFormatting.GOLD + "module");
	}
}
