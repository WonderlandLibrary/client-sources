package lol.point.returnclient.module.impl.player;

import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.NumberSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

@ModuleInfo(
        name = "Timer",
        description = "alter game speed",
        category = Category.PLAYER
)
public class Timer extends Module {

    private final NumberSetting speed = new NumberSetting("Timer speed", 1.0, 0.1, 5, 1);

    public Timer() {
        addSettings(speed);
    }

    public String getSuffix() {
        return String.valueOf(speed.value.floatValue());
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(eventUpdate -> {
        mc.timer.timerSpeed = speed.value.floatValue();
    });

    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
    }

}
