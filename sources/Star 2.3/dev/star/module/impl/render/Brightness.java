package dev.star.module.impl.render;

import dev.star.event.impl.player.MotionEvent;
import dev.star.module.Category;
import dev.star.module.Module;

public final class Brightness extends Module {

    @Override
    public void onMotionEvent(MotionEvent event) {
        mc.gameSettings.gammaSetting = 100;
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = 0;
        super.onDisable();
    }

    public Brightness() {
        super("Brightness", Category.RENDER, "changes the game brightness");
    }

}
