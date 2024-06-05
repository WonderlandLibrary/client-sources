package win.sightclient.module.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldSettings.GameType;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.utils.minecraft.MoveUtils;

public class Phase extends Module {

	private ModeSetting mode = new ModeSetting("Mode", this, new String[] {"Hypixel", "Motion", "Teleport"});
	
	public Phase() {
		super("Phase", Category.MOVEMENT);
	}
	
	@Override
	public void onEvent(Event e) {
		this.setSuffix(this.mode.getValue());
		if (e instanceof EventMove) {
			EventMove em = (EventMove)e;
			if (this.mode.getValue().equalsIgnoreCase("Hypixel")) { // I like cock and balls
				if (mc.thePlayer.isCollidedHorizontally && MoveUtils.isMoving() && mc.thePlayer.onGround) {
					if (mc.timer.timerSpeed == 0.2F) {
						final float var2 = MoveUtils.getDirection();
						mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + (mc.thePlayer.motionX * 0.3925), mc.thePlayer.posY, mc.thePlayer.posZ + (mc.thePlayer.motionZ * 0.3925), mc.thePlayer.onGround));
						this.mc.thePlayer.setPosition(mc.thePlayer.posX + 0.8420 * Math.cos(Math.toRadians(var2 + 90.0f)), mc.thePlayer.posY, mc.thePlayer.posZ + 0.8420 * Math.sin(Math.toRadians(var2 + 90.0f)));
						for (int i = 0; i < 2; i++) {
	                    	if (this.isInsideBlock()) {
	                    		mc.timer.timerSpeed = 1F;
	                    		this.mc.thePlayer.setPosition(mc.thePlayer.posX + 0.3520 * Math.cos(Math.toRadians(var2 + 90.0f)), mc.thePlayer.posY, mc.thePlayer.posZ + 0.3520 * Math.sin(Math.toRadians(var2 + 90.0f)));
	                    	}
	                    }
						if (mc.timer.timerSpeed == 1F) {
							//mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + (mc.thePlayer.motionX * 0.3925), mc.thePlayer.posY, mc.thePlayer.posZ + (mc.thePlayer.motionZ * 0.3925), mc.thePlayer.onGround));
						}
						em.setY(0);
					} else {
						mc.thePlayer.moveForward *= 0.2F;
						mc.thePlayer.moveStrafing *= 0.2F;
						mc.timer.timerSpeed = 0.2F;
						mc.thePlayer.cameraPitch += 18;
						mc.thePlayer.cameraYaw += 1;
					}
				} else {
					mc.timer.timerSpeed = 1F;
				}
			} else if (this.mode.getValue().equalsIgnoreCase("Teleport")) {
				if (mc.thePlayer.isCollided && mc.thePlayer.onGround) {
					final float var2 = MoveUtils.getDirection();
					double ySet = 0;
					double xSet = 0.9420 * Math.cos(Math.toRadians(var2 + 90.0f));
					double zSet = 0.9420 * Math.sin(Math.toRadians(var2 + 90.0f));
					if (mc.thePlayer.onGround) {
						if (mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
							ySet = -0.3;
						}
					}
					if (!mc.thePlayer.isCollidedHorizontally) {
						xSet = 0;
						zSet = 0;
					}
					mc.timer.timerSpeed = ySet == 0 && xSet == 0 && zSet == 0 ? 01F : 0.8F;
					if (mc.timer.timerSpeed == 0.8F) {
						this.mc.thePlayer.setPosition(mc.thePlayer.posX + xSet, mc.thePlayer.posY + ySet, mc.thePlayer.posZ + zSet);
						for (int i = 0; i < 32; i++) {
	                    	if (this.isInsideBlock()) {
	                    		this.mc.thePlayer.setPosition(mc.thePlayer.posX + xSet, mc.thePlayer.posY + ySet, mc.thePlayer.posZ + zSet);
	                    	}
	                    }
						em.setY(0);
					} else {
						mc.thePlayer.moveForward *= 0.2F;
						mc.thePlayer.moveStrafing *= 0.2F;
					}
				} else {
					mc.timer.timerSpeed = 1F;
				}
			} else if (this.mode.getValue().equalsIgnoreCase("Motion")) {
				if (mc.thePlayer.onGround) {
					if (this.isInsideBlock()) {
						mc.thePlayer.noClip = true;
						em.setY(0);
						MoveUtils.setMotion(em, MoveUtils.getBaseSpeed() * 2.069);
						mc.thePlayer.isCollidedHorizontally = false;
					} else if (mc.thePlayer.isCollidedHorizontally) {
						mc.thePlayer.noClip = true;
						em.setY(0);
					} else {
						mc.thePlayer.noClip = false;
					}
				} else {
					mc.thePlayer.noClip = false;
				}
			} else if (this.mode.getValue().equalsIgnoreCase("Vanilla")) {
				if (this.lastPos != null) {
					if (this.isInsideBlock()) {
						mc.thePlayer.noClip = true;
					} else if ((mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isCollidedVertically) && mc.thePlayer.onGround) {
						mc.thePlayer.noClip = true;
					} else {
						mc.thePlayer.noClip = false;
					}
				} else {
					mc.thePlayer.noClip = false;
				}
			}
		} else if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate)e;
			if (eu.isPre() && this.mode.getValue().equalsIgnoreCase("Vanilla")) {
				if (this.lastPos != null) {
					if (this.isInsideBlock()) {
						mc.thePlayer.onGround = false;
						MoveUtils.setMotion(null, MoveUtils.getBaseSpeed());
						if (mc.thePlayer.ticksExisted % 150 != 0) {
							eu.setX(this.lastPos.getX());
							eu.setY(this.lastPos.getY());
							eu.setZ(this.lastPos.getZ()); 
						}
					} else {
						this.lastPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ);
					}
					if (mc.thePlayer.noClip) {
						if (mc.gameSettings.keyBindJump.isKeyDown() && !mc.gameSettings.keyBindSneak.isKeyDown()) {
							mc.thePlayer.motionY = 0.3;
						} else if (mc.gameSettings.keyBindSneak.isKeyDown() && !mc.gameSettings.keyBindJump.isKeyDown()) {
							mc.thePlayer.motionY = -0.3;
						} else {
							mc.thePlayer.onGround = false;
							mc.thePlayer.motionY = 0;
						}
					}
				} else {
					if (!this.isInsideBlock()) {
						this.lastPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY, mc.thePlayer.posZ);
					}
				}
			}
		}
	}
	
	private Vec3 lastPos;
	
	@Override
	public void onDisable() {
		super.onDisable();
		if (mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
			mc.thePlayer.noClip = false;
		}
		this.lastPos = null;
	}

    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX); x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); y < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; ++z) {
                    final Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld, new BlockPos(x, y, z), Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if (boundingBox != null && Minecraft.getMinecraft().thePlayer.boundingBox.intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
