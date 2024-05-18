package ooo.cpacket.ruby.manager;

import java.util.ArrayList;
import java.util.List;

import ooo.cpacket.ruby.command.Command;
import ooo.cpacket.ruby.command.commands.Friend;
import ooo.cpacket.ruby.command.commands.VClip;

public class CommandManager {
	
	public List<Command> cmds = new ArrayList<>();
	
	public CommandManager() {
		this.cmds.add(new VClip());
		this.cmds.add(new Friend());
	}
	
}
