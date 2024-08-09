package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.SliderSetting;
import net.minecraftforge.eventbus.api.Event;


@FunctionRegister(name = "AutoEventdelay", type = Category.Player)
public class EventDelay extends Function {
    private final SliderSetting time = new SliderSetting("Заддержка", 30, 5, 60, 5);

    private long lastSentTime = 0;
    public EventDelay() {
        addSettings(this.time);
    }

    @Subscribe
    public void onEvent(Event event) {
        func();
    }

    public void func() {

        long currentTime = System.currentTimeMillis();
        long delay = this.time.get().longValue() * 1000;

        if (currentTime - lastSentTime >= delay) {
            mc.player.sendChatMessage("/event delay");
            lastSentTime = currentTime;
        }
    }
}
