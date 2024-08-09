package wtf.resolute.moduled.impl.misc;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;

import java.util.concurrent.ThreadLocalRandom;

@ModuleAnontion(name = "AntiAFK", type = Categories.Player,server = "")
public class AntiAFK extends Module {

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (mc.player.ticksExisted % 200 != 0) return;

        if (mc.player.isOnGround()) mc.player.jump();
        mc.player.rotationYaw += ThreadLocalRandom.current().nextFloat(-10, 10);
    }
}
