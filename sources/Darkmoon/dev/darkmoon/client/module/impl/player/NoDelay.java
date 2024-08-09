package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;

@ModuleAnnotation(name = "NoDelay", category = Category.PLAYER)
public class NoDelay extends Module {

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.player.jumpTicks = 0;
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.jumpTicks = 10;
            mc.rightClickDelayTimer = 6;
        }
        super.onDisable();
    }
}
