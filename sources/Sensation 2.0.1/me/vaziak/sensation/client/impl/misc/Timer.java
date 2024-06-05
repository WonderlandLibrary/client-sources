package me.vaziak.sensation.client.impl.misc;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;

public class Timer extends Module {
    private DoubleProperty timerSpeed = new DoubleProperty("Timer Speed", "Games timer speed", null, 1, .1, 20, .1, null);

    public Timer() {
        super("Timer", Category.MISC);
        registerValue(timerSpeed);
    }

    public void onEnable() {
        mc.timer.timerSpeed = 1.0f;
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        if (mc.timer.timerSpeed != timerSpeed.getValue().floatValue()) {
            mc.timer.timerSpeed = timerSpeed.getValue().floatValue();
        }
    }
}