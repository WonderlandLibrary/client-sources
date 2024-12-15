package com.alan.clients.module.impl.render.fullbright;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.module.impl.render.FullBright;
import com.alan.clients.value.Mode;

public final class GammaFullBright extends Mode<FullBright> {

    private float oldGamma;

    public GammaFullBright(String name, FullBright parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        mc.gameSettings.gammaSetting = 100.0F;
    };

    @Override
    public void onEnable() {
        oldGamma = mc.gameSettings.gammaSetting;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = oldGamma;
    }
}