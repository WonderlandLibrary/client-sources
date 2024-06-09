package exhibition.module.impl.movement;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventLiquidCollide;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventPacket;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class Jesus extends Module {
   int ticks;

   public Jesus(ModuleData data) {
      super(data);
   }

   public void onEnable() {
      super.onEnable();
      this.ticks = 0;
   }

   public void onDisable() {
      super.onDisable();
      this.ticks = 0;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class, EventPacket.class, EventLiquidCollide.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event.cast();
         if (em.isPre() && PlayerUtil.isOnLiquid() && !PlayerUtil.isInLiquid() && !mc.thePlayer.isSneaking() && !mc.gameSettings.keyBindJump.isPressed()) {
            if (this.ticks == 0 && PlayerUtil.isOnLiquid() && PlayerUtil.isMoving()) {
               mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
               mc.thePlayer.setSprinting(false);
               this.ticks = 1;
            }

            if (mc.thePlayer.fallDistance == 0.0F) {
               em.setY(em.getY() + (mc.thePlayer.ticksExisted % 2 == 0 ? 9.51753852431554E-13D : -9.51753852431554E-13D));
            }
         }

         if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() == Blocks.lava) {
            em.setGround(mc.thePlayer.ticksExisted % 2 != 0);
         }

         if (!this.shouldSetBoundingBox() && PlayerUtil.isInLiquid()) {
            mc.thePlayer.fallDistance = 0.0F;
            mc.thePlayer.motionY = mc.thePlayer.isSneaking() ? -0.13D : 0.1D;
         }

         if (this.ticks == 1 && !PlayerUtil.isOnLiquid() && !PlayerUtil.isInLiquid()) {
            this.ticks = 0;
         }

         if (PlayerUtil.isOnLiquid() && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.1D, mc.thePlayer.posZ)).getBlock() == Blocks.lava && !PlayerUtil.isMoving()) {
            em.setCancelled(true);
         }
      }

      if (event instanceof EventLiquidCollide) {
         EventLiquidCollide ebb = (EventLiquidCollide)event.cast();
         if ((double)ebb.getPos().getY() + 0.9D < mc.thePlayer.boundingBox.minY) {
            ebb.setBounds(new AxisAlignedBB((double)ebb.getPos().getX(), (double)ebb.getPos().getY(), (double)ebb.getPos().getZ(), (double)(ebb.getPos().getX() + 1), (double)(ebb.getPos().getY() + 1), (double)(ebb.getPos().getZ() + 1)));
            ebb.setCancelled(this.shouldSetBoundingBox());
         }
      }

   }

   private boolean shouldSetBoundingBox() {
      return !mc.thePlayer.isSneaking() && mc.thePlayer.fallDistance < 4.0F && !PlayerUtil.isInLiquid();
   }
}
