package com.alan.clients.module.impl.movement;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.potion.PotionEffect;
import rip.vantage.commons.util.time.StopWatch;

import java.util.HashMap;

@ModuleInfo(aliases = {"module.movement.potionextender.name"}, description = "module.movement.potionextender.description", category = Category.MOVEMENT)
public class PotionExtender extends Module {

    private final NumberValue extendDuration = new NumberValue("Extend Duration", this, 10, 1, 60, 1);

    public final HashMap<PotionEffect, StopWatch> potions = new HashMap<>();

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        for (final PotionEffect potion : mc.thePlayer.getActivePotionEffects()) {
            if (potions.containsKey(potion)) {
                final StopWatch stopWatch = potions.get(potion);
                if (stopWatch.finished(this.extendDuration.getValue().longValue() * 1000)) {
                    mc.thePlayer.removePotionEffect(potion.getPotionID());
                    potions.remove(potion);
                }
            } else if (potion.duration == 1) {
                final StopWatch stopWatch = new StopWatch();
                stopWatch.reset();
                potions.put(potion, stopWatch);
            }
        }
    };
}