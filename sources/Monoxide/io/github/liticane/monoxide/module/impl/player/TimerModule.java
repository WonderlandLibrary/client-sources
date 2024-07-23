package io.github.liticane.monoxide.module.impl.player;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;

@ModuleData(name = "Timer", description = "Increases game speed", category = ModuleCategory.MISCELLANEOUS)
public class TimerModule extends Module {
    private final NumberValue<Float> timerSpeed = new NumberValue<Float>("Time Speed", this, 1f, 0.1f, 5f, 2);

    @Listen
    public void onUpdate(UpdateEvent updateEvent) {
        mc.timer.timerSpeed = timerSpeed.getValue();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
    }

}