package com.polarware.module.impl.combat.antibot;

import com.polarware.Client;
import com.polarware.module.impl.combat.AntiBotModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.value.Mode;

public final class AdvancedAntiBot extends Mode<AntiBotModule> {

    public AdvancedAntiBot(String name, AntiBotModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        mc.theWorld.playerEntities.forEach(player -> {
            if (mc.thePlayer.getDistanceSq(player.posX, mc.thePlayer.posY, player.posZ) > 200) {
                Client.INSTANCE.getBotComponent().remove(player);
            }

            if (player.ticksExisted < 5 || player.isInvisible() || mc.thePlayer.getDistanceSq(player.posX, mc.thePlayer.posY, player.posZ) > 100 * 100) {
                Client.INSTANCE.getBotComponent().add(player);
            }
        });
    };

}