package digital.rbq.module.implement.Command;

import digital.rbq.Lycoris;
import digital.rbq.module.Command;
import digital.rbq.module.Module;
import digital.rbq.utility.ChatUtils;

@Command.Info(name = "t", syntax = { "<Module>" }, help = "Toggle specified module.")
public class ToggleCmd extends Command {

	@Override
	public void execute(String[] args) throws Error {
		if (args.length < 1) {
			this.syntaxError();
		} else {
			Module mod = Lycoris.INSTANCE.getModuleManager().getModuleByName(args[0]);
			if (mod != null) {
				mod.toggle();
			} else {
				ChatUtils.sendMessageToPlayer("Invalid module (" + args[0] + ")");
			}
		}
	}

}
