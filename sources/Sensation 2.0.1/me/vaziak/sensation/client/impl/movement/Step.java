package me.vaziak.sensation.client.impl.movement;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.BlockStepEvent;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.RunTickEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.impl.combat.KillAura;
import me.vaziak.sensation.client.impl.combat.Criticals;
import me.vaziak.sensation.client.impl.combat.KillAura;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Step extends Module {

    protected StringsProperty abmode = new StringsProperty("Step Mode", "1337 Step prop_mode", null, false, true,
            new String[]{"Vanilla", "AGC", "NCP"}, new Boolean[]{false, true, false, false});
    TimerUtil time;
    private float yaw, pitch;
    private KillAura cheat_killAura;
    private double starty;
    private double compar;
    private double ypos;
    public static double stepheight;
    public static int waittime;
    boolean canstep;
	private Criticals cheat_criticals;
    public Step() {
        super("Step", Category.MOVEMENT);
        registerValue(abmode);
        time = new TimerUtil();
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        if (mc.thePlayer == null || Sensation.instance.cheatManager.isModuleEnabled("Speed"))
            return;
		setMode(abmode.getSelectedStrings().get(0));
        ypos = playerUpdateEvent.getPosY();
        yaw = playerUpdateEvent.getYaw();
        pitch = playerUpdateEvent.getPitch();
        if (waittime == 0 && mc.timer.timerSpeed < 1f && mc.thePlayer.ticksExisted % 3 == 0 && !Sensation.instance.cheatManager.isModuleEnabled("Fly")) {
            mc.timer.timerSpeed += Math.abs(1.0f - mc.timer.timerSpeed) / 5;
        }
    }

	@Collect
	public void onBlockStep(BlockStepEvent event) {

		if (abmode.getValue().get("NCP") && waittime != 0) event.stepHeight = 0;
		if (abmode.getValue().get("NCP") && waittime != 0) return;
		if (abmode.getValue().get("Vanilla")) { 
			event.stepHeight = 3;
		} else {
			if (mc.thePlayer == null || Sensation.instance.cheatManager.isModuleEnabled("Speed") || Sensation.instance.cheatManager.isModuleEnabled("Long Jump"))
				return;
			boolean timer = ((KillAura) Sensation.instance.cheatManager.getCheatRegistry().get("Kill Aura")).targetIndex == -1 || !Sensation.instance.cheatManager.isModuleEnabled("Kill Aura");
			if (mc.thePlayer.isOnLadder() || mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.toLowerCase().contains("mc-central") && !timer)
				return;
 

				if (cheat_killAura == null) {
					cheat_killAura = ((KillAura) Sensation.instance.cheatManager.getCheatRegistry().get("Kill Aura"));
				}

				if (cheat_criticals == null) {
					cheat_criticals = (Criticals) Sensation.instance.cheatManager.getCheatRegistry().get("Criticals");
				}

				if (Sensation.instance.cheatManager.isModuleEnabled("Fly")) {
					event.stepHeight = 0;
				} else {
					boolean morethanslabheight = mc.thePlayer.getEntityBoundingBox().minY - mc.thePlayer.posY > .626;
					if (event.isPre()) {
						if (mc.thePlayer.isCollidedVertically && !mc.gameSettings.keyBindJump.isKeyDown() && time.hasPassed(abmode.getValue().get("AGC") ? 250 : 300)) {
							event.stepHeight = mc.thePlayer.onGround && Math.abs(mc.thePlayer.posY - ypos) < .0001 ? 2 : 1;

							starty = mc.thePlayer.posY;
						}
					} else {
						compar = (mc.thePlayer.getEntityBoundingBox().minY - starty);
						if (mc.thePlayer.getEntityBoundingBox().minY - mc.thePlayer.posY > .5) {
							
							cheat_criticals.groundTicks = 0;
							if (cheat_criticals.waitDelay != 3) {
								cheat_criticals.waitDelay = 3;
								mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, starty, mc.thePlayer.posZ, yaw -= .001, pitch += .007, mc.thePlayer.onGround));
							}
						}
						double y = mc.thePlayer.posY;
						if (morethanslabheight) {
							if (abmode.getValue().get("AGC")) {
								if (compar <= 1.0) {
									//
									float[] offsets = { 0.41999998688697815f, 0.7531999805212024f};
									for (float position : offsets) {
										mc.timer.timerSpeed = .4f;
										mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, starty + position, mc.thePlayer.posZ, yaw -= .01, pitch += .07, (position <= .42)));
									}
								} else if (compar <= 2) {
									if (cheat_killAura.targetIndex == -1 || !Sensation.instance.cheatManager.isModuleEnabled("Kill Aura")) {

										float[] offsets = { 0.41999998688697815f, 0.7531999805212024f, 0.7000000000000028f};
										for (float position : offsets) {
											mc.timer.timerSpeed = .2f;
											mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, starty + position, mc.thePlayer.posZ, yaw -= .01, pitch += .07, (position <= .42)));
										}
									}
								}
							} else {
								if (compar <= 1.0) {
									mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, (float)starty, mc.thePlayer.posZ, yaw -= .01, pitch += .07, mc.thePlayer.onGround));
									
									float[] offsets = {0.41999998688697815f, 0.7531999805212024f};
									for (float position : offsets) {
										if (position == 0.7531999805212024) {

											mc.timer.timerSpeed = .8f;
										}
										mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + position, mc.thePlayer.posZ, yaw -= .01, pitch += .07, (position <= .42)));
									}
								} else if (compar <= 1.5) {
									mc.timer.timerSpeed = .7f;
									mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, (float)starty, mc.thePlayer.posZ, yaw -= .01, pitch += .07, mc.thePlayer.onGround));
									float[] offsets = {0.41999998688697815f, .32500f, .24000000f, -.06f};
									for (float position : offsets) {
										y += position;
										mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, y, mc.thePlayer.posZ, yaw -= .02, pitch += .03, false));
									}
								} else {
									mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, (float)starty, mc.thePlayer.posZ, yaw -= .01, pitch += .07, mc.thePlayer.onGround));
									
									mc.timer.timerSpeed = .6f;
									float[] offsets = {0.425f,0.821f,0.699f,0.599f,1.022f,1.372f,1.652f,1.869f};
									for (float position : offsets) {
										mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, y + position, mc.thePlayer.posZ, yaw -= .02, pitch += .03, false));
									}
								}
							}
							time.reset(); 
					}
				}
			}
		}
	}
}