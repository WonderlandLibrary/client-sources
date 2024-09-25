package none.module.modules.combat;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.MathHelper;
import none.Client;
import none.event.Event;
import none.event.EventSystem;
import none.event.RegisterEvent;
import none.event.events.EventMove;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.utils.MoveUtils;
import none.utils.Utils;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class Velocity extends Module {

	private static String[] modes = { "Normal", "Custom", "Jump", "AAC", "AACPush", "Reverse", "Reverse2", "Seksin", "Seksin2"};
	public static ModeValue velocitymode = new ModeValue("Velocity-Mode", "Normal", modes);
	private NumberValue<Integer> h = new NumberValue<>("H", 10, 0, 100);
	private NumberValue<Integer> v = new NumberValue<>("V", 10, 0, 100);
	private NumberValue<Float> reverseStrengthValue = new NumberValue<>("ReverseStrength", 1F, 0.1F, 1F);
	private final NumberValue<Float> reverse2StrenghtValue = new NumberValue("Reverse2Strength", 0.05F, 0.02F, 0.1F);
	private final NumberValue<Float> aacPushXZReducerValue = new NumberValue("AACPushXZReducer", 2F, 1F, 3F);
	private final BooleanValue aacPushYReducerValue = new BooleanValue("AACPushYReducer", true);
	private final BooleanValue HorizontalMotionReset = new BooleanValue("HorizontalMotionReset", true);
	private final BooleanValue VerticalMotionReset = new BooleanValue("VerticalMotionReset", true);

	public Velocity() {
		super("Velocity", "Velocity", Category.COMBAT, Keyboard.KEY_V);
	}

	int count;
	double xmot, zmot, ymot = 0;

	private long velocityTime;
	private boolean gotVelocity;
	private boolean gotHurt;
	public boolean spoof = false, packeted = false;

	private double motionX = 0;
	private double motionY = 0;
	private double motionZ = 0;
	@Override
	protected void onEnable() {
		super.onEnable();
		count = 0;
		if (Client.instance.moduleManager.autoAwakeNgineXE.isEnabled()) {
			evc("If you get flag please. disabled velocity");
			velocitymode.setObject("Normal");
		}
	}

	@Override
	protected void onDisable() {
		super.onDisable();
		if (mc.thePlayer == null)
			return;

		mc.thePlayer.speedInAir = 0.02F;
	}

	@Override
	@RegisterEvent(events = { EventPreMotionUpdate.class, EventPacket.class, EventTick.class, EventMove.class })
	public void onEvent(Event event) {
		String currentMode = velocitymode.getSelected();
		if (!isEnabled())
			return;

		setDisplayName(getName() + ChatFormatting.WHITE + " " + velocitymode.getSelected());
		String mode = velocitymode.getSelected();
		if (mc.thePlayer.isInWater())
			return;

		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
			Packet p = ep.getPacket();
			if (mode.equalsIgnoreCase("Seksin2")) {
//				if (spoof && p instanceof S08PacketPlayerPosLook) {
//					event.setCancelled(true);
//				}
				
				if((p instanceof S27PacketExplosion)) {
					event.setCancelled(true); // No anticheat detects antiknockback with TNT 
	            }
				
				if (p instanceof S12PacketEntityVelocity) {
					S12PacketEntityVelocity packet = (S12PacketEntityVelocity) p;
					if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
						event.setCancelled(true);
		            }
				}
				
				if (spoof && p instanceof C03PacketPlayer && !(p instanceof C04PacketPlayerPosition) && !(p instanceof C06PacketPlayerPosLook)) {
					C03PacketPlayer packet = (C03PacketPlayer) p;
					if (p instanceof C05PacketPlayerLook){
	    				C05PacketPlayerLook e = (C05PacketPlayerLook) p;
	    				mc.thePlayer.connection.sendPacketNoEvent(new C06PacketPlayerPosLook(packet.x * 0.00001, packet.y, packet.z * 0.00001, e.getYaw(), e.getPitch(), packet.isOnGround()));
	    			}else{
	    				mc.thePlayer.connection.sendPacketNoEvent(new C04PacketPlayerPosition(packet.x * 0.00001, packet.y, packet.z * 0.00001, packet.isOnGround()));
	    			}
					spoof = false;
	    			event.setCancelled(true);
				}
			}else if (mode.equalsIgnoreCase("Seksin")) {
				if (spoof && p instanceof S08PacketPlayerPosLook) {
					spoof = false;
					event.setCancelled(true);
				}
				
				if (p instanceof S12PacketEntityVelocity) {
					S12PacketEntityVelocity packet = (S12PacketEntityVelocity) p;
					if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
//						motionX = (packet.getMotionX() * 0.0001);
//						motionY = (packet.getMotionY() * 0.0001);
//						motionZ = (packet.getMotionZ() * 0.0001);
						
						event.setCancelled(true);
		            }
				}
				if((p instanceof S27PacketExplosion)) {
					event.setCancelled(true); // No anticheat detects antiknockback with TNT 
	            }
				
//				if (packeted && p instanceof C03PacketPlayer && !(p instanceof C04PacketPlayerPosition) && !(p instanceof C06PacketPlayerPosLook)) {
//					C03PacketPlayer packet = (C03PacketPlayer) p;
//					if (p instanceof C05PacketPlayerLook){
//	    				C05PacketPlayerLook e = (C05PacketPlayerLook) p;
//	    				mc.thePlayer.connection.sendPacketNoEvent(new C06PacketPlayerPosLook(packet.x, packet.y, packet.z, e.getYaw(), e.getPitch(), packet.isOnGround()));
//	    			}else{
//	    				mc.thePlayer.connection.sendPacketNoEvent(new C04PacketPlayerPosition(packet.x, packet.y, packet.z, packet.isOnGround()));
//	    			}
//					packeted = false;
//	    			event.setCancelled(true);
//				}
//				
//				if (spoof && p instanceof C03PacketPlayer) {
//					C03PacketPlayer packet = (C03PacketPlayer) p;
//						if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(motionX, 0, 0).expand(0, 0, 0)).isEmpty() && (mc.thePlayer.onGround)){
//							packet.x = motionX;
//						}
//						if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0, motionY, 0).expand(0, 0, 0)).isEmpty() && (mc.thePlayer.onGround)){
////							packet.y = motionY;
//						}
//						if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0, 0, motionZ).expand(0, 0, 0)).isEmpty() && (mc.thePlayer.onGround)){
//							packet.z = motionZ;
//						}
//					spoof = false;
//					packeted = true;
//					event.setCancelled(true);
//					mc.thePlayer.connection.sendPacket(packet);
//				}
			} else {
				if (p instanceof S12PacketEntityVelocity) {
					S12PacketEntityVelocity packet = (S12PacketEntityVelocity) p;
					int entId = packet.getEntityID();
					int x = packet.getMotionX();
					int y = packet.getMotionY();
					int z = packet.getMotionZ();

					if (entId == mc.thePlayer.getEntityId()) {
						velocityTime = System.currentTimeMillis();
						if (velocitymode.getSelected().equalsIgnoreCase("Normal")) {
							ep.setCancelled(true);
						} else if (velocitymode.getSelected().equalsIgnoreCase("Custom")) {
							int vertical = v.getObject();
							int horizontal = h.getObject();
							if (vertical != 0 || horizontal != 0) {
								packet.setMotionX(horizontal * packet.getMotionX() / 100);
								packet.setMotionY(vertical * packet.getMotionY() / 100);
								packet.setMotionZ(horizontal * packet.getMotionZ() / 100);
							} else {
								ep.setCancelled(true);
							}
						} else if (velocitymode.getSelected().equalsIgnoreCase("Reverse2")) {
							gotVelocity = true;
						}
					}
				}
				if (p instanceof S27PacketExplosion) {
					if (ep.isPre()) {
						if (velocitymode.getSelected().equalsIgnoreCase("Normal")) {
							ep.setCancelled(true);
						} else if (velocitymode.getSelected().equalsIgnoreCase("Custom")) {
							ep.setCancelled(true);
						} else {
							ep.setCancelled(true);
						}
					}
				}
			}
		}

		if (event instanceof EventTick) {
			if (currentMode.equalsIgnoreCase("Reverse")) {
				if (!gotVelocity)
					return;

				if (!mc.thePlayer.onGround && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()
						&& !mc.thePlayer.isInWeb) {
					MoveUtils.strafe(MoveUtils.defaultSpeed() * reverseStrengthValue.getObject());
				} else if (System.currentTimeMillis() - velocityTime > 80L) {
					gotVelocity = false;
				}
				return;
			} else if (currentMode.equalsIgnoreCase("AAC")) {
				if (velocityTime != 0L && System.currentTimeMillis() - velocityTime > 80L) {
					mc.thePlayer.motionX *= h.getObject() / 100;
					mc.thePlayer.motionZ *= v.getObject() / 100;
					velocityTime = 0L;
				}
				return;
			} else if (currentMode.equalsIgnoreCase("AACPush")) {
				if (mc.thePlayer.movementInput.jump)
					return;

				if (velocityTime != 0L && System.currentTimeMillis() - velocityTime > 80L)
					velocityTime = 0L;

				if (mc.thePlayer.hurtTime > 0 && mc.thePlayer.motionX != 0 && mc.thePlayer.motionZ != 0)
					mc.thePlayer.onGround = true;

				if (mc.thePlayer.hurtResistantTime > 0 && aacPushYReducerValue.getObject())
					mc.thePlayer.motionY -= 0.0144;

				if (mc.thePlayer.hurtResistantTime >= 19) {
					final double reduce = aacPushXZReducerValue.getObject();

					mc.thePlayer.motionX /= reduce;
					mc.thePlayer.motionZ /= reduce;
				}
			} else if (currentMode.equalsIgnoreCase("Jump")) {
				if (mc.thePlayer.hurtTime > 0 && mc.thePlayer.onGround) {
					mc.thePlayer.motionY = 0.42;

					final float f = mc.thePlayer.rotationYaw * 0.017453292F;
					mc.thePlayer.motionX -= MathHelper.sin(f) * 0.2F;
					mc.thePlayer.motionZ += MathHelper.cos(f) * 0.2F;
				}
			} else if (currentMode.equalsIgnoreCase("Reverse2")) {
				if (!gotVelocity) {
					mc.thePlayer.speedInAir = 0.02F;
					return;
				}

				if (mc.thePlayer.hurtTime > 0)
					gotHurt = true;

				if (!mc.thePlayer.onGround && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()
						&& !mc.thePlayer.isInWeb) {
					if (gotHurt)
						mc.thePlayer.speedInAir = reverse2StrenghtValue.getObject();
				} else if (System.currentTimeMillis() - velocityTime > 80L) {
					gotVelocity = false;
					gotHurt = false;
				}
				return;
			} else if (mode.equalsIgnoreCase("Seksin")) {
				if (mc.thePlayer.hurtTime > 8) {
					if (mc.thePlayer.onGround) {
						spoof = true;
					} else {
						spoof = true;
					}
					if (VerticalMotionReset.getObject() == true) {
						mc.thePlayer.motionY = 0;
					}
					if (HorizontalMotionReset.getObject() == true) {
						mc.thePlayer.motionX = 0;
						mc.thePlayer.motionZ = 0;
					}
				}else {
					spoof = false;
				}
			}else if (mode.equalsIgnoreCase("Seksin2")) {
				if (mc.thePlayer.hurtTime > 8) {
					if (mc.thePlayer.onGround) {
						spoof = true;
					} else {
//						spoof = true;
					}
					if (VerticalMotionReset.getObject() == true) {
						mc.thePlayer.motionY = 0;
					}
					if (HorizontalMotionReset.getObject() == true) {
						mc.thePlayer.motionX = 0;
						mc.thePlayer.motionZ = 0;
					}
				}
			}
		}

		if (event instanceof EventMove) {
			EventMove em = (EventMove) event;
			if (mode.equalsIgnoreCase("Seksin")) {
				if (mc.thePlayer.hurtTime > 8) {
					if (VerticalMotionReset.getObject() == true) {
						em.setY(0);
					}
					if (HorizontalMotionReset.getObject() == true) {
						em.setX(0);
						em.setZ(0);
					}
				}
			}
		}

		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre()) {

			}
		}
	}

}
