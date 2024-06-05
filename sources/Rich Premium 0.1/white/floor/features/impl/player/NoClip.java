package white.floor.features.impl.player;

import net.minecraft.entity.Entity;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class NoClip extends Feature {

    public NoClip() {
        super("NoClip","bebra", 0, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
            mc.player.noClip = true;
            mc.player.motionY = 0.00001;

            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.motionY = 0.4;
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.player.motionY = -0.4;
            }
        }


    public static boolean isNoClip(Entity entity) {
        if (Main.featureDirector.getModule(NoClip.class).isToggled() && mc.player != null
                && (mc.player.ridingEntity == null || entity == mc.player.ridingEntity))
            return true;
        return entity.noClip;

    }

    public void onDisable() {
        mc.player.noClip = false;
        super.onDisable();
    }
}
