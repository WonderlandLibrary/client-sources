package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.MathUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo(
   name = "SpeedMine",
   category = Category.PLAYER
)
public class SpeedMine extends Module {
   private final ModeValue<String> mode = new ModeValue(new String[]{"Vanilla", "Percent", "Instant", "NCP"});
   private final NumberValue<Double> percent = new NumberValue("Percent", 0.8D, 0.0D, 1.0D, 0.05D);
   private final NumberValue<Double> speed = new NumberValue("Speed", 1.0D, 0.1D, 2.0D, 0.1D);

   public SpeedMine() {
      this.addSettings(new Value[]{this.mode, this.percent, this.speed});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      boolean isValid = mc.getGameSettings().keyBindAttack.pressed && mc.getMinecraft().objectMouseOver != null && mc.getMinecraft().objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mc.getMinecraft().objectMouseOver.getBlockPos() != null;
      String var3 = (String)this.mode.getValue();
      byte var4 = -1;
      switch(var3.hashCode()) {
      case -672743999:
         if (var3.equals("Instant")) {
            var4 = 1;
         }
         break;
      case 77115:
         if (var3.equals("NCP")) {
            var4 = 3;
         }
         break;
      case 985725989:
         if (var3.equals("Percent")) {
            var4 = 2;
         }
         break;
      case 1897755483:
         if (var3.equals("Vanilla")) {
            var4 = 0;
         }
      }

      switch(var4) {
      case 0:
         if ((double)mc.getPlayerController().curBlockDamageMP * (Double)this.speed.getValue() > 1.0D) {
            mc.getPlayerController().curBlockDamageMP = 1.0F;
         }
         break;
      case 1:
         if (isValid) {
            mc.getPlayerController().curBlockDamageMP = 1.0F;
         }
         break;
      case 2:
         if ((double)mc.getPlayerController().curBlockDamageMP >= (Double)this.percent.getValue()) {
            mc.getPlayerController().curBlockDamageMP = 1.0F;
         }
         break;
      case 3:
         if (isValid && mc.getPlayerController().curBlockDamageMP >= 0.5F && !mc.getPlayer().isDead) {
            PlayerControllerMP var10000 = mc.getPlayerController();
            var10000.curBlockDamageMP += MathUtil.getDifference(mc.getPlayerController().curBlockDamageMP, 1.0F) * 0.7F;
         }
      }

   }
}
