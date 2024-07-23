package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.listener.event.minecraft.input.InputEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.value.impl.NumberValue;

@ModuleData(name = "KineticSneak", description = "Allows you to sneak faster.", category = ModuleCategory.MOVEMENT)
public class KineticSneakModule extends Module {

    private final NumberValue<Double> multiplier = new NumberValue<>("Multiplier", this, 0.3D, 0.0D, 1.0D, 1);

    @Listen
    public void onInputEvent(InputEvent event) {
        event.setMultiplier(multiplier.getValue());
    }

}
