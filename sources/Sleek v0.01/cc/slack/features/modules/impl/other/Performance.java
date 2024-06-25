package cc.slack.features.modules.impl.other;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.PrintUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.settings.GameSettings;

@ModuleInfo(
   name = "Performance",
   category = Category.OTHER
)
public class Performance extends Module {
   public ModeValue<String> performancemode = new ModeValue(new String[]{"None", "Simple", "Extreme"});
   public NumberValue<Integer> chunkupdates = new NumberValue("Chunk Updates", 1, 1, 10, 1);
   public BooleanValue garbagevalue = new BooleanValue("MemoryFix", false);

   public Performance() {
      this.addSettings(new Value[]{this.performancemode, this.chunkupdates, this.garbagevalue});
   }

   public void onEnable() {
      String var1 = (String)this.performancemode.getValue();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -1818419758:
         if (var1.equals("Simple")) {
            var2 = 0;
         }
         break;
      case 359367820:
         if (var1.equals("Extreme")) {
            var2 = 1;
         }
      }

      switch(var2) {
      case 0:
         PrintUtil.message("Performance Mode Simple is enabled successfully");
         break;
      case 1:
         PrintUtil.message("Performance Mode EXTREME is enabled successfully");
      }

   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if ((Boolean)this.garbagevalue.getValue() && mc.getPlayer().ticksExisted % 5000 == 0) {
         System.gc();
      }

      this.configureGSettings((String)this.performancemode.getValue());
   }

   private void configureGSettings(String mode) {
      GameSettings settings = mc.getMinecraft().gameSettings;
      settings.ofChunkUpdates = (Integer)this.chunkupdates.getValue();
      byte var4 = -1;
      switch(mode.hashCode()) {
      case -1818419758:
         if (mode.equals("Simple")) {
            var4 = 0;
         }
         break;
      case 359367820:
         if (mode.equals("Extreme")) {
            var4 = 1;
         }
      }

      switch(var4) {
      case 0:
         settings.fancyGraphics = false;
         break;
      case 1:
         settings.clouds = 0;
         settings.ofCloudsHeight = 0.0F;
         settings.fancyGraphics = false;
         settings.mipmapLevels = 0;
         settings.particleSetting = 2;
         settings.ofDynamicLights = 0;
         settings.ofSmoothBiomes = false;
         settings.field_181151_V = false;
         settings.ofTranslucentBlocks = 1;
         settings.ofDroppedItems = 1;
      }

   }
}
