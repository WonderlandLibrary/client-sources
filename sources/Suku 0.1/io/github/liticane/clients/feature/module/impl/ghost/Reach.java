package io.github.liticane.clients.feature.module.impl.ghost;


import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.util.player.MoveUtil;

import java.util.Random;

@Module.Info(name = "Reach", category = Module.Category.GHOST)
public class Reach extends Module {
    public NumberProperty minrange = new NumberProperty("Min Range", this, 3, 3, 6, 0.1);
    public NumberProperty maxrange = new NumberProperty("Max Range", this, 3, 3, 6, 0.1);
    public NumberProperty chance = new NumberProperty("Chance", this, 100, 1, 100, 1);
    public BooleanProperty onlymove = new BooleanProperty("Only When Move",this,false);
    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
        this.setSuffix(minrange.getValue() + " - " + maxrange.getValue());
    };

    public float getReachAmount() {
        Random random = new Random();
        if (random.nextInt(100) >= chance.getValue()) return 3.0f;
        if (!Client.INSTANCE.getModuleManager().get(Reach.class).isToggled()) return 3.0f;
        if (mc.player == null) return 3.0f;
        if (!MoveUtil.isMoving()) {
            if (onlymove.isToggled()) return 3.0f;
        }
        float actualreach = (float)maxrange.getValue() - (float)minrange.getValue();
        actualreach = (float)((double)actualreach * Math.abs(random.nextGaussian()));
        return actualreach + (float)minrange.getValue();
    }


}