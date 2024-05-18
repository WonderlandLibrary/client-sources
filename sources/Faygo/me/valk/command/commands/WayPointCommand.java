package me.valk.command.commands;

import java.util.List;

import org.lwjgl.input.Keyboard;

import me.valk.Vital;
import me.valk.command.Command;
import me.valk.manager.managers.WaypointManager;
import me.valk.module.Module;
import me.valk.utils.bind.KeyBind;

public class WayPointCommand extends Command {

	public WayPointCommand() {
		super("Waypoint", new String[] { "wp" }, "Add waypoints");
	}

	@Override
	public void onCommand(List<String> args) {

		if (args.get(0).equalsIgnoreCase("add")) {

			String name = args.get(1);

			String x = args.get(2);
			int xint = Integer.parseInt(x);

			String y = args.get(3);
			int yint = Integer.parseInt(y);

			String z = args.get(4);
			int zint = Integer.parseInt(z);

			if (name == null) {
				error("Invalid args! Usage : 'Waypoint add name x y z' or 'Waypoint remove name'");
				return;
			}
			if (x == null) {
				error("Invalid args! Usage : 'Waypoint add name x y z' or 'Waypoint remove name'");
				return;
			}
			if (z == null) {
				error("Invalid args! Usage : 'Waypoint add name x y z' or 'Waypoint remove name'");
				return;
			}
			if (y == null) {
				error("Invalid args! Usage : 'Waypoint add name x y z' or 'Waypoint remove name'");
				return;
			}
			if (name != null && x != null && y != null && z != null) {
				WaypointManager.addWayPoint(name, xint, yint, zint);
				addChat("Added new waypoint by name " + name);

			} else {
				error("Invalid args! Usage : 'Waypoint add name x y z' or 'Waypoint remove name'");
			}
		} else if (args.get(0).equalsIgnoreCase("remove")) {
			if (args.size() == 2) {
				String name = args.get(1);
				try {
					WaypointManager.removeWayPoint(name);
					addChat("Removed a waypoint called " + name);
				} catch (Exception ex) {
				}

			}
		} else {
			error("Invalid args! Usage : 'Waypoint add name x y z' or 'Waypoint remove name'");
		}
	}

}
