package me.vaziak.sensation.client.impl.movement;

import java.security.SecureRandom;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.utils.client.ChatUtils;
import me.vaziak.sensation.utils.math.MathUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

/**
 * Made by Jonathan H. (Niada) ------------------------------- ethereal.rip @
 * 9:53PM - 3/19/2019 -------------------------------
 **/
public class NoFall extends Module {
	private SecureRandom secRan;
    private int hypixel;
    public NoFall() {
        super("No Fall", Category.MOVEMENT);
        registerValue(prop_mode);
        secRan = new SecureRandom();
    }
    protected StringsProperty prop_mode = new StringsProperty("Mode", "Mode es modes!", false, false,new String[]{"Spoof", "C03AAC", "NoGround", "Watchdog"});

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        setMode(prop_mode.getSelectedStrings().get(0));
        if (mc.thePlayer.isInWater()) return;
        if (prop_mode.getValue().get("C03AAC")) {
        	if (mc.thePlayer.fallDistance != 0.0 && mc.thePlayer.fallDistance >= 2.5) {
        		playerUpdateEvent.setOnGround(true);
        		if (mc.thePlayer.onGround) {

        			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
        					mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
        					mc.thePlayer.posX + 0.2, mc.thePlayer.posY, mc.thePlayer.posZ + 0.2, true));
        			mc.thePlayer.setSpeed(0.4);
        			mc.thePlayer.fallDistance = 0;
        		} else {
        			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
        					mc.thePlayer.posX, Double.NEGATIVE_INFINITY, mc.thePlayer.posZ, false));
        			mc.thePlayer.fallDistance = 0.5F;
        		}
        	} 
        }
        if (prop_mode.getValue().get("NoGround")) {
        	playerUpdateEvent.setOnGround(false);
        }

        if (prop_mode.getValue().get("Spoof")) {
        	if (mc.thePlayer.fallDistance >= 2.75) {
        		playerUpdateEvent.setOnGround(true);
        	}
 
        }
    }
    
    @Collect
    public void onPacket(SendPacketEvent e) {
 
    }
}
