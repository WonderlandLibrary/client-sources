package io.github.liticane.monoxide.module.impl.misc;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.listener.event.minecraft.input.InputEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.math.MathUtil;
import io.github.liticane.monoxide.util.math.time.TimeHelper;

@ModuleData(name = "AntiAFK", description = "Moves to not get you kicked", category = ModuleCategory.MISCELLANEOUS)
public class AntiAFKModule extends Module {

    private final TimeHelper stopwatch = new TimeHelper();
    private int direction = -1;

    @Listen
    public void onInputEvent(InputEvent event) {
        if (stopwatch.hasReached(500L + MathUtil.getAdvancedRandom(0, 50))) {
            direction = -direction;
            stopwatch.reset();
        }

        event.setMoveForward(0);
        event.setMoveStrafing(direction * 1.0F);
    }

}