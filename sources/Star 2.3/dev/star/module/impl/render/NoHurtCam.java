package dev.star.module.impl.render;

import dev.star.event.impl.render.HurtCamEvent;
import dev.star.module.Category;
import dev.star.module.Module;

public class NoHurtCam extends Module {

    public NoHurtCam() {
        super("NoHurtCam", Category.RENDER, "removes shaking after being hit");
    }

    @Override
    public void onHurtCamEvent(HurtCamEvent e) {
        e.cancel();
    }

}
