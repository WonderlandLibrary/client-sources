package host.kix.uzi.command.commands;

import host.kix.uzi.command.Command;
import host.kix.uzi.module.modules.render.Waypoints;
import host.kix.uzi.utilities.minecraft.Logger;

/**
 * Created by Kix on 6/6/2017.
 * Made for the eclipse project.
 */
public class WaypointsCommand extends Command {

    public WaypointsCommand() {
        super("Waypoints", "Allows the user to add and remove waypoints.", "wp", "wayp", "point", "wpo");
    }

    @Override
    public void dispatch(String message) {
        String[] arguments = message.split(" ");
        if (arguments[1].equalsIgnoreCase("add") || arguments[1].equalsIgnoreCase("a")) {
            String label = arguments[2];
            int x = Integer.parseInt(arguments[3]);
            int y = Integer.parseInt(arguments[4]);
            int z = Integer.parseInt(arguments[5]);
            Waypoints.points.add(new Waypoints.Point(label, x, y, z));
        }
        if (arguments[1].equalsIgnoreCase("delete") || arguments[1].equalsIgnoreCase("d") || arguments[1].equalsIgnoreCase("del")) {
            String label = arguments[2];
            Waypoints.Point point = Waypoints.getPoint(label);
            Waypoints.points.remove(point);
        }
    }
}
