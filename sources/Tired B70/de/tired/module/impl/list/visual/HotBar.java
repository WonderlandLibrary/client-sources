package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.module.ModuleManager;

@ModuleAnnotation(name = "HotBar", category = ModuleCategory.RENDER, clickG = "Better hotbar design")
public class HotBar extends Module {
    public BooleanSetting smooth = new BooleanSetting("smooth", this, true);

    public ModeSetting hotbarMode = new ModeSetting("hotbarMode", this, new String[]{"B40", "B48", "B50"});

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }

    public static HotBar getInstance() {
        return ModuleManager.getInstance(HotBar.class);
    }

}
