package de.tired.module.impl.list.misc;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.event.EventTarget;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;

@ModuleAnnotation(name = "FastPlace", category = ModuleCategory.MISC, clickG = "Places faster blocks")
public class FastPlace extends Module {

    public NumberSetting delay = new NumberSetting("delay", this, 0, 0, 3, 1);

    @EventTarget
    public void onRender(UpdateEvent e) {
        MC.rightClickDelayTimer = delay.getValueInt();
        setDesc("Delay " + delay.getValue());
    }

    @Override
    public void onState() {
    }

    @Override
    public void onUndo() {
        MC.rightClickDelayTimer = 4;

    }
}
