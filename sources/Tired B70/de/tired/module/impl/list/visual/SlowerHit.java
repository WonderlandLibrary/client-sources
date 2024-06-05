package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.module.ModuleManager;

@ModuleAnnotation(name = "SlowerHIT", category = ModuleCategory.RENDER, clickG = "Slower hit animation")
public class SlowerHit extends Module {

    public NumberSetting value = new NumberSetting("value",  this, 1, 1, 100, 1);

    public static SlowerHit getInstance() {
        return ModuleManager.getInstance(SlowerHit.class);
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
