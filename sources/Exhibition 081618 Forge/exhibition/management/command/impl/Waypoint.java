package exhibition.management.command.impl;

import exhibition.Client;
import exhibition.management.command.Command;
import exhibition.util.misc.ChatUtil;
import exhibition.util.render.Colors;
import java.util.Iterator;
import net.minecraft.util.Vec3;

public class Waypoint extends Command {
   public Waypoint(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args == null) {
         this.printUsage();
      } else if (args.length > 1) {
         if (!args[0].equalsIgnoreCase("d") && !args[0].equalsIgnoreCase("del")) {
            if (args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("add")) {
               int color;
               if (args.length == 2) {
                  if (!Client.waypointManager.containsName(args[1])) {
                     color = Colors.getColor((int)(255.0D * Math.random()), (int)(255.0D * Math.random()), (int)(255.0D * Math.random()));
                     Client.waypointManager.createWaypoint(args[1], new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0D, this.mc.thePlayer.posZ), color, this.mc.getCurrentServerData().serverIP);
                     ChatUtil.printChat("§4[§cE§4]§8 §7Waypoint §c" + args[1] + "§7 has been successfully created.");
                  } else {
                     ChatUtil.printChat("§4[§cE§4]§8 §7Waypoint §c" + args[1] + "§7 already exists.");
                     this.printUsage();
                  }
               } else if (args.length == 5) {
                  if (!Client.waypointManager.containsName(args[1])) {
                     color = Colors.getColor((int)(255.0D * Math.random()), (int)(255.0D * Math.random()), (int)(255.0D * Math.random()));
                     Client.waypointManager.createWaypoint(args[1], new Vec3((double)Integer.parseInt(args[2]), (double)Integer.parseInt(args[3]), (double)Integer.parseInt(args[4])), color, this.mc.getCurrentServerData().serverIP);
                     ChatUtil.printChat("§4[§cE§4]§8 §7Waypoint §c" + args[1] + " §7has been successfully created.");
                  } else {
                     ChatUtil.printChat("§4[§cE§4]§8 §7Waypoint §c" + args[1] + " §7already exists.");
                     this.printUsage();
                  }
               } else {
                  this.printUsage();
               }
            }
         } else if (args.length == 2) {
            Iterator var2 = Client.waypointManager.getWaypoints().iterator();

            exhibition.management.waypoints.Waypoint waypoint;
            do {
               if (!var2.hasNext()) {
                  ChatUtil.printChat("§4[§cE§4]§8 §7No Waypoint under the name §c" + args[1] + "§7 was found.");
                  return;
               }

               waypoint = (exhibition.management.waypoints.Waypoint)var2.next();
            } while(!waypoint.getName().equalsIgnoreCase(args[1]));

            Client.waypointManager.deleteWaypoint(waypoint);
            ChatUtil.printChat("§4[§cE§4]§8 §7Waypoint §c" + args[1] + "§7 has been removed.");
         } else {
            this.printUsage();
         }
      } else {
         this.printUsage();
      }
   }

   public String getUsage() {
      return "add/del <name> or add <name> <x> <y> <z>";
   }
}
