package vestige.anticheat.impl;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockSlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import vestige.anticheat.ACPlayer;
import vestige.anticheat.AnticheatCheck;
import vestige.anticheat.PlayerData;

public class SpeedCheck extends AnticheatCheck {
   private float lastFriction;
   private boolean wasSneaking;

   public SpeedCheck(ACPlayer player) {
      super("Speed", player);
   }

   public void check() {
      PlayerData data = this.player.getData();
      EntityPlayer entity = this.player.getEntity();
      boolean sprinting = data.isSprinting();
      boolean sneaking = data.isSneaking();
      boolean onGround = data.isLastGround();
      boolean exempt = data.getDist() < 0.22D || entity.ticksExisted < 50 || entity.hurtTime >= 4 || mc.thePlayer.getDistanceToEntity(entity) > 20.0F || this.player.isBot();
      double predictedDist = data.getLastDist();
      if (this.wasSneaking || sneaking) {
         predictedDist = Math.max(predictedDist, 0.055D);
      }

      float friction = 0.91F;
      if (onGround) {
         friction = (float)((double)this.getFriction() * 0.91D);
      }

      float f = 0.16277136F / (friction * friction * friction);
      float attributeSpeed;
      if (onGround) {
         float speed = sprinting ? this.getWalkSpeed(entity) * 1.3F : this.getWalkSpeed(entity);
         attributeSpeed = speed * f;
      } else {
         attributeSpeed = sprinting ? 0.026F : 0.02F;
      }

      predictedDist *= (double)this.lastFriction;
      if (!data.isOnGround() && data.isLastGround() && data.getMotionY() >= 0.0D && sprinting) {
         predictedDist += 0.2D;
      }

      if (sneaking) {
         attributeSpeed = (float)((double)attributeSpeed * 0.3D);
      }

      if (entity.isUsingItem()) {
         attributeSpeed = (float)((double)attributeSpeed * 0.2D);
      }

      predictedDist += (double)attributeSpeed;
      double dist = data.getDist();
      double exceed = dist - predictedDist;
      double maxExceed = 0.02D;
      if (sneaking) {
         maxExceed += 0.005D;
      }

      if (!exempt) {
         if (exceed > maxExceed && dist > (double)this.getWalkSpeed(entity)) {
            if (this.increaseBuffer() >= 4.5D) {
               this.alert("Predicted dist : " + this.round(predictedDist) + " | Dist : " + this.round(dist));
            }
         } else {
            this.decreaseBufferBy(0.1D);
         }
      }

      this.lastFriction = friction;
      this.wasSneaking = sneaking;
   }

   private float getWalkSpeed(EntityPlayer entity) {
      return 0.1F + (float)this.getSpeedBoost(entity) * 0.02F;
   }

   private int getSpeedBoost(EntityPlayer entity) {
      return entity.isPotionActive(Potion.moveSpeed) ? entity.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
   }

   private float getFriction() {
      PlayerData data = this.player.getData();
      Block block = mc.theWorld.getBlockState(new BlockPos(data.getX(), Math.floor(data.getY()) - 1.0D, data.getZ())).getBlock();
      if (block != null) {
         if (block instanceof BlockIce || block instanceof BlockPackedIce) {
            return 0.98F;
         }

         if (block instanceof BlockSlime) {
            return 0.8F;
         }
      }

      return 0.6F;
   }
}
