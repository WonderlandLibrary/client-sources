/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import winter.console.cmds.Command;
import winter.module.modules.waypoint.WaypointUtil;
import winter.notifications.Notification;
import winter.utils.render.overlays.Hud;

public class Waypoint
extends Command {
    public Waypoint() {
        super("Waypoint");
        this.desc("Adds a waypoint");
        this.use("waypoint [add/remove] [name]");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        if (args[0].equalsIgnoreCase("waypoint")) {
            if (args.length > 2) {
                if (args[1].equalsIgnoreCase("add")) {
                    if (!WaypointUtil.pointExists(args[2])) {
                        WaypointUtil.addPoint(args[2], Minecraft.getMinecraft().getCurrentServerData().serverIP, new double[]{Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ}, -1);
                        this.printChat("Waypoint added.");
                        Hud.cur = new Notification("Waypoints", "Waypoint added.", 2);
                    } else {
                        this.printChat("Waypoint with name already exists.");
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (WaypointUtil.pointExists(args[2])) {
                        WaypointUtil.removePoint(args[2]);
                        this.printChat("Waypoint removed.");
                        Hud.cur = new Notification("Waypoints", "Waypoint removed.", 2);
                    } else {
                        this.printChat("No waypoint with specified name");
                    }
                }
            } else {
                this.printChat("Not enough arguments. Required arguments: 3");
            }
        }
    }
}

