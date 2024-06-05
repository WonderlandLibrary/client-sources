package rip.helium.cheat;

import java.util.ArrayList;
import java.util.List;

import rip.helium.cheat.commands.Command;
import rip.helium.cheat.commands.cmds.*;

public class CommandManager {

	public List<Command> cmds = new ArrayList<>();


	public CommandManager() {
		this.cmds.add(new VClip());
		this.cmds.add(new Friend());
		this.cmds.add(new Username());
		this.cmds.add(new StaffDec());
	}
}
