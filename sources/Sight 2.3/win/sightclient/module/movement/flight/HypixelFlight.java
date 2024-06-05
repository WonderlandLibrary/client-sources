package win.sightclient.module.movement.flight;

import java.util.ArrayList;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.player.EventFlag;
import win.sightclient.event.events.player.EventMove;
import win.sightclient.module.Module;
import win.sightclient.module.ModuleMode;
import win.sightclient.module.combat.TargetStrafe;
import win.sightclient.module.other.Disabler;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.notification.Notification;
import win.sightclient.utils.PlayerUtils;
import win.sightclient.utils.minecraft.ChatUtils;
import win.sightclient.utils.minecraft.MoveUtils;
import win.sightclient.utils.minecraft.ServerUtils;

public class HypixelFlight extends ModuleMode {

	private NumberSetting speed;
	private int stage;
	private double lastDist;
	private double moveSpeed;
	private int value;
	private double yPos;
	
	private ModeSetting hmode;
	private boolean startedGround = false;
	
	public HypixelFlight(Module parent, NumberSetting speed, ModeSetting hmode) {
		super(parent);
 		this.speed = speed;
		this.hmode = hmode;
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate)e;
			if (eu.isPre()) {
				if (this.stage == 8) {
					Disabler.sendDisable(false);
				}
			    if (this.stage > 2) {
					this.value++;
					eu.setY(mc.thePlayer.posY + this.yPos);
					switch (value) {
						case 1 :
							mc.timer.timerSpeed = 0.9F;
							this.yPos -= -this.yPos;
							break;
                        case 2:
                        	mc.timer.timerSpeed = 1F;
							this.yPos += 2E-8;
                        case 3:
							this.yPos += 1E-6;
						case 4 :
							this.yPos -= 1E-10;
							break;
						case 5 :
							this.yPos += 1E-8;
							this.value = 0;
							break;
						case 6 :
							this.yPos -= 2E-7;
							this.value = 0;
							break;
					}
					
				}
	        	final double xDif = mc.thePlayer.posX - mc.thePlayer.prevPosX;
	            final double zDif = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
	            this.lastDist = Math.sqrt(xDif * xDif + zDif * zDif);
			}
		} else if (e instanceof EventMove) {
			EventMove em = (EventMove)e;
			if (this.stage > 2) {
				em.setY(mc.thePlayer.motionY = 0);
			}
			if (mc.thePlayer.isCollidedHorizontally) {
				this.moveSpeed = 0;
			}
			if (MoveUtils.isMoving()) {
				if (stage == 0) {
					if (mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround) {
						if (this.hmode.getValue().equalsIgnoreCase("Premium")) {
							PlayerUtils.damagePlayer(0);
						}
					}
					if (this.startedGround) {
						this.moveSpeed = this.speed.getValue() / 6;
					}
				} else if (stage == 1) {
					if (mc.thePlayer.onGround) {
						em.setY(mc.thePlayer.motionY = MoveUtils.getJumpBoost(0.42));
					}
					this.moveSpeed = this.speed.getValue() / 6.25;
				} else if (stage == 2) {
					if (this.startedGround) {
						this.moveSpeed = this.speed.getValue() / 5;
					} else {
						this.moveSpeed = 0;
						if (mc.thePlayer.fallDistance > 1 && ServerUtils.isOnHypixel()) {
							this.moveSpeed = 5;
						}
					}
				} else {
					if (this.stage == 6) {
						this.moveSpeed = MoveUtils.getBaseSpeed() * (this.speed.getValue() / 1.25);
					}
					if (this.stage >= 12 && this.stage <= 20) {
						//mc.timer.timerSpeed = 1.56F;
					} else {
						mc.timer.timerSpeed = 1F;
					}
					this.moveSpeed -= this.moveSpeed / (MoveUtils.getSpeedEffect() > 0 ? 130 : 160 - 1E-6D);
					if (mc.thePlayer.isCollidedHorizontally) {
						this.moveSpeed = 0;
					}
				}
				this.stage++;
			} else {
				mc.timer.timerSpeed = 1F;
			}
			if (ts == null) {
				ts = (TargetStrafe)Sight.instance.mm.getModuleByName("TargetStrafe");
			}
			if (this.hmode.getValue().equalsIgnoreCase("Premium")) {
				if (ts.canStrafe()) {
					ts.strafe(em, Math.max(MoveUtils.getBaseSpeed(), this.moveSpeed));
				} else {
					MoveUtils.setMotion(em, Math.max(MoveUtils.getBaseSpeed(), this.moveSpeed));
				}
			} else {
				if (ts.canStrafe()) {
					ts.strafe(em, MoveUtils.getBaseSpeed());
				} else {
					MoveUtils.setMotion(em, MoveUtils.getBaseSpeed());
				}
			}
		} else if (e instanceof EventFlag) {
			Sight.instance.nm.send(new Notification("Flight", "Flight was disabled to prevent future flags."));
			this.parent.setToggled(false);
		}
	}
	
	private TargetStrafe ts;
	
	@Override
	public void onDisable() {
		MoveUtils.setMotion(null, 0);
		mc.timer.timerSpeed = 1F;
		mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + this.yPos, mc.thePlayer.posZ);
	}

	@Override
	public void onEnable() {
		this.value = 0;
		this.stage = 0;
		this.lastDist = 0;
		this.yPos = 0;
		this.moveSpeed = 0;
		if (mc.thePlayer != null) {
			this.startedGround = mc.thePlayer.onGround;
		}
		Disabler.sendDisable(true);
	}
}