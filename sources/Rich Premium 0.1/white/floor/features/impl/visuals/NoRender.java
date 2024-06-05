package white.floor.features.impl.visuals;

import clickgui.setting.Setting;
import white.floor.Main;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class NoRender extends Feature {

    public NoRender() {
        super("NoRender","puk", 0, Category.VISUALS);
        Main.settingsManager.rSetting(new Setting("HurtCam", this, true));
        Main.settingsManager.rSetting(new Setting("CameraClip", this, true));
        Main.settingsManager.rSetting(new Setting("AntiTotemAnimation", this, false));
        Main.settingsManager.rSetting(new Setting("NoFireOverlay", this, false));
        Main.settingsManager.rSetting(new Setting("NoPotionDebug", this, false));
        Main.settingsManager.rSetting(new Setting("NoExpBar", this, false));
        Main.settingsManager.rSetting(new Setting("NoPumpkinOverlay", this, false));
        Main.settingsManager.rSetting(new Setting("NoBossBar", this, false));
        Main.settingsManager.rSetting(new Setting("NoArrowInPlayer", this, false));
        Main.settingsManager.rSetting(new Setting("NoArmor", this, false));
    }
    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}
