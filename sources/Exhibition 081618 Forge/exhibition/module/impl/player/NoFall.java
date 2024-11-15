package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class NoFall extends Module {
   public NoFall(ModuleData data) {
      super(data);
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPre() && mc.thePlayer.motionY < 0.0D && (double)mc.thePlayer.fallDistance > 3.5D && this.isBlockUnder() && !mc.thePlayer.isCollidedVertically) {
            if (mc.thePlayer.fallDistance <= 20.0F) {
               em.setGround(true);
            }

            if (mc.thePlayer.fallDistance <= 20.0F || mc.thePlayer.ticksExisted % 3 == 0) {
               mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
         }
      }

   }

   private boolean isBlockUnder() {
      for(int i = (int)(mc.thePlayer.posY - 1.0D); i > 0; --i) {
         BlockPos pos = new BlockPos(mc.thePlayer.posX, (double)i, mc.thePlayer.posZ);
         if (!(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)) {
            return true;
         }
      }

      return false;
   }
}
