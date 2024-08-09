package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.SliderSetting;

@FunctionRegister(name = "Timer", type = Category.MOVEMENT, desc = "Ускоряет вас")
public class Timer extends Function {

    private final SliderSetting speed = new SliderSetting("Скорость", 2f, 0.1f, 10f, 0.1f);

    public Timer() {
        addSettings(speed);
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        mc.timer.timerSpeed = speed.get();
    }

    void reset() {
        mc.timer.timerSpeed = 1;
    }

    @Override
    public boolean onEnable() {
        super.onEnable();
        reset();
        return false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        reset();
    }
}
