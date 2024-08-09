package wtf.resolute.moduled.impl.movement;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.utiled.player.MoveUtils;

@ModuleAnontion(name = "Parkour", type = Categories.Movement,server = "")
public class Parkour extends Module {

    @Subscribe
    private void onUpdate(EventUpdate e) {

        if (MoveUtils.isBlockUnder(0.001f) && mc.player.isOnGround()) {
            mc.player.jump();
        }
    }

}
