package com.polarware.module.impl.render.fullbright;

import com.polarware.module.impl.render.FullBrightModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.TickEvent;
import com.polarware.value.Mode;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * @author Strikeless (mode), Patrick (implementation)
 * @since 04.11.2021
 */
public final class EffectFullBright extends Mode<FullBrightModule> {

    public EffectFullBright(String name, FullBrightModule parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<TickEvent> onTick = event -> {
        mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, Integer.MAX_VALUE, 1));
    };

    @Override
    public void onDisable() {
        if (mc.thePlayer.isPotionActive(Potion.nightVision)) {
            mc.thePlayer.removePotionEffect(Potion.nightVision.id);
        }
    }
}