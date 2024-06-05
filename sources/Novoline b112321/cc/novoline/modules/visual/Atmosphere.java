package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.visual.HUD;
import java.util.function.Supplier;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S2BPacketChangeGameState;

public final class Atmosphere extends AbstractModule {
   @Property("time-mode")
   private final StringProperty z = PropertyFactory.createString("Static").acceptableValues(new String[]{"Static", "Cycle"});
   @Property("time")
   private final DoubleProperty time = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(12.0D)).minimum(Double.valueOf(1.0D))).maximum(Double.valueOf(24.0D));
   @Property("cycle-speed")
   private final IntProperty A = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(5)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(20));
   @Property("weather-mode")
   private final StringProperty weather_mode = PropertyFactory.createString("Clean").acceptableValues(new String[]{"Clean", "Rain", "Thunder", "Snowfall", "Snowstorm"});
   @Property("weather-control")
   private final BooleanProperty weather_control = PropertyFactory.booleanFalse();
   private long y;

   public Atmosphere(ModuleManager var1) {
      super(var1, "Atmosphere", EnumModuleType.VISUALS);
      Manager.put(new Setting("WORLD_TIME_MODE", "Time Mode", SettingType.COMBOBOX, this, this.z));
      Manager.put(new Setting("WORLD_TIME", "Hour", SettingType.SLIDER, this, this.time, 0.5D, this::lambda$new$0));
      Manager.put(new Setting("WORLD_CYCLE_SPEED", "Cycle Speed", SettingType.SLIDER, this, this.A, 1.0D, this::lambda$new$1));
      Manager.put(new Setting("WEATHER_CONTROL", "Weather control", SettingType.CHECKBOX, this, this.weather_control));
      SettingType var10004 = SettingType.COMBOBOX;
      StringProperty var10006 = this.weather_mode;
      BooleanProperty var10007 = this.weather_control;
      this.weather_control.getClass();
      Manager.put(new Setting("WEATHER_MODE", "Weather", var10004, this, var10006, var10007::get));
   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      int var2 = HUD.e();
      if(this.mc.world != null) {
         if(((Boolean)this.weather_control.get()).booleanValue()) {
            String var3 = (String)this.weather_mode.get();
            byte var4 = -1;
            switch(var3.hashCode()) {
            case 756396958:
               if(!var3.equals("Snowfall")) {
                  break;
               }

               var4 = 0;
            case 2539444:
               if(!var3.equals("Rain")) {
                  break;
               }

               var4 = 1;
            case 1986044198:
               if(!var3.equals("Snowstorm")) {
                  break;
               }

               var4 = 2;
            case 329757892:
               if(var3.equals("Thunder")) {
                  var4 = 3;
               }
            }

            switch(var4) {
            case 0:
            case 1:
               this.mc.world.setRainStrength(1.0F);
               this.mc.world.setThunderStrength(0.0F);
            case 2:
            case 3:
               this.mc.world.setRainStrength(1.0F);
               this.mc.world.setThunderStrength(1.0F);
            default:
               this.mc.world.setRainStrength(0.0F);
               this.mc.world.setThunderStrength(0.0F);
            }
         }

         if(this.z.equals("Static")) {
            this.mc.world.setWorldTime(((Double)this.time.get()).longValue() * 1000L);
         }
      }

   }

   @EventTarget
   public void a(Render2DEvent var1) {
      int var2 = HUD.h();
      if(this.z.equals("Cycle")) {
         this.y += (long)((Integer)this.A.get()).intValue();
         this.mc.world.setWorldTime(this.y);
         if(this.y > 24000L) {
            this.y = 0L;
         }
      }

   }

   @EventTarget
   public void b(PacketEvent var1) {
      int var2 = HUD.h();
      if(var1.getDirection().equals(PacketDirection.INCOMING)) {
         if(((Boolean)this.weather_control.get()).booleanValue() && var1.getPacket() instanceof S2BPacketChangeGameState) {
            S2BPacketChangeGameState var3 = (S2BPacketChangeGameState)var1.getPacket();
            if(var3.getGameState() == 1 || var3.getGameState() == 2 || var3.getGameState() == 7 || var3.getGameState() == 8) {
               var1.setCancelled(true);
            }
         }

         if(var1.getPacket() instanceof S03PacketTimeUpdate) {
            var1.setCancelled(true);
         }
      }

   }

   public StringProperty getWeather_mode() {
      return this.weather_mode;
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(this.z.equals("Cycle"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.z.equals("Static"));
   }
}
