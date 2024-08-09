package wtf.resolute.moduled.impl.movement;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.SliderSetting;

@ModuleAnontion(name = "Timer", type = Categories.Movement,server = "")
public class Timer extends Module {

    private final SliderSetting speed = new SliderSetting("Скорость", 2f, 0.1f, 10f, 0.1f);

    public Timer() {
        addSettings(speed);
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        mc.timer.timerSpeed = speed.get();
    }

    private void reset() {
        mc.timer.timerSpeed = 1;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        reset();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        reset();
    }
}
