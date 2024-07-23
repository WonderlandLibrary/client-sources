package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;

@ModuleData(name = "FastLadder", description = "Climb up ladders faster", category = ModuleCategory.MOVEMENT)
public class FastLadderModule extends Module {
    private final NumberValue<Float> speed = new NumberValue<Float>("Speed", this, 0.32f, 0.05f, 5f, 0);

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Listen
    public void onUpdateMotion(UpdateMotionEvent updateMotionEvent) {
        if(updateMotionEvent.getType() == UpdateMotionEvent.Type.MID) {
            if(Methods.mc.thePlayer.isOnLadder() && this.isMoving()) {
                Methods.mc.thePlayer.motionY = speed.getValue();
            }
        }
    }

}
