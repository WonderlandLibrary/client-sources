package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.BlinkUtil;
import io.github.nevalackin.radbus.Listen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

@ModuleInfo(
   name = "TestBlink",
   category = Category.PLAYER
)
public class TestBlink extends Module {
   List<GameSettings> inputReplay = new ArrayList();
   private Minecraft startingMC = mc.getMinecraft();
   private int ticks = 0;

   public void onEnable() {
      BlinkUtil.enable(true, true);
      this.inputReplay.clear();
      this.startingMC = mc.getMinecraft();
      this.ticks = 0;
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      this.inputReplay.add(mc.getGameSettings());
      ++this.ticks;
   }

   public void onDisable() {
      BlinkUtil.clearPackets(false, true);
      BlinkUtil.setConfig(true, false);
      Minecraft.setMinecraft(this.startingMC);

      for(int i = 1; i <= this.ticks; ++i) {
         try {
            mc.getMinecraft().gameSettings = (GameSettings)this.inputReplay.get(i);
            mc.getMinecraft().runTick();
         } catch (IOException var3) {
         }
      }

      BlinkUtil.disable(true);
   }
}
