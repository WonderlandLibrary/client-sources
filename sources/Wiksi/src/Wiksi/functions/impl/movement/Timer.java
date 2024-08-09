//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.SliderSetting;

@FunctionRegister(
        name = "Timer",
        type = Category.Movement
)
public class Timer extends Function {
    private final SliderSetting speed = new SliderSetting("Скорость", 2.0F, 0.1F, 10.0F, 0.1F);

    public Timer() {
        this.addSettings(new Setting[]{this.speed});
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        mc.timer.timerSpeed = (Float)this.speed.get();
    }

    private void reset() {
        mc.timer.timerSpeed = 1.0F;
    }

    public void onEnable() {
        super.onEnable();
        this.reset();
    }

    public void onDisable() {
        super.onDisable();
        this.reset();
    }
}
