package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.visual.HUD;
import cc.novoline.modules.visual.Waypoints$1;
import cc.novoline.modules.visual.Waypoints$2;
import cc.novoline.modules.visual.Waypoints$Serializer;
import cc.novoline.modules.visual.Waypoints$Waypoint;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.api.FontRenderer;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_16;
import cc.novoline.utils.java.Checks;
import com.google.common.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.Ea;
import net.X9;

public final class Waypoints extends AbstractModule {
   private final List waypoints = new ObjectArrayList();
   private static FontRenderer SF_16 = Fonts$SF$SF_16.SF_16;
   private static Timer tpTimer = new Timer();
   private final List waypointsView;

   public Waypoints(ModuleManager var1) {
      super(var1, "Waypoints", (EnumModuleType)EnumModuleType.VISUALS, (String)null, "waypoints");
      this.waypointsView = Collections.unmodifiableList(this.waypoints);
      this.addWaypointsFromConfig();
   }

   private void addWaypointsFromConfig() {
      try {
         this.waypoints.addAll(this.config.getRootNode().getNode(new Object[]{"waypoints"}).getList((TypeToken)(new Waypoints$1(this)), (List)(new ArrayList())));
      } catch (X9 var2) {
         throw new RuntimeException("An error occurred while deserialization of the waypoints file", var2);
      }
   }

   protected void a(Ea var1) {
      var1.a((TypeToken)Waypoints$Serializer.TYPE_TOKEN, new Waypoints$Serializer());
   }

   @EventTarget
   public void onRender2D(Render2DEvent var1) {
      this.waypoints.forEach(Waypoints$Waypoint::render);
   }

   @EventTarget
   public void onRender3D(Render3DEvent var1) {
      HUD.e();
      Iterator var3 = this.waypoints.iterator();
      if(var3.hasNext()) {
         Waypoints$Waypoint var4 = (Waypoints$Waypoint)var3.next();
         float var5 = (float)((double)((float)var4.getX() + 0.0F * var1.getPartialTicks()) - this.mc.getRenderManager().renderPosX);
         float var6 = (float)((double)((float)var4.getY() + 0.0F * var1.getPartialTicks()) - this.mc.getRenderManager().renderPosY);
         float var7 = (float)((double)((float)var4.getZ() + 0.0F * var1.getPartialTicks()) - this.mc.getRenderManager().renderPosZ);
         var4.setPositions(var4.convertTo2D((double)var5, (double)var6, (double)var7));
      }

   }

   public void addWaypoint(Waypoints$Waypoint var1) {
      this.waypoints.add(var1);
      Waypoints var10000 = this;

      try {
         var10000.updateConfigNode();
      } catch (X9 var3) {
         this.logger.warn("An error occurred while saving friends list", var3);
      }

   }

   public boolean removeWaypoint(String var1) {
      HUD.e();
      Checks.notBlank(var1, "name");
      boolean var3 = this.waypoints.removeIf(Waypoints::lambda$removeWaypoint$0);
      if(var3) {
         Waypoints var10000 = this;

         try {
            var10000.updateConfigNode();
         } catch (X9 var5) {
            this.logger.warn("An error occurred while saving friends list", var5);
         }
      }

      return var3;
   }

   private void updateConfigNode() throws X9 {
      this.config.getRootNode().getNode(new Object[]{"waypoints"}).a(new Waypoints$2(this), this.waypoints);
   }

   public Waypoints$Waypoint getWaypointByName(String var1) {
      return (Waypoints$Waypoint)this.waypoints.stream().filter(Waypoints::lambda$getWaypointByName$1).findFirst().orElse((Object)null);
   }

   public List getWaypoints() {
      return this.waypointsView;
   }

   public List getWaypointsList() {
      return this.waypoints;
   }

   private static boolean lambda$getWaypointByName$1(String var0, Waypoints$Waypoint var1) {
      return var1.getName().equalsIgnoreCase(var0);
   }

   private static boolean lambda$removeWaypoint$0(String var0, Waypoints$Waypoint var1) {
      return var1.getName().equalsIgnoreCase(var0);
   }

   static FontRenderer access$000() {
      return SF_16;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
