package io.github.liticane.monoxide.module.impl.misc;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;

@ModuleData(name = "FastPlace", description = "Removes placing delay", category = ModuleCategory.MISCELLANEOUS)
public class FastPlaceModule extends Module {

    @Listen
    public void onUpdate(UpdateEvent updateEvent) {
        mc.rightClickDelayTimer = 0;
    }

    @Override
    public void onDisable() {
        mc.rightClickDelayTimer = 6;
    }

}