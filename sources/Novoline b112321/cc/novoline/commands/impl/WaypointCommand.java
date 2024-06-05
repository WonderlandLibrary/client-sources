package cc.novoline.commands.impl;

import cc.novoline.Novoline;
import cc.novoline.commands.NovoCommand;
import cc.novoline.modules.visual.Waypoints;
import cc.novoline.modules.visual.Waypoints$Waypoint;
import cc.novoline.utils.messages.MessageFactory;
import cc.novoline.utils.notifications.NotificationType;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import net.Uj;
import net.a_E;

public final class WaypointCommand extends NovoCommand {
   public WaypointCommand(Novoline var1) {
      super(var1, "waypoint", "Manages waypoints", (Iterable)Arrays.asList(new String[]{"waypoints", "wp"}));
   }

   private void sendHelp() {
      this.a("Waypoints help:", ".waypoint", new Uj[]{MessageFactory.a("add (x) (y) (z) (name)", "add waypoint"), MessageFactory.a("remove (name)", "removes waypoint"), MessageFactory.a("clear", "remove all waypoints"), MessageFactory.a("list", "show waypoints list")});
   }

   public void process(String[] var1) {
      a_E.b();
      Waypoints var3 = (Waypoints)Novoline.getInstance().getModuleManager().getModule(Waypoints.class);
      if(var1.length == 0) {
         this.sendHelp();
      } else {
         String var4 = var1[0];
         String var5 = var4.toLowerCase();
         byte var6 = -1;
         switch(var5.hashCode()) {
         case 96417:
            if(!var5.equals("add")) {
               break;
            }

            var6 = 0;
         case -934610812:
            if(!var5.equals("remove")) {
               break;
            }

            var6 = 1;
         case -1335458389:
            if(!var5.equals("delete")) {
               break;
            }

            var6 = 2;
         case 99339:
            if(!var5.equals("del")) {
               break;
            }

            var6 = 3;
         case 94746189:
            if(!var5.equals("clear")) {
               break;
            }

            var6 = 4;
         case 3322014:
            if(var5.equals("list")) {
               var6 = 5;
            }
         }

         switch(var6) {
         case 0:
            if(var1.length != 5) {
               this.sendHelp();
               return;
            }

            var3.addWaypoint(Waypoints$Waypoint.of(var1[4], Integer.parseInt(var1[1]), Integer.parseInt(var1[2]), Integer.parseInt(var1[3])));

            try {
               var3.getConfig().save();
               this.notify("Waypoint " + var1[4] + " was added successfully!", 5000);
               break;
            } catch (IOException var10) {
               Novoline.getInstance().getNotificationManager().pop("Can\'t save to file", NotificationType.ERROR);
               var3.getLogger().warn("An error occurred while saving waypoints list", var10);
            }
         case 1:
         case 2:
         case 3:
            if(var1.length != 2) {
               this.sendHelp();
               return;
            }

            if(var3.removeWaypoint(var1[1])) {
               this.notify("Removed " + var1[1] + " waypoint!", 5000);
               Waypoints var10000 = var3;

               try {
                  var10000.getConfig().save();
                  break;
               } catch (IOException var9) {
                  Novoline.getInstance().getNotificationManager().pop("Can\'t save to file", NotificationType.ERROR);
                  var3.getLogger().warn("An error occurred while saving waypoints list", var9);
               }
            }

            this.notifyError("Waypoint doesn\'t exist!", 5000);
         case 4:
            if(var1.length != 1) {
               this.sendHelp();
               return;
            }

            if(var3.getWaypointsList().isEmpty()) {
               break;
            }

            var3.getWaypointsList().clear();
         case 5:
            if(var1.length != 1) {
               this.sendHelp();
               return;
            }

            this.send("Waypoints:");
            Iterator var7 = var3.getWaypointsList().iterator();
            if(var7.hasNext()) {
               Waypoints$Waypoint var8 = (Waypoints$Waypoint)var7.next();
               this.send(" - name: §3" + var8.getName() + "§r, coordinates: §8X:§r " + var8.getX() + " §8Y:§r " + var8.getY() + " §8Z:§r " + var8.getZ());
            }
         }

      }
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
