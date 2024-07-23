package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.StepEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;

@ModuleData(name = "Step", description = "Makes you walk up blocks.", category = ModuleCategory.MOVEMENT)
public class StepModule extends Module {
    private final ModeValue mode = new ModeValue("Mode", this, new String[]{ "Vanilla" });
    private final NumberValue<Float> height = new NumberValue<>("Height", this, 1.0F, 0.5F, 2.0F, 1);

    @Override
    public String getSuffix() {
    	return mode.getValue();
    }

    @Listen
    public final void onStep(StepEvent stepEvent) {
        if (mode.getValue().equals("Vanilla")) {
            stepEvent.setStepHeight(height.getValue());
        }
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        mc.thePlayer.stepHeight = 0.6F;
        mc.timer.timerSpeed = 1;
    }
}