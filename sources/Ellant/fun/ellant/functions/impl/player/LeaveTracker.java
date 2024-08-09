package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventEntityLeave;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;

@FunctionRegister(name = "LeaveTracker", type = Category.PLAYER, desc = "Пон?")
public class LeaveTracker extends Function {


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
