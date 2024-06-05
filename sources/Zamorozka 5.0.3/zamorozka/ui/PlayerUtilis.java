package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import zamorozka.event.events.EventMove2;
import zamorozka.main.Zamorozka;

import java.util.List;

public class PlayerUtilis {
    public static Minecraft mc = Minecraft.getMinecraft();
    
    public static boolean playeriswalking() {
        if (PlayerUtilis.mc.gameSettings.keyBindForward.pressed) {
            return true;
        }
        if (PlayerUtilis.mc.gameSettings.keyBindBack.pressed) {
            return true;
        }
        if (PlayerUtilis.mc.gameSettings.keyBindLeft.pressed) {
            return true;
        }
        return PlayerUtilis.mc.gameSettings.keyBindRight.pressed;
    }

    public static boolean playeriswalkingforward() {
        if (PlayerUtilis.mc.gameSettings.keyBindBack.pressed) {
            return false;
        }
        if (PlayerUtilis.mc.gameSettings.keyBindLeft.pressed) {
            return false;
        }
        if (PlayerUtilis.mc.gameSettings.keyBindRight.pressed) {
            return false;
        }
        return PlayerUtilis.mc.gameSettings.keyBindForward.pressed;
    }

    public static void sendGround(boolean onGround) {
    	PlayerUtilis.mc.player.connection.sendPacket((Packet)new CPacketPlayer(onGround));
    }
    
    public static Entity raycast(Entity entiy) {
		Entity var2 = mc.player;
		Vec3d var9 = entiy.getPositionVector().add(new Vec3d(0, entiy.getEyeHeight(), 0));
		Vec3d var7 = mc.player.getPositionVector().addVector(0, mc.player.getEyeHeight(), 0);
		Vec3d var10 = null;
		float var11 = 1.0F;
		AxisAlignedBB a = mc.player.getEntityBoundingBox()
				.addCoord(var9.xCoord - var7.xCoord, var9.yCoord - var7.yCoord, var9.zCoord - var7.zCoord)
				.expand(var11, var11, var11);
		List var12 = mc.world.getEntitiesWithinAABBExcludingEntity(var2, a);
		double var13 = Zamorozka.settingsManager.getSettingByName("Range").getValDouble() + 0.45f;
		Entity b = null;
		for (int var15 = 0; var15 < var12.size(); ++var15) {
			Entity var16 = (Entity) var12.get(var15);

			if (var16.canBeCollidedWith()) {
				float var17 = var16.getCollisionBorderSize();
				AxisAlignedBB var18 = var16.getEntityBoundingBox().expand((double) var17, (double) var17,
						(double) var17);
				RayTraceResult var19 = var18.calculateIntercept(var7, var9);

				if (var18.isVecInside(var7)) {
					if (0.0D < var13 || var13 == 0.0D) {
						b = var16;
						var10 = var19 == null ? var7 : var19.hitVec;
						var13 = 0.0D;
					}
				} else if (var19 != null) {
					double var20 = var7.distanceTo(var19.hitVec);

					if (var20 < var13 || var13 == 0.0D) {
						b = var16;
						var10 = var19.hitVec;
						var13 = var20;
					}
				}
			}
		}
		return b;
	}
    
    public static void setSpeed(double speed) {
		PlayerUtil.mc.player.motionX = -(Math.sin(getDirection(0)) * speed);
		PlayerUtil.mc.player.motionZ = Math.cos(getDirection(0)) * speed;
	}
	public static void setSpeed(final EventMove2 moveEvent, final double moveSpeed) {
        setSpeed(moveEvent, moveSpeed, mc.player.rotationYaw, mc.player.movementInput.moveStrafe, mc.player.movementInput.moveForward);
    }

    public static void setSpeed(final EventMove2 moveEvent, final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;

        if (forward == 0.0 && strafe == 0.0) {
            moveEvent.setZ(0);
            moveEvent.setX(0);
        } else {
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
            final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            final double sin = Math.sin(Math.toRadians(yaw + 90.0f));

            moveEvent.setX((forward * moveSpeed * cos + strafe * moveSpeed * sin));
            moveEvent.setZ((forward * moveSpeed * sin - strafe * moveSpeed * cos));
        }
    }
    
    public static float getDirection(float yaw) {
		if (Minecraft.getMinecraft().player.moveForward < 0.0f) {
			yaw += 180.0f;
		}
		float forward = 1.0f;
		if (Minecraft.getMinecraft().player.moveForward < 0.0f) {
			forward = -0.5f;
		} else if (Minecraft.getMinecraft().player.moveForward > 0.0f) {
			forward = 0.5f;
		}
		if (Minecraft.getMinecraft().player.moveStrafing > 0.0f) {
			yaw -= 90.0f * forward;
		}
		if (Minecraft.getMinecraft().player.moveStrafing < 0.0f) {
			yaw += 90.0f * forward;
		}
		yaw *= 0.017453292f;
		return yaw;
	}
    
}
