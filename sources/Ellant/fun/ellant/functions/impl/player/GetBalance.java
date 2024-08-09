package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import com.jagrosh.discordipc.IPCClient;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.SliderSetting;
import net.minecraftforge.eventbus.api.Event;


@FunctionRegister(name = "GetBalance", type = Category.PLAYER, desc = "Автоматически пишет </balance>")


public class GetBalance extends Function {
    private final SliderSetting time = new SliderSetting("Задержка", 30, 5, 60, 5);

    private long lastSentTime = 0;
    public GetBalance() {
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
            mc.player.sendChatMessage("/balance");
            lastSentTime = currentTime;
        }
    }
}