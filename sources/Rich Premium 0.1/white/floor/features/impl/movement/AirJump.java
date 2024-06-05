package white.floor.features.impl.movement;

import net.minecraft.network.play.server.SPacketDisconnect;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class AirJump extends Feature {

    public AirJump() {
        super("AirJump", "Jump in air.", 0, Category.MOVEMENT);
    }

    @EventTarget
    public void sdd(EventUpdate eventUpdate) {
        if(mc.gameSettings.keyBindJump.isKeyDown())
            mc.player.jump();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}
