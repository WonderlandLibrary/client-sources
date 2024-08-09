package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;

@FunctionRegister(name = "NoJumpDelay", type = Category.Player)
public class NoJumpDelay extends Function {
    @Subscribe
    public void onUpdate(EventUpdate e) {
        mc.player.jumpTicks = 0;
    }
}
