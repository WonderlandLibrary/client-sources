package cc.novoline.modules.visual;

import cc.novoline.modules.visual.HUD;
import cc.novoline.modules.visual.Waypoints$Waypoint;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class Waypoints$Serializer implements TypeSerializer {
   public static final TypeToken TYPE_TOKEN = TypeToken.of(Waypoints$Waypoint.class);

   public Waypoints$Waypoint deserialize(TypeToken var1, ConfigurationNode var2) {
      String var3 = var2.getNode(new Object[]{"name"}).getString();
      int var4 = var2.getNode(new Object[]{"x"}).getInt();
      int var5 = var2.getNode(new Object[]{"y"}).getInt();
      int var6 = var2.getNode(new Object[]{"z"}).getInt();
      return Waypoints$Waypoint.of(var3, var4, var5, var6);
   }

   public void a(TypeToken var1, Waypoints$Waypoint var2, ConfigurationNode var3) {
      int var4 = HUD.e();
   }
}
