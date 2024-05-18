package com.darkcart.xdolf.commands;

import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Waypoint;

import net.minecraft.client.renderer.entity.RenderManager;

public class CmdWaypoint extends Command {
	
	public CmdWaypoint() {
		super("waypoint");
	}

	@Override
	public void runCommand(String s, String[] args) {
		try {
			if(args[0].equalsIgnoreCase("add")) {
				try {
					for(Waypoint w : Waypoint.wayPoints) {
						if(w.getName().equals(args[1])) {
							Wrapper.addChatMessage("\"" + args[1] + "\"" + " already exists.");
							return;
						}
					}
					Waypoint point = new Waypoint(args[1], (int)Wrapper.getPlayer().posX, (int)Wrapper.getPlayer().posY, (int)Wrapper.getPlayer().posZ, RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
					Wrapper.getFileManager().saveWaypoints();
					Wrapper.addChatMessage("Added waypoint \"" + args[1] + "\"");
				} catch(Exception e) {
					Wrapper.addChatMessage("Usage: " + getSyntax());
				}
			} else if(args[0].equalsIgnoreCase("del")) {
				try {
					for(Waypoint w : Waypoint.wayPoints) {
						if(w.getName().equals(args[1])) {
							Waypoint.wayPoints.remove(w);
							Wrapper.getFileManager().saveWaypoints();
							Wrapper.addChatMessage("Removed waypoint: \"" + args[1] + "\".");
							break;
						} else {
							Wrapper.addChatMessage(args[1] + " does not exist.");
						}
					}
				} catch(Exception e) {
					Wrapper.addChatMessage("Usage: " + getSyntax());
				}
			} else if(args[0].equalsIgnoreCase("clear")) {
				Waypoint.wayPoints.clear();
				Wrapper.getFileManager().saveWaypoints();
				Wrapper.addChatMessage("Cleared waypoints.");
			}
		} catch(Exception e) {
			Wrapper.addChatMessage("Usage: " + getSyntax());
		}
	}
	
	@Override
	public String getDescription() {
		return "Adds and removes waypoints";
	}

	@Override
	public String getSyntax() {
		return "waypoint add/del <name>";
	}
}
