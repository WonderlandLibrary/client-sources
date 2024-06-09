package lunadevs.luna.utils;

import com.zCoreEvent.events.MoveEvent;

import lunadevs.luna.events.PacketSendEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;

public class MoveUtils {
	  public static void tpRel(double x, double y, double z)
	  {
	    EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
	    player.setPosition(player.posX + x, player.posY + y, player.posZ + z);
	  }
	  
	  public static void tpPacket(double x, double y, double z)
	  {
	    EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
	    player.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(player.posX + x, player.posY + y, player.posZ + z, false));
	    player.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY, player.posZ, false));
	    player.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY, player.posZ, true));
	  }
	  
	  public static void setSpeed(float speed)
	  {
	    EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
	    double yaw = player.rotationYaw;
	    boolean isMoving = (player.moveForward != 0.0F) || (player.moveStrafing != 0.0F);
	    boolean isMovingForward = player.moveForward > 0.0F;
	    boolean isMovingBackward = player.moveForward < 0.0F;
	    boolean isMovingRight = player.moveStrafing > 0.0F;
	    boolean isMovingLeft = player.moveStrafing < 0.0F;
	    boolean isMovingSideways = (isMovingLeft) || (isMovingRight);
	    boolean isMovingStraight = (isMovingForward) || (isMovingBackward);
	    if (isMoving)
	    {
	      if ((isMovingForward) && (!isMovingSideways)) {
	        yaw += 0.0D;
	      } else if ((isMovingBackward) && (!isMovingSideways)) {
	        yaw += 180.0D;
	      } else if ((isMovingForward) && (isMovingLeft)) {
	        yaw += 45.0D;
	      } else if (isMovingForward) {
	        yaw -= 45.0D;
	      } else if ((!isMovingStraight) && (isMovingLeft)) {
	        yaw += 90.0D;
	      } else if ((!isMovingStraight) && (isMovingRight)) {
	        yaw -= 90.0D;
	      } else if ((isMovingBackward) && (isMovingLeft)) {
	        yaw += 135.0D;
	      } else if (isMovingBackward) {
	        yaw -= 135.0D;
	      }
	      yaw = Math.toRadians(yaw);
	      player.motionX = (-Math.sin(yaw) * speed);
	      player.motionZ = (Math.cos(yaw) * speed);
	    }
	  }
	  
	  public static MovementInput movementInput() {
	        return Minecraft.getMinecraft().thePlayer.movementInput;
	    }
	  
	  public static float yaw() {
	        return Minecraft.getMinecraft().thePlayer.rotationYaw;
	    }

	  public static void setMSpeed(final MoveEvent event, final double speed) {
	        double forward = movementInput().moveForward;
	        double strafe = movementInput().moveStrafe;
	        float yaw = yaw();
	        if (forward == 0.0 && strafe == 0.0) {
	            event.setX(0.0);
	            event.setZ(0.0);
	        }
	        else {
	            if (forward != 0.0) {
	                if (strafe > 0.0) {
	                    yaw += ((forward > 0.0) ? -45 : 45);
	                }
	                else if (strafe < 0.0) {
	                    yaw += ((forward > 0.0) ? 45 : -45);
	                }
	                strafe = 0.0;
	                if (forward > 0.0) {
	                    forward = 1.0;
	                }
	                else if (forward < 0.0) {
	                    forward = -1.0;
	                }
	            }
	            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
	            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
	        }
	    }
}