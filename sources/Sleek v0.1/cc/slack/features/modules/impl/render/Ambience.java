package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S2BPacketChangeGameState;

@ModuleInfo(
   name = "Ambience",
   category = Category.RENDER
)
public class Ambience extends Module {
   public ModeValue<String> timemode = new ModeValue("Time", new String[]{"None", "Sun", "Night", "Custom"});
   private final NumberValue<Integer> customtime = new NumberValue("Custom Time", 5, 1, 24, 1);
   public ModeValue<String> weathermode = new ModeValue("Weather", new String[]{"None", "Clear", "Rain", "Thunder"});
   private final NumberValue<Float> weatherstrength = new NumberValue("Weather Strength", 1.0F, 0.0F, 1.0F, 0.01F);

   public Ambience() {
      this.addSettings(new Value[]{this.timemode, this.customtime, this.weathermode, this.weatherstrength});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      String var2 = (String)this.timemode.getValue();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 83500:
         if (var2.equals("Sun")) {
            var3 = 0;
         }
         break;
      case 75265016:
         if (var2.equals("Night")) {
            var3 = 1;
         }
         break;
      case 2029746065:
         if (var2.equals("Custom")) {
            var3 = 2;
         }
      }

      switch(var3) {
      case 0:
         mc.getWorld().setWorldTime(6000L);
         break;
      case 1:
         mc.getWorld().setWorldTime(15000L);
         break;
      case 2:
         mc.getWorld().setWorldTime((long)((Integer)this.customtime.getValue() * 1000));
      }

      var2 = (String)this.weathermode.getValue();
      var3 = -1;
      switch(var2.hashCode()) {
      case 2539444:
         if (var2.equals("Rain")) {
            var3 = 1;
         }
         break;
      case 65193517:
         if (var2.equals("Clear")) {
            var3 = 0;
         }
         break;
      case 329757892:
         if (var2.equals("Thunder")) {
            var3 = 2;
         }
      }

      switch(var3) {
      case 0:
         mc.getWorld().setRainStrength(0.0F);
         mc.getWorld().setThunderStrength(0.0F);
         break;
      case 1:
         mc.getWorld().setRainStrength((Float)this.weatherstrength.getValue());
         mc.getWorld().setThunderStrength(0.0F);
         break;
      case 2:
         mc.getWorld().setRainStrength((Float)this.weatherstrength.getValue());
         mc.getWorld().setThunderStrength((Float)this.weatherstrength.getValue());
      }

   }

   @Listen
   public void onPacket(PacketEvent event) {
      if (!((String)this.timemode.getValue()).contains("None") && event.getPacket() instanceof S03PacketTimeUpdate) {
         event.cancel();
      }

      if (!((String)this.weathermode.getValue()).contains("None") && event.getPacket() instanceof S2BPacketChangeGameState && ((S2BPacketChangeGameState)event.getPacket()).getGameState() >= 7 && ((S2BPacketChangeGameState)event.getPacket()).getGameState() <= 8) {
         event.cancel();
      }

   }
}
