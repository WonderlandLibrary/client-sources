package net.SliceClient.event;

import com.darkmagician6.eventapi.events.Event;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Consumer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovementInput;

public class EventMotionUpdate
  implements Event
{
  private double x;
  private double minY;
  private double y;
  private double z;
  private double oldX;
  private double oldY;
  private double oldZ;
  public float yaw;
  public float pitch;
  private float oldYaw;
  private float oldPitch;
  private float moveForward;
  private float moveStrafing;
  private boolean sneaking;
  private boolean sprinting;
  private boolean onGround;
  private Queue<Consumer<EntityPlayerSP>> postponeActions;
  private MovementInput movementInput;
  
  public EventMotionUpdate(double x, double minY, double y, double z, double oldX, double oldY, double oldZ, float yaw, float pitch, float oldYaw, float oldPitch, boolean sprinting, boolean sneaking, boolean onGround, float moveForward, float moveStrafing, MovementInput movementInput)
  {
    this.x = x;
    this.y = y;
    this.minY = minY;
    this.z = z;
    this.oldX = oldX;
    this.oldY = oldY;
    this.oldZ = oldZ;
    this.yaw = yaw;
    this.pitch = pitch;
    this.oldYaw = oldYaw;
    this.oldPitch = oldPitch;
    this.onGround = onGround;
    postponeActions = new ArrayDeque();
    this.moveForward = moveStrafing;
    this.moveStrafing = moveStrafing;
    this.movementInput = movementInput;
  }
  
  public double getOldX()
  {
    return oldX;
  }
  
  public double getOldY()
  {
    return oldY;
  }
  
  public double getOldZ()
  {
    return oldZ;
  }
  
  public double getX()
  {
    return x;
  }
  
  public void setX(double x)
  {
    this.x = x;
  }
  
  public double getY()
  {
    return y;
  }
  
  public void setY(double y)
  {
    this.y = y;
  }
  
  public double getMinY()
  {
    return minY;
  }
  
  public void setMinY(double y)
  {
    minY = y;
  }
  
  public double getZ()
  {
    return z;
  }
  
  public void setZ(double z)
  {
    this.z = z;
  }
  
  public float getYaw()
  {
    return yaw;
  }
  
  public void setYaw(float yaw)
  {
    this.yaw = yaw;
  }
  
  public float getPitch()
  {
    return pitch;
  }
  
  public void setPitch(float pitch)
  {
    this.pitch = pitch;
  }
  
  public boolean isOnGround()
  {
    return onGround;
  }
  
  public void setOnGround(boolean onGround)
  {
    this.onGround = onGround;
  }
  
  public boolean isSprinting()
  {
    return sprinting;
  }
  
  public void setSprinting(boolean sprinting)
  {
    this.sprinting = sprinting;
  }
  
  public boolean isSneaking()
  {
    return sneaking;
  }
  
  public void setSneaking(boolean sneaking)
  {
    this.sneaking = sneaking;
  }
  
  public boolean isMoving()
  {
    double x = this.x - oldX;
    double y = this.y - oldY;
    double z = this.z - oldZ;
    return x * x + y * y + z * z > 9.0E-4D;
  }
  
  public boolean isRotating()
  {
    double yaw = this.yaw - oldYaw;
    double pitch = this.pitch - oldPitch;
    return (yaw != 0.0D) || (pitch != 0.0D);
  }
  
  public Queue<Consumer<EntityPlayerSP>> getPostponeActions()
  {
    return postponeActions;
  }
  
  public MovementInput getMovementInput()
  {
    return movementInput;
  }
  
  public float getStrafe()
  {
    return MovementInput.moveStrafe;
  }
  
  public float getForward()
  {
    return MovementInput.moveForward;
  }
  
  public void setForward(float forward)
  {
    MovementInput.moveForward = forward;
  }
  
  public void setStrafe(float strafe)
  {
    MovementInput.moveStrafe = strafe;
  }
}
