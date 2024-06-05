package me.vaziak.sensation.client.impl.player;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.event.events.RunTickEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.impl.movement.Flight;
import me.vaziak.sensation.client.impl.movement.Speed;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;

public class Regen extends Module {
    private StringsProperty modeProperty = new StringsProperty("Mode", "How this cheat will function.", null, false, true, new String[]{"Vanilla", "CubeTest", "Falcon [TEST]"});
    private DoubleProperty health = new DoubleProperty("Health", "The health you want to regenerate at", null, 7, 1, 20, 1, null);
    private DoubleProperty packets = new DoubleProperty("Packets", "The amount of packets used for regen (more = faster)", null, 100, 5, 500, 5, null);
    
	private TimerUtil timerUtil;

	private TimerUtil waitTime;
	private boolean wait;
	public Regen() {
        super("Regen", Category.PLAYER);
        registerValue(modeProperty, packets, health);
        timerUtil = new TimerUtil();
        waitTime = new TimerUtil();
    }

    public void onDisable() {
    }

    public void onEnable() {
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        setMode(modeProperty.getSelectedStrings().get(0));
        if (modeProperty.getValue().get("CubeTest") && mc.thePlayer.getHealth() <= health.getValue().intValue() && mc.thePlayer.onGround) {
            if (mc.thePlayer.ticksExisted % 3 == 0) {
                mc.thePlayer.setSpeed(0);
                for (int i = 0; i < 30; i++) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                }
            } else {
                mc.thePlayer.setSpeed(mc.thePlayer.getSpeed() / 2);
            }
        }
        
        if (modeProperty.getValue().get("Falcon [TEST]") && !playerUpdateEvent.isPre()) {
        	if (!wait) {
	        	if (mc.thePlayer.onGround && mc.thePlayer.getHealth() < 10) {
	        		if (!timerUtil.hasPassed(5000)) {
		                double POSITIVE_INFINITY = Double.NEGATIVE_INFINITY;
		                double POSITIVE_INFINITY2 = Double.POSITIVE_INFINITY;
		                if (mc.thePlayer != null && mc.theWorld != null) {
		                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(POSITIVE_INFINITY2, POSITIVE_INFINITY, POSITIVE_INFINITY, true));
		                }
	        		} else {
	        			timerUtil.reset();
	        			wait = true;
	        			waitTime.reset();
	        		}
	            }
        	} else {
        		if (waitTime.hasPassed(4000)) {
        			wait = false;
        		}
        	}
        }

        if (modeProperty.getValue().get("Vanilla") && mc.thePlayer.getHealth() <= health.getValue().intValue()) {
            for (int i = 0; i < packets.getValue(); i++) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
            }
        }
    }
    
    @Collect
    public void onRunTick(RunTickEvent event) {

    }
    
    @Collect
    public void onPacketReceive(ProcessPacketEvent event) {
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            if (packet.getChatComponent().getUnformattedText().contains("You cannot go past the border.")) {
                event.setCancelled(true);
            }
        }
    }
    
    @Collect
    public void onPacketSend(SendPacketEvent event) {

    }

    private boolean isColliding(AxisAlignedBB box) {
        return mc.theWorld.checkBlockCollision(box);
    }
}