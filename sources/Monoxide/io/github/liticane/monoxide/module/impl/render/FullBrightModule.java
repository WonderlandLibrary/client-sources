package io.github.liticane.monoxide.module.impl.render;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;

@ModuleData(name = "FullBright", description = "Increases brightness to the maximum", category = ModuleCategory.RENDER)
public class FullBrightModule extends Module {
    private float oldGamma;

    @Listen
    public void onUpdate(UpdateEvent updateEvent) {
        mc.gameSettings.gammaSetting = 100F;
    }

    @Override
    public void onEnable() {
        this.oldGamma = mc.gameSettings.gammaSetting;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = this.oldGamma;
    }

}