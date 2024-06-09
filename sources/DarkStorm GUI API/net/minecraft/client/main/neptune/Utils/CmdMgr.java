package net.minecraft.client.main.neptune.Utils;

import java.util.Collection;
import java.util.TreeMap;



//import net.client.Mod.Collection.Cmds.AuraCmd;


import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Events.EventChatSend;
import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.BindCmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.ConnectCmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.ESPCmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.FriendCmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.HelpCmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.PDCmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.PhaseCmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.ServerInfoCmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.SpeedCmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.ToggleCmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.TracersCmd;
import net.minecraft.client.main.neptune.Mod.Collection.Cmds.VClipCmd;
import net.minecraft.client.main.neptune.memes.Memeager;
import net.minecraft.client.main.neptune.memes.Memetarget;

public class CmdMgr {
	private final TreeMap<String, Cmd> cmds;

	public CmdMgr() {
		this.cmds = new TreeMap<String, Cmd>();
		this.add(new BindCmd());
		this.add(new FriendCmd());
		this.add(new HelpCmd());
		this.add(new PDCmd());
		this.add(new PhaseCmd());
		this.add(new ServerInfoCmd());
		this.add(new ToggleCmd());
		this.add(new VClipCmd());
		Memeager.register(this);
	}

	private void add(Cmd cmd) {
		this.cmds.put(cmd.getCmdName(), cmd);
	}

	@Memetarget
	public void onChatSend(EventChatSend event) {
		if(!Neptune.getWinter().legit.isEnabled()) {
			final String message = event.message;
			if (message.startsWith(".")) {
				event.setCancelled(true);
				final String input = message.substring(1);
				final String commandName = input.split(" ")[0];
				String[] args;
				if (input.contains(" ")) {
					args = input.substring(input.indexOf(" ") + 1).split(" ");
				} else {
					args = new String[0];
				}
				final Cmd cmd = this.getCommandByName(commandName);
				if (cmd != null) {
					try {
						cmd.execute(args);
					} catch (Cmd.SyntaxError e) {
						if (e.getMessage() != null) {
							ChatUtils.sendMessageToPlayer("§4Syntax error:§r " + e.getMessage());
						} else {
							ChatUtils.sendMessageToPlayer("§4Syntax error!§r");
						}
						cmd.printSyntax();
					} catch (Cmd.Error e2) {
						ChatUtils.sendMessageToPlayer(e2.getMessage());
					} catch (Exception ex) {
					}
				} else {
					ChatUtils.sendMessageToPlayer("\"." + commandName + "\" is not a valid command.");
				}
			}
		}
	}

	public Cmd getCommandByName(final String name) {
		return this.cmds.get(name);
	}

	public Collection<Cmd> getCmds() {
		return this.cmds.values();
	}

	public int countCommands() {
		return this.cmds.size();
	}
}
