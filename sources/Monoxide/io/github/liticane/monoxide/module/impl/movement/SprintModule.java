package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.module.impl.player.ScaffoldWalkModule;
import net.minecraft.client.settings.KeyBinding;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.listener.event.minecraft.game.RunTickEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;

@ModuleData(name = "Sprint", description = "Makes you sprint automatically.", category = ModuleCategory.MOVEMENT)
public class SprintModule extends Module {

    @Listen
    public void onTickEvent(RunTickEvent event) {
        ScaffoldWalkModule scaffoldWalkModule = ModuleManager.getInstance().getModule("ScaffoldWalk");
        if (scaffoldWalkModule.isEnabled()) {
            return;
        }
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
    }

}