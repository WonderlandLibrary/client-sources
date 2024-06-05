package cc.novoline.modules.misc;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.misc.WindowedFullscreen;
import cc.novoline.modules.visual.Waypoints;
import cc.novoline.modules.visual.Waypoints$Waypoint;
import cc.novoline.utils.DebugUtil;
import cc.novoline.utils.notifications.NotificationType;
import java.util.Iterator;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent$Action;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent$Action;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class LightningTracker extends AbstractModule {
   @Property("auto-waypoint")
   BooleanProperty auto_waypoint = PropertyFactory.booleanFalse();
   @Property("additional_y")
   private IntProperty add_y = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(10)).minimum(Integer.valueOf(10))).maximum(Integer.valueOf(90));

   public LightningTracker(ModuleManager var1) {
      super(var1, EnumModuleType.MISC, "LightningTracker", "Lightning Tracker");
      Manager.put(new Setting("LT_ADD_Y", "Addition Y", SettingType.SLIDER, this, this.add_y, 5.0D));
      Manager.put(new Setting("LT_AUTO_WP", "Auto waypoint", SettingType.CHECKBOX, this, this.auto_waypoint));
   }

   @EventTarget
   public void onPacket(PacketEvent var1) {
      String[] var2 = WindowedFullscreen.a();
      if(var1.getDirection().equals(PacketDirection.INCOMING) && var1.getPacket() instanceof S29PacketSoundEffect) {
         S29PacketSoundEffect var3 = (S29PacketSoundEffect)var1.getPacket();
         if(var3.getSoundName().equals("ambient.weather.thunder")) {
            int var4 = (int)var3.getX();
            int var5 = (int)var3.getY() + ((Integer)this.add_y.get()).intValue();
            int var6 = (int)var3.getZ();
            this.novoline.getNotificationManager().pop(this.getDisplayName(), "Lightning detected " + var4 + " " + var5 + " " + var6, 3000, NotificationType.INFO);
            String var7 = this.mc.isSingleplayer()?"/tp ":".tp ";
            String var8 = var4 + " " + var5 + " " + var6;
            ChatComponentText var9 = new ChatComponentText(var8);
            var9.setChatStyle(var9.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent$Action.SHOW_TEXT, new ChatComponentText(var7 + var8))));
            var9.setChatStyle(var9.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent$Action.RUN_COMMAND, var7 + var8)));
            ChatComponentText var10 = new ChatComponentText(DebugUtil.prefix(this.getDisplayName()).getFormattedText() + "Lightning detected ");
            var10.appendSibling(var9);
            this.mc.ingameGUI.n().a((IChatComponent)var10);
            Waypoints var11 = (Waypoints)this.getModule(Waypoints.class);
            if(((Boolean)this.auto_waypoint.get()).booleanValue()) {
               Iterator var12 = var11.getWaypointsList().iterator();
               if(var12.hasNext()) {
                  Waypoints$Waypoint var13 = (Waypoints$Waypoint)var12.next();
                  if(var13.getName().equals("Lightning") && var13.getX() == var4 && var13.getY() == var5 && var13.getZ() == var6) {
                     return;
                  }
               }

               var11.addWaypoint(new Waypoints$Waypoint("Lightning", var4, var5, var6));
            }
         }
      }

   }
}
