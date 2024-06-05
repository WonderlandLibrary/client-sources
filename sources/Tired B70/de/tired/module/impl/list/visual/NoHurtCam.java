package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;

@ModuleAnnotation(name = "NoHurtCam", category = ModuleCategory.RENDER, clickG = "You wont see any Damage!")
public class NoHurtCam extends Module {

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
