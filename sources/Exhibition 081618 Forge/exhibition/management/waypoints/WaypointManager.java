package exhibition.management.waypoints;

import exhibition.util.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.util.Vec3;

public class WaypointManager {
   private List waypoints = new CopyOnWriteArrayList();
   private final File WAYPOINT_DIR = FileUtils.getConfigFile("Waypoints");

   public WaypointManager() {
      this.loadWaypoints();
   }

   public void loadWaypoints() {
      try {
         List fileContent = FileUtils.read(this.WAYPOINT_DIR);
         Iterator var2 = fileContent.iterator();

         while(var2.hasNext()) {
            String line = (String)var2.next();
            String[] split = line.split(":");
            String waypointName = split[0];

            assert split.length == 6;

            this.createWaypoint(waypointName, new Vec3(Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3])), Integer.parseInt(split[4]), split[5]);
         }
      } catch (Exception var6) {
         System.out.println("[ERROR] Failed loading waypoints! Please check that waypoints.txt is in the valid format!");
         var6.printStackTrace();
      }

   }

   public void saveWaypoints() {
      List fileContent = new ArrayList();
      Iterator var2 = this.getWaypoints().iterator();

      while(var2.hasNext()) {
         Waypoint waypoint = (Waypoint)var2.next();
         String waypointName = waypoint.getName();
         String x = String.valueOf(waypoint.getVec3().xCoord);
         String y = String.valueOf(waypoint.getVec3().yCoord);
         String z = String.valueOf(waypoint.getVec3().zCoord);
         fileContent.add(String.format("%s:%s:%s:%s:%s:%s", waypointName, x, y, z, waypoint.getColor(), waypoint.getAddress()));
      }

      FileUtils.write(this.WAYPOINT_DIR, fileContent, true);
   }

   public void deleteWaypoint(Waypoint waypoint) {
      this.waypoints.remove(waypoint);
   }

   public void createWaypoint(String name, Vec3 vec3, int color, String address) {
      this.waypoints.add(new Waypoint(name, vec3, color, address));
      this.saveWaypoints();
   }

   public List getWaypoints() {
      return this.waypoints;
   }

   public boolean containsName(String name) {
      Iterator var2 = this.getWaypoints().iterator();

      Waypoint waypoint;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         waypoint = (Waypoint)var2.next();
      } while(!waypoint.getName().equalsIgnoreCase(name));

      return true;
   }
}
