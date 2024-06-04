package com.polarware.module.impl.combat.antibot;

import com.polarware.Client;
import com.polarware.module.impl.combat.AntiBotModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.value.Mode;

/**
 * @author Wykt
 * @since 2/04/2023
 */

public final class FuncraftAntiBot extends Mode<AntiBotModule> {
    public FuncraftAntiBot(String name, AntiBotModule parent) {
        super(name, parent);
    }

    @EventLink
    private final Listener<PreUpdateEvent> preUpdateEventListener = event -> {
        mc.theWorld.playerEntities.forEach(player -> {
            if(player.getDisplayName().getUnformattedText().contains("§")) {
                Client.INSTANCE.getBotComponent().remove(player);
                return;
            }

            Client.INSTANCE.getBotComponent().add(player);
        });
    };
}