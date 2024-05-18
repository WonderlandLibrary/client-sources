package net.SliceClient.modules;

import net.minecraft.client.Minecraft;

public class Bhop extends net.SliceClient.module.Module {
  private double speed;
  private int stage;
  private boolean disabling;
  private boolean stopMotionUntilNext;
  private double moveSpeed;
  private boolean spedUp;
  public static boolean canStep;
  private double lastDist;
  public static double yOffset;
  private boolean cancel;
  
  public Bhop() {
    super("Bhop", net.SliceClient.module.Category.SPEED, 16376546);
  }
  











  public void onEnable()
  {
    com.darkmagician6.eventapi.EventManager.register(this);
    net.minecraft.util.Timer.timerSpeed = 1.0F;
    cancel = false;
    stage = 1;
    moveSpeed = (Minecraft.thePlayer == null ? 0.0873D : getBaseMoveSpeed());
    if (!disabling) {
      super.onEnable();
    }
  }
  
  public void onDisable()
  {
    com.darkmagician6.eventapi.EventManager.unregister(this);
    moveSpeed = getBaseMoveSpeed();
    yOffset = 0.0D;
    stage = 0;
    disabling = false;
    super.onDisable();
    thePlayerspeedInAir = 0.001F;
    thePlayerspeedOnGround = 0.001F;
    net.minecraft.util.Timer.timerSpeed = 1.0F;
    super.onDisable();
  }
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null))
      return;
    start();
    net.minecraft.util.Timer.timerSpeed = 1.0F;
    if (net.SliceClient.Utils.MathUtils.round(thePlayerposY - (int)thePlayerposY, 3) == net.SliceClient.Utils.MathUtils.round(0.138D, 3))
    {
      net.minecraft.client.entity.EntityPlayerSP thePlayer = Minecraft.thePlayer;
      motionY -= 100.08D;
      thePlayermotionY -= 6.09032596E-4D;
      net.minecraft.client.entity.EntityPlayerSP thePlayer2 = Minecraft.thePlayer;
      posY -= 100.0931609032596D;
    }
    if ((stage == 1) && ((thePlayermoveForward != 0.0F) || (thePlayermoveStrafing != 0.0F)))
    {
      stage = 2;
      moveSpeed = (1.38D * getBaseMoveSpeed() - 0.001D);
    }
    else if (stage == 2)
    {
      net.minecraft.util.Timer.timerSpeed = 1.1115F;
      stage = 3;
      thePlayermotionY = 0.3994D;
      moveSpeed *= 2.149D;
    }
    else if (stage == 3)
    {
      stage = 4;
      double difference = 0.066D * (lastDist - getBaseMoveSpeed());
      moveSpeed = (lastDist - difference);
    }
    else
    {
      if (thePlayerisCollidedVertically) {
        stage = 1;
      }
      moveSpeed = (lastDist - lastDist / 159.0D);
    }
    moveSpeed = Math.max(moveSpeed, getBaseMoveSpeed());
    net.minecraft.util.MovementInput movementInput = thePlayermovementInput;
    float forward = net.minecraft.util.MovementInput.moveForward;
    float strafe = net.minecraft.util.MovementInput.moveStrafe;
    Minecraft.getMinecraft();float yaw = thePlayerrotationYaw;
    if ((forward == 0.0F) && (strafe == 0.0F))
    {
      thePlayermotionX = 0.0D;
      thePlayermotionZ = 0.0D;
    }
    else if (forward != 0.0F)
    {
      if (strafe >= 1.0F)
      {
        yaw += (forward > 0.0F ? -45 : 45);
        strafe = 0.0F;
      }
      else if (strafe <= -1.0F)
      {
        yaw += (forward > 0.0F ? 45 : -45);
        strafe = 0.0F;
      }
      if (forward > 0.0F) {
        forward = 1.0F;
      } else if (forward < 0.0F) {
        forward = -1.0F;
      }
    }
    double mx = Math.cos(Math.toRadians(yaw + 90.0F));
    double mz = Math.sin(Math.toRadians(yaw + 90.0F));
    double motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
    double motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    thePlayermotionX = (forward * moveSpeed * mx + strafe * moveSpeed * mz);
    thePlayermotionZ = (forward * moveSpeed * mz - strafe * moveSpeed * mx);
  }
  
  @com.darkmagician6.eventapi.EventTarget
  private void start() {
    double xDist = thePlayerposX - thePlayerprevPosX;
    double zDist = thePlayerposZ - thePlayerprevPosZ;
    lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
  }
  

  private double getBaseMoveSpeed()
  {
    double baseSpeed = 0.1002873D;
    return baseSpeed;
  }
}
