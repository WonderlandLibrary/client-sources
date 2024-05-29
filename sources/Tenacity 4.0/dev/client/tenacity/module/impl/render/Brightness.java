package dev.client.tenacity.module.impl.render;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;

public final class Brightness extends Module {
    private final EventListener<MotionEvent> motionEventEventListener = event -> {
        mc.gameSettings.gammaSetting = 100;
    };

    @Override
    public void onDisable(){
        mc.gameSettings.gammaSetting = 0;
        super.onDisable();
    }

    public Brightness() {
        super("Brightness", Category.RENDER, "changes the game brightness");
    }
}
