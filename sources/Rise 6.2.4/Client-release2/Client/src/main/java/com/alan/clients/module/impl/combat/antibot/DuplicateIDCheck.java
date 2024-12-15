package com.alan.clients.module.impl.combat.antibot;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.impl.combat.AntiBot;
import com.alan.clients.value.Mode;

public final class DuplicateIDCheck extends Mode<AntiBot> {

    public DuplicateIDCheck(String name, AntiBot parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.theWorld.playerEntities.forEach(player -> {
            if (mc.theWorld.playerEntities.stream().anyMatch(player2 -> player2.getEntityId() == player.getEntityId() && player2 != player)) {
                Client.INSTANCE.getBotManager().add(this, player);
            }
        });
    };

    @Override
    public void onDisable() {
        Client.INSTANCE.getBotManager().clear(this);
    }
}