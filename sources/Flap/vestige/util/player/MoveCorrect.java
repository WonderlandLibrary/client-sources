package vestige.util.player;

import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.Range;
import vestige.util.IMinecraft;
import vestige.util.world.BlockUtils;

public class MoveCorrect {
   public static final double MID_POS = 0.99999999999998D;
   private double moveStep;
   private final MoveCorrect.Mode mode;

   public MoveCorrect(double moveStep, MoveCorrect.Mode mode) {
      this.moveStep = moveStep;
      this.mode = mode;
   }

   public boolean isDoneX() {
      return this.isDoneX(0.99999999999998D);
   }

   public boolean isDoneZ() {
      return this.isDoneZ(0.99999999999998D);
   }

   public boolean moveX(boolean stopMotion) {
      return this.moveX(0.99999999999998D, stopMotion);
   }

   public boolean moveZ(boolean stopMotion) {
      return this.moveZ(0.99999999999998D, stopMotion);
   }

   public boolean isDoneX(double delta) {
      return IMinecraft.mc.thePlayer.posX == Math.floor(IMinecraft.mc.thePlayer.posX) + delta;
   }

   public boolean isDoneZ(double delta) {
      return IMinecraft.mc.thePlayer.posZ == Math.floor(IMinecraft.mc.thePlayer.posZ) + delta;
   }

   public boolean moveX(@Range(from = -1L,to = 1L) double pos, boolean stopMotion) {
      if (stopMotion) {
         IMinecraft.mc.thePlayer.motionX = 0.0D;
      }

      double targetPos = (double)(pos > 0.0D ? (long)MathHelper.floor_double(IMinecraft.mc.thePlayer.posX) : Math.round(IMinecraft.mc.thePlayer.posX)) + pos;
      if (IMinecraft.mc.thePlayer.posX != targetPos) {
         if (targetPos > IMinecraft.mc.thePlayer.posX) {
            this.doMove(Math.min(IMinecraft.mc.thePlayer.posX + this.moveStep, targetPos), IMinecraft.mc.thePlayer.posZ);
         } else {
            this.doMove(Math.max(IMinecraft.mc.thePlayer.posX - this.moveStep, targetPos), IMinecraft.mc.thePlayer.posZ);
         }
      }

      return this.isDoneX(pos);
   }

   public boolean moveZ(@Range(from = -1L,to = 1L) double pos, boolean stopMotion) {
      if (stopMotion) {
         IMinecraft.mc.thePlayer.motionZ = 0.0D;
      }

      double targetPos = (double)(pos > 0.0D ? (long)MathHelper.floor_double(IMinecraft.mc.thePlayer.posZ) : Math.round(IMinecraft.mc.thePlayer.posZ)) + pos;
      if (IMinecraft.mc.thePlayer.posZ != targetPos) {
         if (targetPos > IMinecraft.mc.thePlayer.posZ) {
            this.doMove(IMinecraft.mc.thePlayer.posX, Math.min(IMinecraft.mc.thePlayer.posZ + this.moveStep, targetPos));
         } else {
            this.doMove(IMinecraft.mc.thePlayer.posX, Math.max(IMinecraft.mc.thePlayer.posZ - this.moveStep, targetPos));
         }
      }

      return this.isDoneZ(pos);
   }

   private void doMove(double posX, double posZ) {
      switch(this.mode.ordinal()) {
      case 0:
         if (!BlockUtils.insideBlock(IMinecraft.mc.thePlayer.getEntityBoundingBox().offset(posX - IMinecraft.mc.thePlayer.posX, 0.0D, posZ - IMinecraft.mc.thePlayer.posZ))) {
            IMinecraft.mc.thePlayer.setPosition(posX, IMinecraft.mc.thePlayer.posY, posZ);
         }
         break;
      case 1:
         if (posX != IMinecraft.mc.thePlayer.posX) {
            IMinecraft.mc.thePlayer.motionX = posX - IMinecraft.mc.thePlayer.posX;
         }

         if (posZ != IMinecraft.mc.thePlayer.posZ) {
            IMinecraft.mc.thePlayer.motionZ = posZ - IMinecraft.mc.thePlayer.posZ;
         }
      }

   }

   public void setMoveStep(double moveStep) {
      this.moveStep = moveStep;
   }

   public static enum Mode {
      POSITION,
      MOTION;

      // $FF: synthetic method
      private static MoveCorrect.Mode[] $values() {
         return new MoveCorrect.Mode[]{POSITION, MOTION};
      }
   }
}
