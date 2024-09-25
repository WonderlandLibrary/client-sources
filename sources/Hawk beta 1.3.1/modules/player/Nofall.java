package eze.modules.player;

import eze.modules.*;
import eze.events.*;
import eze.events.listeners.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Nofall extends Module
{
    int packetsent;
    
    public Nofall() {
        super("Nofall", 49, Category.PLAYER);
        this.packetsent = 0;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && e.isPre() && this.mc.thePlayer.fallDistance > 3.0f) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
    }
}
