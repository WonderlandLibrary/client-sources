package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;

import java.util.Arrays;

@ModuleAnnotation(name = "AntiAFK", category = Category.PLAYER)
public class AntiAFK extends Module {
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (!mc.player.isDead && mc.player.getHealth() > 0) {
            if (mc.player.ticksExisted % 400 == 0) mc.player.sendChatMessage("/dkasdkmw");
        }
    }
}
