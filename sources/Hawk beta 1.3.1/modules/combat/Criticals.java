package eze.modules.combat;

import eze.modules.*;
import eze.events.*;
import eze.events.listeners.*;
import net.minecraft.network.play.client.*;

public class Criticals extends Module
{
    public Criticals() {
        super("Criticals", 0, Category.COMBAT);
    }
    
    @Override
    public void onEnable() {
        this.mc.thePlayer.jump();
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventPacket) {
            final EventPacket event = (EventPacket)e;
            if (EventPacket.getPacket() instanceof C03PacketPlayer) {
                final C03PacketPlayer packet = (C03PacketPlayer)EventPacket.getPacket();
                packet.onground = false;
            }
        }
    }
}
