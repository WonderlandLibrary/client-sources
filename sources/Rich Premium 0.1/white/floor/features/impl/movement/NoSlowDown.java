package white.floor.features.impl.movement;

import clickgui.setting.Setting;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class NoSlowDown extends Feature {
    public NoSlowDown() {
        super("NoSlowDown", "no slowness.", 0, Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("Speed", this, 1, 0.1, 1, false));
        Main.settingsManager.rSetting(new Setting("Matrix", this, true));
    }

    @EventTarget
    public void eventUpdate(EventUpdate eventUpdate) {}

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}
