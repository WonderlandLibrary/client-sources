package lol.point.returnclient.module.impl.player;

import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.NumberSetting;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;

@ModuleInfo(name = "FastPlace", description = "change the fast", category = Category.PLAYER)
public class FastPlace extends Module {

    private final NumberSetting delay = new NumberSetting("Delay", 0, 0, 10);

    public FastPlace(){
        addSettings(delay);
    }

    public String getSuffix() {
        return String.valueOf(delay.value.intValue()) + " ticks";
    }

    public void onDisable() {
        mc.rightClickDelayTimer = 6;
    }

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(eventUpdate -> {
        mc.rightClickDelayTimer = delay.value.intValue();
    });

}
