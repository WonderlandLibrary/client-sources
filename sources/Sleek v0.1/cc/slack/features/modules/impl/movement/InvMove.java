package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.play.client.C16PacketClientStatus;

@ModuleInfo(
   name = "InvMove",
   category = Category.MOVEMENT
)
public class InvMove extends Module {
   private static final BooleanValue noOpen = new BooleanValue("Cancel Inventory Open", false);

   public InvMove() {
      this.addSettings(new Value[]{noOpen});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      MovementUtil.updateBinds();
   }

   @Listen
   public void onPacket(PacketEvent event) {
      if (event.getPacket() instanceof C16PacketClientStatus && (Boolean)noOpen.getValue() && event.getPacket() == new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT)) {
         event.cancel();
      }

   }
}
