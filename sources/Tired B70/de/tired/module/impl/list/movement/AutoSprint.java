package de.tired.module.impl.list.movement;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.tired.other.ClientHelper;

@ModuleAnnotation(name = "AutoSprint", category = ModuleCategory.MOVEMENT, clickG = "Sprinting automatically")
public class AutoSprint extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        MC.thePlayer.setSprinting(ClientHelper.INSTANCE.moving());
    }
    @Override
    public void onState() {

    }
    @Override
    public void onUndo() {

    }
}
