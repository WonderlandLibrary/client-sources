package none.module.modules.movement;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.MobEffects;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.HWID;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event3D;
import none.event.events.EventMove;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventStep;
import none.module.Category;
import none.module.Module;
import none.module.modules.render.ClientColor;
import none.module.modules.world.Scaffold;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.MoveUtils;
import none.utils.RenderingUtil;
import none.utils.TimeHelper;
import none.utils.Utils;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class Fly extends Module {

	private String[] modes = { "Vanilla", "Motion", "Zoom", "MC-Central", "Hypixel", "Cubecraft", "OldSekSin" };
	private ModeValue flymode = new ModeValue("Fly-Mode", "Zoom", modes);
	private BooleanValue hypixelfast = new BooleanValue("Hypixel-Fast", false);
	private NumberValue<Double> hypixelspeed = new NumberValue<>("Hypixel-Speed", 1.0, 0.1, 5.0);
	private BooleanValue esp = new BooleanValue("ESP", true);
	public static BooleanValue bobbing = new BooleanValue("Bobbing", true);

	public Fly() {
		super("Fly", "Fly", Category.MOVEMENT, Keyboard.KEY_F);
	}

	TimeHelper timer = new TimeHelper();
	TimeHelper hypixeltimer = new TimeHelper();
	public static double hypixel = 0;
	private float flytimer = 0;
	public static int fastFlew;
	int stage;
	boolean shouldSpeed;
	private double flyHeight;
	private double startY;
	private int count;
	private boolean failedStart = false;

	@Override
	protected void onEnable() {
		super.onEnable();
		count = 0;
		stage = 0;
		hypixel = 0;
		mc.timer.timerSpeed = 1f;
		timer.setLastMS();
		hypixeltimer.setLastMS();
		if (mc.thePlayer == null)
			return;

		if (flymode.getSelected().equalsIgnoreCase("Cubecraft") && !HWID.isHWID()) {
			evc("Premium Only");
			Client.instance.notification.show(Client.notification("Premium", "You are not Premium", 3));
			setState(false);
			return;
		}

		if (flymode.getSelected().equalsIgnoreCase("Hypixel") && !HWID.isHWID()) {
			evc("Premium Only");
			Client.instance.notification.show(Client.notification("Premium", "You are not Premium", 3));
			setState(false);
			return;
		}

		if (flymode.getSelected().equalsIgnoreCase("Hypixel") && hypixelfast.getObject()
				&& MoveUtils.isOnGround(0.01)) {
			fastFlew = 0;
			for (int i = 0; i < 10; i++) // Imagine flagging to NCP.
				mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
						mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));

			double fallDistance = 3.0125; // add 0.0125 to ensure we get the fall dmg
			while (fallDistance > 0) {
				mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
						mc.thePlayer.posX, mc.thePlayer.posY + 0.0624986421, mc.thePlayer.posZ, false));
				mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
						mc.thePlayer.posX, mc.thePlayer.posY + 0.0625, mc.thePlayer.posZ, false));
				mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
						mc.thePlayer.posX, mc.thePlayer.posY + 0.0624986421, mc.thePlayer.posZ, false));
				mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
						mc.thePlayer.posX, mc.thePlayer.posY + 0.0000013579, mc.thePlayer.posZ, false));
				fallDistance -= 0.0624986421;
			}
			mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
					mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
			fastFlew = 10;
//        	mc.thePlayer.jump();
			mc.thePlayer.posY += 0.42;
			mc.thePlayer.motionY = 0.41999998688698f + MoveUtils.getJumpEffect() * 0.1;
			MoveUtils.setMotion(0.3 + MoveUtils.getSpeedEffect() * 0.05f);
			hypixel = 13 + hypixelspeed.getObject();
		} else {
			fastFlew = 100;
		}

		failedStart = false;

		if (flymode.getSelected().equalsIgnoreCase("Cubecraft") && mc.thePlayer.onGround) {
//			for (int i = 0; i < 10; i++) // Imagine flagging to NCP.
//				mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
//						mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
//			for (int i = 0; i < 64; i++) {
//				mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
//						mc.thePlayer.posX, mc.thePlayer.posY + 0.0625, mc.thePlayer.posZ, false));
//				mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
//						mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
//			}
//			mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(
//					mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
////        	if (mc.thePlayer.onGround) {
//        		mc.thePlayer.jump();
			mc.thePlayer.motionY = 0.1;
////        		mc.thePlayer.motionY = 0.41999998688698f + MoveUtils.getJumpEffect()*0.1;
////        	}
		}

		startY = mc.thePlayer.posY;

	}

	public void damagePlayer(int damage) {
		if (damage < 1)
			damage = 1;
		if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth()))
			damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());

		double offset = 0.0625;
		if (mc.thePlayer != null && mc.getConnection() != null && mc.thePlayer.onGround) {
			for (int i = 0; i <= ((3 + damage) / offset); i++) { // TODO: teach rederpz (and myself) how math works
				mc.getConnection().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
				mc.getConnection().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY, mc.thePlayer.posZ, (i == ((3 + damage) / offset))));
			}
		}
	}

	@Override
	protected void onDisable() {
		super.onDisable();
		count = 0;
		fastFlew = 100;
		if (mc.thePlayer == null)
			return;
		MoveUtils.setMotion(0.2);
		mc.thePlayer.jumpMovementFactor = 0;
		mc.timer.timerSpeed = 1f;
		mc.thePlayer.capabilities.isFlying = false;
//        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.01, mc.thePlayer.posZ, true));
//        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.01, mc.thePlayer.posZ, true));
	}

	private void setSpecialMotion(double speed) {
		double forward = mc.thePlayer.movementInput.moveForward;
		double strafe = mc.thePlayer.movementInput.moveStrafe;
		float yaw = mc.thePlayer.rotationYaw;
		if ((forward == 0.0D) && (strafe == 0.0D)) {
			mc.thePlayer.motionX = 0;
			mc.thePlayer.motionZ = 0;
		} else {
			if (forward != 0.0D) {
				if (hypixel <= 0)
					if (strafe > 0.0D) {
						yaw += (forward > 0.0D ? -45 : 45);
					} else if (strafe < 0.0D) {
						yaw += (forward > 0.0D ? 45 : -45);
					}
				strafe = 0.0D;
				if (forward > 0.0D) {
					forward = 1;
				} else if (forward < 0.0D) {
					forward = -1;
				}
			}
			mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F))
					+ strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
			mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F))
					- strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
		}
	}

	public double getBaseMoveSpeed() {
		double baseSpeed = 0.2873D;
		if (mc.thePlayer.isPotionActive(MobEffects.SPEED)) {
			int amplifier = mc.thePlayer.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
			baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
		}
		return baseSpeed;
	}

	@Override
	@RegisterEvent(events = { EventPreMotionUpdate.class, EventPacket.class, Event3D.class, EventMove.class,
			EventStep.class })
	public void onEvent(Event event) {
		if (!isEnabled())
			return;

		setDisplayName(getName() + ChatFormatting.WHITE + " " + flymode.getSelected());

		String currentmode = flymode.getSelected();

		if (event instanceof EventStep) {
			EventStep step = (EventStep) event;
			if (currentmode.equalsIgnoreCase("Hypixel")) {
				step.setActive(false);
				step.setStepHeight(0);
				step.setRealHeight(0);
			}
		}

		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
			Packet p = ep.getPacket();
			if (p instanceof S08PacketPlayerPosLook) {
				S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook) p;
				hypixel = 0;
				if (currentmode.equalsIgnoreCase("Hypixel")) {
					failedStart = true;
					Client.instance.notification.show(new Notification(NotificationType.WARNING, getName(),
							"Hypixel Fast fly has been Disable by lag back.", 3));
					return;
				} else if (flymode.getSelected().equalsIgnoreCase("Cubecraft")) {
					failedStart = true;
					Client.instance.notification.show(new Notification(NotificationType.WARNING, getName(),
							"Cubecraft fly has been Disable by lag back.", 3));
				}
			}

			if (p instanceof S12PacketEntityVelocity) {
				S12PacketEntityVelocity packet = (S12PacketEntityVelocity) p;
				if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
					ep.setCancelled(true);
				}
			}
			if (p instanceof S27PacketExplosion) {
				ep.setCancelled(true);
			}

			if (p instanceof C03PacketPlayer) {
				Block block = MoveUtils.getBlockUnderPlayer(mc.thePlayer, 0.2);
				if (p instanceof C03PacketPlayer) {
					if (Client.instance.moduleManager.scaffold.isEnabled())
						return;

					if (!MoveUtils.isOnGround(0.001) && !block.isFullBlock() && !(block instanceof BlockGlass)) {
						C03PacketPlayer packet = (C03PacketPlayer) p;
						packet.onGround = false;
					}
				}
			}
		}

		if (event instanceof EventMove) {
			EventMove em = (EventMove) event;
			if (currentmode.equalsIgnoreCase("Hypixel")) {
				if (failedStart) {
					em.setX(0D);
					em.setZ(0D);
				} else if (hypixelfast.getObject() && fastFlew < 10 && mc.thePlayer.onGround) {
					em.setX(0D);
					em.setZ(0D);
				}
				if (!MoveUtils.isMoveKeyPressed()) {
					em.setX(0D);
					em.setZ(0D);
				}
				return;
			}

			if (flymode.getSelected().equalsIgnoreCase("Cubecraft")) {
				if (failedStart) {
					em.setX(0D);
					em.setZ(0D);
				}
				return;
			}
		}

		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			double speed = getBaseMoveSpeed();
			if (currentmode.equalsIgnoreCase("Hypixel")) {
				if (!e.isPre())
					return;

				if (hypixelfast.getObject() && fastFlew < 10) {
					fastFlew++;
					if (mc.thePlayer.hurtResistantTime == 19) {
						MoveUtils.setMotion(0.3 + MoveUtils.getSpeedEffect() * 0.05f);
						hypixel = 13 + hypixelspeed.getObject();
						mc.thePlayer.jump();
						mc.thePlayer.posY += 0.42;
//            			mc.thePlayer.motionY = 0.41999998688698f + MoveUtils.getJumpEffect()*0.1;
						fastFlew = 10;
					}
				}

				if (failedStart)
					return;

				Block block = MoveUtils.getBlockUnderPlayer(mc.thePlayer, 0.2);
				if (!MoveUtils.isOnGround(0.0000001) && !block.isFullBlock() && !(block instanceof BlockGlass)) {
					mc.thePlayer.motionY = 0;
					float speedf = 0.29f + MoveUtils.getSpeedEffect() * 0.06f;
					if (hypixel > 0) {
						if (!(mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()
								|| mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown())
								|| mc.thePlayer.isCollidedHorizontally)
							hypixel = 0;
						speedf += hypixel / 18;
						if (hypixelfast.getObject()) {
							hypixel -= 0.315 + MoveUtils.getSpeedEffect() * 0.006; // 0.152
						} else {
							hypixel -= 0.155 + MoveUtils.getSpeedEffect() * 0.006; // 0.152
						}

					}
					setSpecialMotion(speedf);
					mc.thePlayer.jumpMovementFactor = 0;
					mc.thePlayer.onGround = false;
					if (mc.gameSettings.keyBindJump.isKeyDown()) {
						mc.thePlayer.motionY = 0.4;
					}
					if (mc.gameSettings.keyBindSneak.isKeyDown()) {
						mc.thePlayer.motionY -= 0.4;
					}
					mc.thePlayer.lastReportedPosY = 0;
//                    if (hypixeltimer.hasTimeReached(2)) {
//                    	mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-5, mc.thePlayer.posZ);
//                    	hypixeltimer.setLastMS();
//                    }
					count++;
					if (count <= 2) {
						mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.9999E-8, mc.thePlayer.posZ);
					} else if (count == 4) {
						mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1E-8, mc.thePlayer.posZ);
					} else if (count >= 5) {
						mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.99999E-8, mc.thePlayer.posZ);
						count = 0;
					}
				}
			}
			if (e.isPre()) {
				double X = mc.thePlayer.posX;
				double Y = mc.thePlayer.posY;
				double Z = mc.thePlayer.posZ;
				if (flymode.getSelected().equalsIgnoreCase("Zoom")) {
					// Zoom
					if (MoveUtils.isOnGround(0.01)) {
						mc.thePlayer.jump();
					} else {
						mc.thePlayer.onGround = true;
						mc.thePlayer.motionY = 0;
						if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()
								|| mc.gameSettings.keyBindLeft.isKeyDown()
								|| mc.gameSettings.keyBindRight.isKeyDown()) {
							double currentSpeed = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX
									+ mc.thePlayer.motionZ * mc.thePlayer.motionZ);
							// Default 1.0064 : 1.001
							double speedin = 1.4;

							double direction = Utils.getDirection();

							mc.thePlayer.motionX = -Math.sin(direction) * speedin * currentSpeed;
							mc.thePlayer.motionZ = Math.cos(direction) * speedin * currentSpeed;
						} else {
							mc.thePlayer.motionX = 0;
							mc.thePlayer.motionZ = 0;
						}
					}
					if (mc.gameSettings.keyBindJump.isKeyDown()) {
						mc.thePlayer.motionY = 0.4;
					}
					if (mc.gameSettings.keyBindSneak.isKeyDown()) {
						mc.thePlayer.motionY -= 0.4;
					}
				} else if (flymode.getSelected().equalsIgnoreCase("MC-Central")) {
					// MC-Central
//					event.setCancelled(true);
					mc.thePlayer.motionY = 0;
					mc.thePlayer.onGround = true;
					e.setOnGround(true);
//					event.setCancelled(false);
					if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()
							|| mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
						double currentSpeed = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX
								+ mc.thePlayer.motionZ * mc.thePlayer.motionZ);
						// Default 1.0064 : 1.001
						double speedin = 1.17;
//		                double speedin = 2;
						MoveUtils.setMotion(speedin);
					} else {
						mc.thePlayer.motionX = 0;
						mc.thePlayer.motionZ = 0;
					}

					if (mc.gameSettings.keyBindJump.isKeyDown()) {
						mc.thePlayer.motionY = 0.4;
					}
					if (mc.gameSettings.keyBindSneak.isKeyDown()) {
						mc.thePlayer.motionY -= 0.4;
					}
				} else if (flymode.getSelected().equalsIgnoreCase("Cubecraft")) {
					if (failedStart)
						return;
					if (!MoveUtils.isOnGround(0.001)) {
						stage++;
						mc.thePlayer.onGround = false;
						mc.thePlayer.jumpMovementFactor = 0;
						float timer = 1f;
						double motion = 0;
						if (stage == 0) {
							timer = 0.3f;
							speed = 2;
							motion = -0.1;
						} else if (stage == 1) {
//							motion = 0.1;
							speed = 0.5;
							timer = 1f;
						} else if (stage == 2) {
//							motion = 0.1;
							speed = 1;
							timer = 0.85f;
						} else if (stage == 3) {
//							motion = -0.05;
							speed = 0.5;
							timer = 1f;
						} else if (stage >= 4) {
							motion = -0.05;
							speed = 2;
							timer = 0.3f;
							stage = 0;
						}
						mc.timer.timerSpeed = timer;
						if (motion != 0) {
							mc.thePlayer.motionY = motion;
						}
//						speed = 1.6F;
                		MoveUtils.setMotion(speed);
//						final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
//
//						mc.thePlayer.setPosition(mc.thePlayer.posX + (-Math.sin(yaw) * speed), mc.thePlayer.posY,
//								mc.thePlayer.posZ + (Math.cos(yaw) * speed));
						
						mc.thePlayer.lastReportedPosY = 0;
//	                    if (hypixeltimer.hasTimeReached(2)) {
//	                    	mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-5, mc.thePlayer.posZ);
//	                    	hypixeltimer.setLastMS();
//	                    }
						count++;
						if (count <= 2) {
							mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.9999E-8, mc.thePlayer.posZ);
						} else if (count == 4) {
							mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1E-8, mc.thePlayer.posZ);
						} else if (count >= 5) {
							mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.99999E-8, mc.thePlayer.posZ);
							count = 0;
						}
					} else {
//                		if(mc.timer.timerSpeed == 0.3f){
						mc.timer.timerSpeed = 1;
						failedStart = true;
//                			MoveUtils.setMotion(0);
//                		}
					}
				} else if (flymode.getSelected().equalsIgnoreCase("OldSekSin")) {
					if (stage <= 4 && mc.thePlayer.ticksExisted % 6 == 0 && !MoveUtils.isOnGround(0.01)) {
						stage++;
//						mc.thePlayer.posY -= 0.25;
						final double yaw = Math.toRadians(mc.thePlayer.rotationYaw);
//						mc.thePlayer.motionY = 0;
						mc.thePlayer.motionY = -0.015D;
						mc.thePlayer.onGround = true;
						timer.setLastMS();
					} else if (MoveUtils.isOnGround(0.01)) {
						mc.timer.timerSpeed = 1F;
//						stage = 0;
					} else if (!MoveUtils.isOnGround(0.01)) {
//						mc.timer.timerSpeed = 0.1F;
					}
				} else if (flymode.getSelected().equalsIgnoreCase("Vanilla")) {
					mc.thePlayer.capabilities.isFlying = true;
					final float vanillaSpeed = 2F;
//					mc.thePlayer.capabilities.isFlying = false;
					mc.thePlayer.motionY = 0;
					mc.thePlayer.motionX = 0;
					mc.thePlayer.motionZ = 0;
					if (mc.gameSettings.keyBindJump.isKeyDown())
						mc.thePlayer.motionY += vanillaSpeed;
					if (mc.gameSettings.keyBindSneak.isKeyDown())
						mc.thePlayer.motionY -= vanillaSpeed;
					MoveUtils.setMotion(vanillaSpeed);
				} else if (flymode.getSelected().equalsIgnoreCase("Motion")) {
					final float vanillaSpeed = 2F;
					mc.thePlayer.capabilities.isFlying = false;
					mc.thePlayer.motionY = 0;
					mc.thePlayer.motionX = 0;
					mc.thePlayer.motionZ = 0;
					if (mc.gameSettings.keyBindJump.isKeyDown())
						mc.thePlayer.motionY += vanillaSpeed;
					if (mc.gameSettings.keyBindSneak.isKeyDown())
						mc.thePlayer.motionY -= vanillaSpeed;
					MoveUtils.setMotion(vanillaSpeed);
				}
			}
		}

		if (event instanceof Event3D) {
			int renderColor = ClientColor.getColor();
			if (esp.getObject()) {
				if (mc.thePlayer.posY > startY + 0.5) {
					drawESP(mc.thePlayer, Color.RED.getRGB());
				} else {
					drawESP(mc.thePlayer, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(1000));
				}
			}
		}
	}

	public void drawESP(EntityLivingBase entity, int color) {
		double x = entity.lastTickPosX
				+ (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks;

//		double y = entity.lastTickPosY
//				+ (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks + entity.getEyeHeight() * 1.2;
		double y = startY + (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks
				+ entity.getEyeHeight() * 1.5;

		double z = entity.lastTickPosZ
				+ (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks;
		double width = Math.abs(entity.boundingBox.maxX - entity.boundingBox.minX) + 1;
		double height = 0.1;
		Vec3 vec = new Vec3(x - width / 2, y, z - width / 2);
		Vec3 vec2 = new Vec3(x + width / 2, y + height, z + width / 2);
		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		RenderingUtil.enableGL2D();
		RenderingUtil.pre3D();
		Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks,
				2);
		RenderingUtil.glColor(color);
		RenderingUtil.drawOutlinedBoundingBox(
				new AxisAlignedBB(vec.xCoord - renderManager.renderPosX, vec.yCoord - renderManager.renderPosY,
						vec.zCoord - renderManager.renderPosZ, vec2.xCoord - renderManager.renderPosX,
						vec2.yCoord - renderManager.renderPosY, vec2.zCoord - renderManager.renderPosZ));
		GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		RenderingUtil.post3D();
		RenderingUtil.disableGL2D();
	}

}
