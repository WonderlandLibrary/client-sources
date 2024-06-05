package white.floor.features.impl.player;

import clickgui.setting.Setting;
import white.floor.Main;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;


public class NoPush extends Feature {

    public NoPush() {
        super("NoPush", "Does not repel",0, Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("Players", this, true));
        Main.settingsManager.rSetting(new Setting("Blocks", this, true));
        Main.settingsManager.rSetting(new Setting("Water", this, true));

    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}

