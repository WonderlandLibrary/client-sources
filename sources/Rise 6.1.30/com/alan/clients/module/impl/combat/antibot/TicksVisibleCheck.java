package com.alan.clients.module.impl.combat.antibot;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.combat.AntiBot;
import com.alan.clients.value.Mode;

public final class TicksVisibleCheck extends Mode<AntiBot> {

    public TicksVisibleCheck(String name, AntiBot parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.theWorld.playerEntities.forEach(player -> {
            if (player.ticksVisible < 160) {
                Client.INSTANCE.getBotManager().add(this, player);
            } else if (player.ticksExisted == 160) {
                Client.INSTANCE.getBotManager().remove(this, player);
            }
        });
    };

    @Override
    public void onDisable() {
        Client.INSTANCE.getBotManager().clear(this);
    }
}