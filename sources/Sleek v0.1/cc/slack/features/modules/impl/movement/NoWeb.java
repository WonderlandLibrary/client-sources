package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.entity.EntityPlayerSP;

@ModuleInfo(
   name = "NoWeb",
   category = Category.MOVEMENT
)
public class NoWeb extends Module {
   private final ModeValue<String> mode = new ModeValue(new String[]{"Vanilla", "Fast Fall", "Verus"});

   public NoWeb() {
      this.addSettings(new Value[]{this.mode});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (mc.getPlayer().isInWeb) {
         String var2 = ((String)this.mode.getValue()).toLowerCase();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -85473089:
            if (var2.equals("fast fall")) {
               var3 = 1;
            }
            break;
         case 112097665:
            if (var2.equals("verus")) {
               var3 = 2;
            }
            break;
         case 233102203:
            if (var2.equals("vanilla")) {
               var3 = 0;
            }
         }

         switch(var3) {
         case 0:
            mc.getPlayer().isInWeb = false;
            break;
         case 1:
            if (mc.getPlayer().onGround) {
               mc.getPlayer().jump();
            }

            if (mc.getPlayer().motionY > 0.0D) {
               EntityPlayerSP var10000 = mc.getPlayer();
               var10000.motionY -= mc.getPlayer().motionY * 2.0D;
            }
            break;
         case 2:
            MovementUtil.strafe(1.0F);
            if (!mc.getGameSettings().keyBindJump.isKeyDown() && !mc.getGameSettings().keyBindSneak.isKeyDown()) {
               mc.getPlayer().motionY = 0.0D;
            }

            if (mc.getGameSettings().keyBindJump.isKeyDown()) {
               mc.getPlayer().motionY = 4.42D;
            }

            if (mc.getGameSettings().keyBindSneak.isKeyDown()) {
               mc.getPlayer().motionY = -4.42D;
            }
         }

      }
   }
}
