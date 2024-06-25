package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
   name = "Step",
   category = Category.MOVEMENT
)
public class Step extends Module {
   private final ModeValue<String> mode = new ModeValue("Step Mode", new String[]{"Vanilla", "NCP", "Verus", "Vulcan"});
   private final NumberValue<Float> timerSpeed = new NumberValue("Timer", 1.0F, 0.0F, 2.0F, 0.05F);

   public Step() {
      this.addSettings(new Value[]{this.mode, this.timerSpeed});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (mc.getPlayer().isCollidedHorizontally && mc.getPlayer().onGround) {
         mc.getTimer().timerSpeed = (Float)this.timerSpeed.getValue();
         String var2 = ((String)this.mode.getValue()).toLowerCase();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -805359837:
            if (var2.equals("vulcan")) {
               var3 = 3;
            }
            break;
         case 108891:
            if (var2.equals("ncp")) {
               var3 = 2;
            }
            break;
         case 112097665:
            if (var2.equals("verus")) {
               var3 = 1;
            }
            break;
         case 233102203:
            if (var2.equals("vanilla")) {
               var3 = 0;
            }
         }

         switch(var3) {
         case 0:
            mc.getPlayer().stepHeight = 1.0F;
            break;
         case 1:
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.getPlayer().posX, mc.getPlayer().posY + 0.41999998688697815D, mc.getPlayer().posZ, mc.getPlayer().onGround));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.getPlayer().posX, mc.getPlayer().posY + 0.8500000238418579D, mc.getPlayer().posZ, mc.getPlayer().onGround));
            mc.getPlayer().stepHeight = 1.0F;
            mc.getTimer().timerSpeed = 0.91F;
            break;
         case 2:
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.getPlayer().posX, mc.getPlayer().posY + 0.41999998688697815D, mc.getPlayer().posZ, mc.getPlayer().onGround));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.getPlayer().posX, mc.getPlayer().posY + 0.7531999945640564D, mc.getPlayer().posZ, mc.getPlayer().onGround));
            mc.getPlayer().stepHeight = 1.0F;
            break;
         case 3:
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.getPlayer().posX, mc.getPlayer().posY + 0.5D, mc.getPlayer().posZ, mc.getPlayer().onGround));
            mc.getPlayer().stepHeight = 1.0F;
         }
      } else {
         mc.getPlayer().stepHeight = 0.5F;
         mc.getTimer().timerSpeed = 1.0F;
      }

   }
}
