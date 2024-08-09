package wtf.resolute.moduled.impl.misc;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.evented.EventEntityLeave;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;

@ModuleAnontion(name = "LeaveTracker", type = Categories.Misc,server = "")
public class LeaveTracker extends Module {

    @Subscribe
    private void onEntityLeave(EventEntityLeave eel) {
        Entity entity = eel.getEntity();

        if (!isEntityValid(entity)) {
            return;
        }

        String message = "Игрок "
                + entity.getDisplayName().getString()
                + " ливнул на "
                + entity.getStringPosition();

        print(message);
    }

    private boolean isEntityValid(Entity entity) {
        if (!(entity instanceof AbstractClientPlayerEntity) || entity instanceof ClientPlayerEntity) {
            return false;
        }

        return !(mc.player.getDistance(entity) < 100);
    }
}
