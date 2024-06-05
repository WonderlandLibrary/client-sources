package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.EventPreMotion;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;

@ModuleAnnotation(name = "NoBob", category = ModuleCategory.RENDER, clickG = "Cool arm animation idk")
public class NoBob extends Module {


    float oldGamma;

    @EventTarget
    public void onRender(EventPreMotion e) {
        MC.thePlayer.distanceWalkedModified = 0.0f;
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {


    }
}
