package eze.modules.player;

import eze.modules.*;
import eze.util.*;
import eze.events.*;
import eze.events.listeners.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class FastEat extends Module
{
    Timer timer;
    boolean PlayerEat;
    
    public FastEat() {
        super("FastEat", 0, Category.PLAYER);
        this.timer = new Timer();
        this.PlayerEat = false;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && e.isPre() && !this.mc.thePlayer.isBlocking() && this.mc.thePlayer.isEating()) {
            for (int i = 0; i < 51; ++i) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
            }
        }
    }
}
