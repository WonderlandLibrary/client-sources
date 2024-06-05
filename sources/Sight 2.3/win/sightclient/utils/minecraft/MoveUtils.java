package win.sightclient.utils.minecraft;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import win.sightclient.event.events.player.EventMove;

public class MoveUtils {

	protected static Minecraft mc = Minecraft.getMinecraft();
	
	public static boolean isMoving() {
		return Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown()
				|| Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown()
				|| Minecraft.getMinecraft().gameSettings.keyBindLeft.isKeyDown()
				|| Minecraft.getMinecraft().gameSettings.keyBindRight.isKeyDown();
	}
	
	public static boolean isBlockUnderneath(BlockPos pos) {
		for (int k = 0; k < pos.getY() + 1; k++) {
			if (mc.theWorld.getBlockState(new BlockPos(pos.getX(), k, pos.getZ())).getBlock().getMaterial() != Material.air) {
				return true;
			}
		}
		return false;
	}
	
    public static float getDirection() {
        float direction = mc.thePlayer.rotationYaw;
        boolean back =mc.gameSettings.keyBindBack.isKeyDown() && !mc.gameSettings.keyBindForward.isKeyDown();
        boolean forward =!mc.gameSettings.keyBindBack.isKeyDown() && mc.gameSettings.keyBindForward.isKeyDown();
        if (mc.gameSettings.keyBindLeft.isKeyDown()) {
        	direction -= (back ? 135 : (forward ? 45 : 90));
        } if (mc.gameSettings.keyBindRight.isKeyDown()) {
        	direction += (back ? 135 : (forward ? 45 : 90));
        }
        if (back && direction == mc.thePlayer.rotationYaw) {
        	direction += 180F;
        }
        return direction;
    }
    
    public static float getDirection2() {
        float direction = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0F)
            direction += 180.0F;
        float forward = 1.0F;
        if (mc.thePlayer.moveForward < 0.0F)
            forward = -0.5F;
        else if (mc.thePlayer.moveForward > 0.0F)
            forward = 0.5F;
        if (mc.thePlayer.moveStrafing > 0.0F)
            direction -= 90.0F * forward;
        else if (mc.thePlayer.moveStrafing < 0.0F)
            direction += 90.0F * forward;
        direction *= 0.017453292F;
        return direction;
    }
	
	public static void setMotion(EventMove em, double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
        	if (em != null) {
        		em.setX(0);
        		em.setZ(0);
        	} else {
                mc.thePlayer.motionX = 0.0;
                mc.thePlayer.motionZ = 0.0;
        	}
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
            if (em != null) {
                em.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90f)));
                em.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90f)));
            } else {
            	mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90f));
            	mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90f));
            }
        }
	}
	
	public static void setMotion(EventMove em, double speed, double strafeSpeed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe * strafeSpeed;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
        	if (em != null) {
        		em.setX(0);
        		em.setZ(0);
        	} else {
                mc.thePlayer.motionX = 0.0;
                mc.thePlayer.motionZ = 0.0;
        	}
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
            if (em != null) {
                em.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90f)));
                em.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90f)));
            } else {
            	mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90f));
            	mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90f));
            }
        }
	}
	
    public static double getBaseSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
	
	public static void setMotionWithValues(EventMove em, double speed, float yaw, double forward, double strafe) {
        if (forward == 0.0 && strafe == 0.0) {
        	if (em != null) {
        		em.setX(0);
        		em.setZ(0);
        	} else {
                mc.thePlayer.motionX = 0.0;
                mc.thePlayer.motionZ = 0.0;
        	}
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            if (em != null) {
                em.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90f)));
                em.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90f)));
            } else {
            	mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90f));
            	mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90f));
            }
        }
	}

    public static int getSpeedEffect() {
        if (MoveUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }

    public static double getJumpBoost(double jumpHeight) {
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            final int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            jumpHeight += (amplifier + 1) * 0.1f;
        }
        return jumpHeight;
    }
}
