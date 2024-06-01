package io.github.liticane.electron.module.impl.movement;

import io.github.liticane.electron.event.api.listener.EventHandler;
import io.github.liticane.electron.event.api.listener.Listener;
import io.github.liticane.electron.event.impl.minecraft.player.MoveUpdateEvent;
import io.github.liticane.electron.module.Module;
import io.github.liticane.electron.module.ModuleType;

public class SprintModule extends Module {
    public SprintModule() {
        super("Sprint", ModuleType.MOVEMENT);
        this.setEnabled(true);
    }

    @EventHandler
    public Listener<MoveUpdateEvent> onMoveUpdateEvent = (event) -> {
        mc.thePlayer.setSprinting(true);
    };
}