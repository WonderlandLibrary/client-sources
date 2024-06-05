package xyz.vergoclient.modules.impl.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import tv.twitch.chat.Chat;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventMove;
import xyz.vergoclient.event.impl.EventSendPacket;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.event.impl.EventUpdate;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.BooleanSetting;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.util.main.ChatUtils;
import xyz.vergoclient.util.main.MovementUtils;
import xyz.vergoclient.util.main.Timer;
import xyz.vergoclient.util.main.TimerUtil;
import xyz.vergoclient.util.packet.PacketUtil;

import java.util.Arrays;

public class Speed extends Module implements OnEventInterface {


	Timer jumpTimer;

	Timer packTimer;

	public TimerUtil packetFucker = new TimerUtil();

	public Speed() {
		super("Speed", Category.MOVEMENT);
		this.jumpTimer = new Timer();
		this.packTimer = new Timer();
	}

	public ModeSetting mode = new ModeSetting("Mode", "Hypixel1", "Hypixel1", "Hypixel2", "Hypixel3");

	public BooleanSetting strafe = new BooleanSetting("Strafe", true);

	int ticks;

	@Override
	public void loadSettings() {
		mode.modes.clear();
		mode.modes.addAll(Arrays.asList("Hypixel1", "Hypixel2", "Hypixel3"));

		addSettings(mode, strafe);
	}

	@Override
	public void onEnable() {
		mc.timer.timerSpeed = 1;
		mc.timer.ticksPerSecond = 20;

		packetFucker.reset();

		ticks = mc.thePlayer.ticksExisted;

		this.packTimer.reset();
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1;
		mc.timer.ticksPerSecond = 20;

		mc.thePlayer.speedInAir = 0.02f;

		if(Vergo.config.modBlink.isEnabled()) {
			Vergo.config.modBlink.toggle();
		}
	}

	@Override
	public void onEvent(Event e) {

		if (mode.is("Hypixel1")) {
			onHypixelEvent(e);
		} else if(mode.is("Hypixel2")) {
			onHypixelEvent(e);
		} else if(mode.is("Hypixel3")) {
			onHypixelEvent(e);
		}

	}

	private void onHypixelEvent(Event e) {

		if (e instanceof EventTick && e.isPre()) {

			if(mode.is("Hypixel1")) {
				setInfo("Hypixel1");
			} else if(mode.is("Hypixel2")) {
				setInfo("Hypixel2");
			} else if(mode.is("Hypixel3")) {
				setInfo("Hypixel3");
			}

		} else if (e instanceof EventUpdate && e.isPre()) {

			if(mode.is("Hypixel1")) {
				if(MovementUtils.isMoving()) {
					hypixelOne(e);
				}
			} else if(mode.is("Hypixel2")) {
				if(MovementUtils.isMoving()) {
					hypixelTwo(e);
				}
			} else if(mode.is("Hypixel3")) {
				if(MovementUtils.isMoving()) {
					hypixelThree(e);
				}
			}
		}

	}

	public static TimerUtil blinkTimer = new TimerUtil();

	private void hypixelOne(Event event) {

		if(mc.thePlayer.isInLava() || mc.thePlayer.isInWater() || mc.thePlayer.isSpectator()) {
			return;
		}

		if(mc.gameSettings.keyBindJump.isKeyDown()) {

		}

		if(Vergo.config.modStrafe.isDisabled() && strafe.isEnabled()) {
			Strafe.strafe();
		}

		if(!mc.thePlayer.isSprinting()) {
			mc.thePlayer.setSprinting(true);
		}

		if(MovementUtils.isOnGround(0.0001) && !mc.thePlayer.isCollidedHorizontally) {
			mc.thePlayer.jump();
			mc.thePlayer.motionY -= 0.023f;
			if(mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown()) {
				MovementUtils.setSpeed(0.455);
			} else {
				mc.timer.timerSpeed = 1.0f;
				MovementUtils.setSpeed(0.25);
			}
			if (mc.thePlayer.isCollidedVertically) {
				mc.thePlayer.motionY = 0.4;
			}
		}

		if(mc.thePlayer.motionY > 0.2) {
			mc.timer.timerSpeed = 1.2f;
		} else if(mc.thePlayer.motionY < 0.19) {
			mc.timer.timerSpeed = 1.06f;
		}

	}

	private void hypixelTwo(Event event) {
		if(mc.thePlayer.isInLava() || mc.thePlayer.isInWater() || mc.thePlayer.isSpectator()) {
			return;
		}

		if(mc.gameSettings.keyBindJump.isKeyDown()) {
			return;
		}


		if(!mc.thePlayer.isSprinting()) {
			mc.thePlayer.setSprinting(true);
		}

		if(Vergo.config.modStrafe.isDisabled() && strafe.isEnabled()) {
			Strafe.strafe();
		}

		if(MovementUtils.isOnGround(0.0001) && !mc.thePlayer.isCollidedHorizontally) {
			mc.thePlayer.motionY -= 0.023f;
			if(mc.gameSettings.keyBindForward.isKeyDown() && !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown() && !mc.gameSettings.keyBindBack.isKeyDown()) {
				MovementUtils.setSpeed(0.49);
				mc.thePlayer.motionX *= 1.00154;
				mc.thePlayer.motionY *= 1.00154;
			} else {
				mc.timer.timerSpeed = 1.0f;
				MovementUtils.setSpeed(0.32);
			}
			if (mc.thePlayer.isCollidedVertically) {
				mc.thePlayer.motionY = 0.4;
			}
		}

		if(MovementUtils.isMoving()) {
			if(mc.thePlayer.fallDistance < 1) {
				mc.timer.timerSpeed = 1.0f;
			} else {
				if (mc.thePlayer.motionY > 0.2) {
					mc.timer.timerSpeed = 1.15f;
				} else if (mc.thePlayer.motionY < 0.19) {
					mc.timer.timerSpeed = 1.06f;
				}
			}
		}

	}

	private void hypixelThree(Event event) {

		if(mc.thePlayer.isOnLadder() || mc.thePlayer.isInWater() || mc.thePlayer.isInLava()) {
			return;
		}

		if(!mc.thePlayer.isSprinting() && Vergo.config.modSprint.isDisabled()) {
			if(mc.gameSettings.keyBindForward.isKeyDown()) {
				mc.thePlayer.setSprinting(true);
			}
		}
		if(MovementUtils.isOnGround(0.001)) {
			mc.thePlayer.jump();
		}

		if(Vergo.config.modStrafe.isDisabled() && strafe.isEnabled()) {
			Strafe.strafe();
		}

	}

}
