package com.polarware.module.impl.render.fullbright;

import com.polarware.module.impl.render.FullBrightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.TickEvent;
import com.polarware.value.Mode;

/**
 * @author Strikeless
 * @since 04.11.2021
 */
public final class GammaFullBright extends Mode<FullBrightModule> {

    private float oldGamma;

    public GammaFullBright(String name, FullBrightModule parent) {
        super(name, parent);
    }


    @EventLink()
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