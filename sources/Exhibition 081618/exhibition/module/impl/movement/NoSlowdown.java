package exhibition.module.impl.movement;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.module.impl.combat.Killaura;
import exhibition.util.PlayerUtil;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowdown extends Module {
   public NoSlowdown(ModuleData data) {
      super(data);
      this.settings.put("VANILLA", new Setting("VANILLA", true, "Vanilla. No send packetorinos"));
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      EventMotionUpdate em = (EventMotionUpdate)event;
      if (!((Module)Client.getModuleManager().get(Killaura.class)).isEnabled() && mc.thePlayer.isBlocking() && PlayerUtil.isMoving() && !((Boolean)((Setting)this.settings.get("VANILLA")).getValue()).booleanValue()) {
         if (em.isPre()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
         }

         if (em.isPost()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
         }
      }

   }
}
