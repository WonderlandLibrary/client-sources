package de.tired.module.impl.list.movement;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;

@ModuleAnnotation(name = "FastLadder", category = ModuleCategory.MOVEMENT, clickG = "Climb faster on ladders")
public class FastLadder extends Module {

    public int fastLadderTicks = 0;

    @EventTarget
    public void onUpdate(UpdateEvent e) {

        if (!shouldExecuteFastLadder()) return;

        MC.thePlayer.motionY += 0.1F;


    }

    public boolean shouldExecuteFastLadder() {
        return MC.thePlayer.isOnLadder() && MC.thePlayer.hurtTime == 0;
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
