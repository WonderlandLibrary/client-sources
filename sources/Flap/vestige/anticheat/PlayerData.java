package vestige.anticheat;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import vestige.util.IMinecraft;

public class PlayerData implements IMinecraft {
   private final EntityPlayer player;
   private double x;
   private double y;
   private double z;
   private double lastX;
   private double lastY;
   private double lastZ;
   private double motionX;
   private double motionY;
   private double motionZ;
   private double lastMotionX;
   private double lastMotionY;
   private double lastMotionZ;
   private double dist;
   private double lastDist;
   private boolean onGround;
   private boolean lastGround;
   private boolean lastLastGround;
   private boolean mathGround;
   private boolean lastMathGround;
   private boolean lastLastMathGround;
   private float yaw;
   private float pitch;
   private float lastYaw;
   private float lastPitch;
   private float yawChange;
   private float pitchChange;
   private boolean sprinting;
   private boolean sneaking;
   private boolean lastSprinting;
   private boolean lastSneaking;
   private boolean collidedHorizontally;
   private boolean lastCollidedHorizontally;
   private boolean closeToGround;
   private int closeToGroundTicks;

   public PlayerData(EntityPlayer player) {
      this.player = player;
   }

   public void updateInfos() {
      this.updatePosition();
      this.updateRotation();
      this.updateActions();
   }

   private void updatePosition() {
      this.lastX = this.x;
      this.lastY = this.y;
      this.lastZ = this.z;
      this.x = this.player.posX;
      this.y = this.player.posY;
      this.z = this.player.posZ;
      this.lastLastGround = this.lastGround;
      this.lastGround = this.onGround;
      this.onGround = this.player.onGround;
      this.lastLastMathGround = this.lastMathGround;
      this.lastMathGround = this.mathGround;
      this.mathGround = this.y % 0.0625D == 0.0D;
      this.lastMotionX = this.motionX;
      this.lastMotionY = this.motionY;
      this.lastMotionZ = this.motionZ;
      this.motionX = this.x - this.lastX;
      this.motionY = this.y - this.lastY;
      this.motionZ = this.z - this.lastZ;
      this.lastDist = this.dist;
      this.dist = Math.hypot(this.motionX, this.motionZ);
      this.lastCollidedHorizontally = this.collidedHorizontally;
      this.collidedHorizontally = this.player.isCollidedHorizontally;
      this.findBlock();
   }

   private void findBlock() {
      boolean foundBlock = false;
      ++this.closeToGroundTicks;

      for(double x2 = this.x - 1.0D; x2 <= this.x + 1.0D; ++x2) {
         for(double y2 = this.y - 1.0D; y2 <= this.y + 1.0D; ++y2) {
            for(double z2 = this.z - 1.0D; z2 <= this.z + 1.0D; ++z2) {
               BlockPos pos = new BlockPos(x2, y2, z2);
               Block block = mc.theWorld.getBlockState(pos).getBlock();
               if (block != null) {
                  Material material = block.getMaterial();
                  if (material != Material.air && material != Material.water && material != Material.lava) {
                     foundBlock = true;
                  }
               }
            }
         }
      }

      if (foundBlock != this.closeToGround) {
         this.closeToGroundTicks = 0;
      }

      this.closeToGround = foundBlock;
   }

   private void updateRotation() {
      this.lastYaw = this.yaw;
      this.lastPitch = this.pitch;
      this.yaw = this.player.rotationYaw;
      this.pitch = this.player.rotationPitch;
      this.yawChange = this.yaw - this.lastYaw;
      this.pitchChange = this.pitch - this.lastPitch;
   }

   private void updateActions() {
      this.lastSprinting = this.sprinting;
      this.lastSneaking = this.sneaking;
      this.sprinting = this.player.isSprinting();
      this.sneaking = this.player.isSneaking();
   }

   public EntityPlayer getPlayer() {
      return this.player;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public double getLastX() {
      return this.lastX;
   }

   public double getLastY() {
      return this.lastY;
   }

   public double getLastZ() {
      return this.lastZ;
   }

   public double getMotionX() {
      return this.motionX;
   }

   public double getMotionY() {
      return this.motionY;
   }

   public double getMotionZ() {
      return this.motionZ;
   }

   public double getLastMotionX() {
      return this.lastMotionX;
   }

   public double getLastMotionY() {
      return this.lastMotionY;
   }

   public double getLastMotionZ() {
      return this.lastMotionZ;
   }

   public double getDist() {
      return this.dist;
   }

   public double getLastDist() {
      return this.lastDist;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public boolean isLastGround() {
      return this.lastGround;
   }

   public boolean isLastLastGround() {
      return this.lastLastGround;
   }

   public boolean isMathGround() {
      return this.mathGround;
   }

   public boolean isLastMathGround() {
      return this.lastMathGround;
   }

   public boolean isLastLastMathGround() {
      return this.lastLastMathGround;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public float getLastYaw() {
      return this.lastYaw;
   }

   public float getLastPitch() {
      return this.lastPitch;
   }

   public float getYawChange() {
      return this.yawChange;
   }

   public float getPitchChange() {
      return this.pitchChange;
   }

   public boolean isSprinting() {
      return this.sprinting;
   }

   public boolean isSneaking() {
      return this.sneaking;
   }

   public boolean isLastSprinting() {
      return this.lastSprinting;
   }

   public boolean isLastSneaking() {
      return this.lastSneaking;
   }

   public boolean isCollidedHorizontally() {
      return this.collidedHorizontally;
   }

   public boolean isLastCollidedHorizontally() {
      return this.lastCollidedHorizontally;
   }

   public boolean isCloseToGround() {
      return this.closeToGround;
   }

   public int getCloseToGroundTicks() {
      return this.closeToGroundTicks;
   }
}
