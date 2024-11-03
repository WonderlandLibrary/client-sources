package dev.star.module.impl.player;

import dev.star.event.impl.player.MotionEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.NumberSetting;

@SuppressWarnings("unused")
public final class Timer extends Module {

    private final NumberSetting amount = new NumberSetting("Amount", 1, 10, 0.1, 0.1);

    @Override
    public void onMotionEvent(MotionEvent event) {
        mc.timer.timerSpeed = amount.getValue().floatValue();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
        super.onDisable();
    }

    public Timer() {
        super("Timer", Category.PLAYER, "changes game speed");
        this.addSettings(amount);
    }

}
