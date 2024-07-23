package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.SafeWalkEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;

@ModuleData(name = "SafeWalk", description = "Prevents you from falling off edges", category = ModuleCategory.MOVEMENT)
public class SafeWalkModule extends Module {

    @Listen
    public void onSafeWalkEvent(SafeWalkEvent event) {
        if(Methods.mc.thePlayer == null || Methods.mc.theWorld == null)
            return;

        event.setSafe(true);
    }

}
