package Squad.Utils;

import net.minecraft.client.Minecraft;

public class MoveUtils {

	
	 public static void setSpeed(float speed)
	   {
	     double yaw =  Minecraft.getMinecraft().thePlayer.rotationYaw;
	     boolean isMoving = ( Minecraft.getMinecraft().thePlayer.moveForward != 0.0F) || ( Minecraft.getMinecraft().thePlayer.moveStrafing != 0.0F);
	     boolean isMovingForward =  Minecraft.getMinecraft().thePlayer.moveForward > 0.0F;
	     boolean isMovingBackward =  Minecraft.getMinecraft().thePlayer.moveForward < 0.0F;
	     boolean isMovingRight =  Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0F;
	     boolean isMovingLeft =  Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0F;
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
	       Minecraft.getMinecraft().thePlayer.motionX = (-Math.sin(yaw) * speed);
	       Minecraft.getMinecraft().thePlayer.motionZ = (Math.cos(yaw) * speed);
	     }
	   }
	}
	

