package white.floor.helpers.render;

import clickgui.setting.Setting;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class FullBright extends Feature {
    private float oldBrightness;

    public FullBright() {
        super("FullBright","Changes the gamut", 0, Category.VISUALS);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.gameSettings.gammaSetting = 10;
    }

    @Override
    public void onEnable() {
        NotificationPublisher.queue(this.getName(), "Was enabled.", NotificationType.INFO);
        super.onEnable();
        oldBrightness = mc.gameSettings.gammaSetting;
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = oldBrightness;
        NotificationPublisher.queue(this.getName(), "Was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}