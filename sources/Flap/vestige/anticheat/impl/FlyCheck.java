package vestige.anticheat.impl;

import net.minecraft.entity.player.EntityPlayer;
import vestige.anticheat.ACPlayer;
import vestige.anticheat.AnticheatCheck;
import vestige.anticheat.PlayerData;

public class FlyCheck extends AnticheatCheck {
   public FlyCheck(ACPlayer player) {
      super("Fly", player);
   }

   public void check() {
      PlayerData data = this.player.getData();
      EntityPlayer entity = this.player.getEntity();
      boolean closeToGround = data.isCloseToGround() || data.getCloseToGroundTicks() <= 2;
      boolean exempt = data.getDist() < 0.2D || entity.ticksExisted < 50 || entity.hurtTime >= 4 || mc.thePlayer.getDistanceToEntity(entity) > 20.0F || this.player.isBot() || entity.isInvisible() || entity.isInvisibleToPlayer(mc.thePlayer);
      if (!closeToGround && !exempt) {
         double motionY = data.getMotionY();
         double lastMotionY = data.getLastMotionY();
         double predictedMotionY = (lastMotionY - 0.08D) * 0.9800000190734863D;
         if (Math.abs(predictedMotionY) < 0.005D) {
            predictedMotionY = 0.0D;
         }

         double exceed = motionY - predictedMotionY;
         if (exceed > 0.01D && this.increaseBufferBy(exceed) > 0.2D) {
            this.alert("Exceed : " + exceed);
         }
      }

   }
}
