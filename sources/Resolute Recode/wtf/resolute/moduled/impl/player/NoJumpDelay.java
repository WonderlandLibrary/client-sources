package wtf.resolute.moduled.impl.player;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;

@ModuleAnontion(name = "NoJumpDelay", type = Categories.Player,server = "")
public class NoJumpDelay extends Module {

    @Subscribe
    public void onUpdate(EventUpdate e) {
        mc.player.jumpTicks = 0;
    }
}
