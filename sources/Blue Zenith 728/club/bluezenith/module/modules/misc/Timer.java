package club.bluezenith.module.modules.misc;

import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.events.Listener;

public class Timer extends Module {
    private final FloatValue speed = new FloatValue("Speed", 1f, 0.1f, 5f, 0.05f).setIndex(1);

    public Timer() {
        super("Timer", ModuleCategory.MISC, "GameSpeed");
    }

    private float oldSpeed = 1f;

    @Listener
    public void onUpdate(UpdatePlayerEvent e) {
        if (oldSpeed != speed.get()) {
            mc.timer.timerSpeed = speed.get();
        }
    }

    @Override
    public void onEnable() {
        mc.timer.timerSpeed = speed.get();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
    }
}
