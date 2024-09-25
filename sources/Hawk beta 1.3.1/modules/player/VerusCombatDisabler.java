package eze.modules.player;

import eze.modules.*;
import eze.events.*;
import eze.events.listeners.*;
import net.minecraft.network.play.client.*;

public class VerusCombatDisabler extends Module
{
    public VerusCombatDisabler() {
        super("FakeVerusCombatDisabler", 0, Category.PLAYER);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventPacket && e.isIncoming()) {
            final EventPacket event = (EventPacket)e;
            if (EventPacket.getPacket() instanceof C0FPacketConfirmTransaction) {
                event.setCancelled(true);
            }
        }
    }
}
