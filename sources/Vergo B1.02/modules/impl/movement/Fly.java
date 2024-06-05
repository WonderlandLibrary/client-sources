package xyz.vergoclient.modules.impl.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventMove;
import xyz.vergoclient.event.impl.EventReceivePacket;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.util.main.MovementUtils;
import xyz.vergoclient.util.main.Timer;

import java.util.Arrays;


public class Fly extends Module implements OnEventInterface {

	Timer timer;

	public Fly() {
		super("Fly", Category.MOVEMENT);
		this.timer = new Timer();
	}
	
	public ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla"/*, "Hypixel"*/);

	private float stage;
	private boolean hasClipped;
	private boolean doFly;
	private double x, y, z;


	@Override
	public void loadSettings() {
		
		mode.modes.clear();
		mode.modes.addAll(Arrays.asList("Vanilla"/*, "Hypixel"*/));
		
		addSettings(mode);
	}

	@Override
	public void onEnable() {
		doFly = false;
		stage = 0;
		x = mc.thePlayer.posX;
		y = mc.thePlayer.posY;
		z = mc.thePlayer.posZ;
		hasClipped = false;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		if(mode.is("Vanilla")) {

			mc.thePlayer.capabilities.isFlying = false;

		}

		mc.timer.timerSpeed = 1.0f;
	}

	public int state;

	@Override
	public void onEvent(Event e) {

		if(e instanceof EventMove) {
			if (mode.is("Vanilla")) {
				if (!mc.thePlayer.capabilities.isFlying) {
					mc.thePlayer.capabilities.isFlying = true;
				}
			}
		}

		// Hypixel

		if(mode.is("Hypixel")) {

			if(e instanceof EventMove) {
				mc.thePlayer.cameraYaw = mc.thePlayer.cameraPitch = 0.05f;
				mc.thePlayer.posY = y;
				if (mc.thePlayer.onGround && stage == 0) {
					mc.thePlayer.motionY = 0.09;
				}
				stage++;
				if (mc.thePlayer.onGround && stage > 2 && !hasClipped) {
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.15, mc.thePlayer.posZ, false));
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.15, mc.thePlayer.posZ, true));
					hasClipped = true;
				}
				if (doFly) {
					mc.thePlayer.motionY = 0.09;
					mc.thePlayer.onGround = true;
					mc.timer.timerSpeed = 2;
				} else {
					MovementUtils.setSpeed(0);
					mc.timer.timerSpeed = 5;
				}
			}

			if(e instanceof EventReceivePacket) {
				EventReceivePacket erc = (EventReceivePacket) e;
				if (erc.packet instanceof S08PacketPlayerPosLook) {
					S08PacketPlayerPosLook s08 = (S08PacketPlayerPosLook) erc.packet;
					y = s08.getY();
					doFly = true;
				}
			}
		}

	}
	
}
