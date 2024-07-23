package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.game.RunTickEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.player.PlayerHandler;

@ModuleData(name = "MoveCorrect", description = "Aligns your movement yaw properly", category = ModuleCategory.MOVEMENT, alwaysRegistered = true)
public class MoveCorrectModule extends Module {

    public ModeValue mode = new ModeValue("Mode", this, new String[]{"Strict", "Silent", "Aggressive"});

    @Listen
    public final void onTick(RunTickEvent runTickEvent) {
        PlayerHandler.moveFix = this.isEnabled();
        if(this.isEnabled()) {
            switch (this.mode.getValue()) {
                case "Strict": {
                    PlayerHandler.currentMode = PlayerHandler.MoveFixMode.STRICT;
                    break;
                }
                case "Aggressive": {
                    PlayerHandler.currentMode = PlayerHandler.MoveFixMode.AGGRESSIVE;
                    break;
                }
                case "Silent": {
                    PlayerHandler.currentMode = PlayerHandler.MoveFixMode.SILENT;
                    break;
                }
            }
        }
    }

}