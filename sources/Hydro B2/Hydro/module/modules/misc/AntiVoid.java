package Hydro.module.modules.misc;

import java.util.ArrayList;
import java.util.Collections;

import Hydro.Client;
import Hydro.event.Event;
import Hydro.event.events.EventPacket;
import Hydro.event.events.EventUpdate;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.MovementUtil;
import Hydro.util.Timer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class AntiVoid extends Module {
	
    public AntiVoid() {
		super("AntiVoid", 0, true, Category.MISC, "Stops you from falling in the void");
	}

    

	//NumberValue<Long> wait = new NumberValue<Long>("Lagback Timer", 700L, 0L, 2000L);
    private ArrayList<Packet> packets = new ArrayList<>();
    private double flyHeight;
    private Timer timer = new Timer();
    private boolean flagged;

    public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}
    
    @Override
    public void onEnable() {
        packets.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        packets.clear();
        super.onDisable();
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventPacket) {
            Packet packet = (Packet) ((EventPacket) e).getPacket();

            if (packet instanceof C03PacketPlayer) {
                if (!(flyHeight > 40) || !(mc.thePlayer.fallDistance > 0) || Client.instance.moduleManager.getModuleByName("Flight").isEnabled() || Client.instance.moduleManager.getModuleByName("LongJump").isEnabled()) {

                    packets.add(packet);
                    if (packets.size() > 5) {
                        packets.remove(0);
                    }
                } else if(mc.thePlayer.fallDistance < 2){
                    e.setCancelled(true);
                }

            }

        } else if (e instanceof EventUpdate) {
            EventUpdate event = (EventUpdate) e;

            updateFlyHeight();
            if(MovementUtil.isOnGround(0.05)) {
                setFlagged(false);
            }
            if (flyHeight > 40 && mc.thePlayer.fallDistance > 0 && !Client.instance.moduleManager.getModuleByName("Flight").isEnabled() && !Client.instance.moduleManager.getModuleByName("LongJump").isEnabled() && !packets.isEmpty() && !isFlagged()) {


                Collections.reverse(packets);
                for (int i = 0; i < packets.size(); i++) {
                    mc.thePlayer.sendQueue.addToSendQueueNoEvent(packets.get(i));
                }
                
                packets.clear();
            }
        }
    }

    public void updateFlyHeight() {
        double h = 1.0D;
        AxisAlignedBB box = mc.thePlayer.getEntityBoundingBox().expand(0.0625D, 0.0625D, 0.0625D);

        for (this.flyHeight = 0.0D; this.flyHeight < mc.thePlayer.posY; this.flyHeight += h) {
            AxisAlignedBB nextBox = box.offset(0.0D, -this.flyHeight, 0.0D);
            if (mc.theWorld.checkBlockCollision(nextBox)) {
                if (h < 0.0625D) {
                    break;
                }

                this.flyHeight -= h;
                h /= 2.0D;
            }
        }

    }
}
