package cc.slack.features.modules.impl.other;

import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
   name = "Tweaks",
   category = Category.OTHER
)
public class Tweaks extends Module {
   public final BooleanValue noachievement = new BooleanValue("No Achievement", true);
   public final BooleanValue noblockhitdelay = new BooleanValue("No Block Hit Delay", false);
   public final BooleanValue noclickdelay = new BooleanValue("No Click Delay", true);
   public final BooleanValue nojumpdelay = new BooleanValue("No Jump Delay", false);
   public final NumberValue<Integer> noJumpDelayTicks = new NumberValue("Jump Delay Ticks", 0, 0, 10, 1);
   public final BooleanValue nobosshealth = new BooleanValue("No Boss Health", false);
   public final BooleanValue nohurtcam = new BooleanValue("No Hurt Cam", false);
   private final BooleanValue fullbright = new BooleanValue("FullBright", true);
   private final BooleanValue exitGUIFix = new BooleanValue("Exit Gui Fix", true);
   public final BooleanValue noPumpkin = new BooleanValue("No Pumpkin", true);
   public final BooleanValue customTitle = new BooleanValue("Custom Title", false);
   float prevGamma = -1.0F;
   boolean wasGUI = false;

   public Tweaks() {
      this.addSettings(new Value[]{this.noachievement, this.noblockhitdelay, this.noclickdelay, this.nohurtcam, this.fullbright, this.nobosshealth, this.nojumpdelay, this.noJumpDelayTicks, this.exitGUIFix, this.noPumpkin, this.customTitle});
   }

   public void onEnable() {
      this.prevGamma = mc.getGameSettings().gammaSetting;
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if ((Boolean)this.fullbright.getValue()) {
         if (mc.getGameSettings().gammaSetting <= 100.0F) {
            ++mc.getGameSettings().gammaSetting;
         }
      } else if (this.prevGamma != -1.0F) {
         mc.getGameSettings().gammaSetting = this.prevGamma;
         this.prevGamma = -1.0F;
      }

      if ((Boolean)this.exitGUIFix.getValue()) {
         if (mc.getCurrentScreen() == null) {
            if (this.wasGUI) {
               MovementUtil.updateBinds();
            }

            this.wasGUI = false;
         } else {
            this.wasGUI = true;
         }
      }

      if ((Boolean)this.noclickdelay.getValue()) {
         mc.getMinecraft().leftClickCounter = 0;
      }

   }

   @Listen
   public void onMotion(MotionEvent event) {
      if ((Boolean)this.noblockhitdelay.getValue()) {
         mc.getPlayerController().blockHitDelay = 0;
      }

   }

   public void onDisable() {
      if (this.prevGamma != -1.0F) {
         mc.getGameSettings().gammaSetting = this.prevGamma;
         this.prevGamma = -1.0F;
      }
   }
}
