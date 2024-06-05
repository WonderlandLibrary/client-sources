package white.floor.features.impl.visuals;

import clickgui.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventTransformSideFirstPerson;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class ViewModel extends Feature {
    public static Setting rightx;
    public static Setting righty;
    public static Setting rightz;
    public static Setting leftx;
    public static Setting lefty;
    public static Setting leftz;

    public ViewModel() {
        super("ViewModel" ,"Changes the position of the hands" ,0, Category.VISUALS);
        leftx = new Setting("LeftX", this, 0.0, -2.0, 2.0, false);
        Main.settingsManager.rSetting(leftx);
        lefty = new Setting("LeftY", this, 0.2, -2.0, 2.0, false);
        Main.settingsManager.rSetting(lefty);
        leftz = new Setting("LeftZ", this, 0.2, -2.0, 2.0, false);
        Main.settingsManager.rSetting(leftz);
        rightx = new Setting("RightX", this, 0.0, -2.0, 2.0, false);
        Main.settingsManager.rSetting(rightx);
        righty = new Setting("RightY", this, 0.2, -2.0, 2.0, false);
        Main.settingsManager.rSetting(righty);
        rightz = new Setting("RightZ", this, 0.2, -2.0, 2.0, false);
        Main.settingsManager.rSetting(rightz);
    }

    @EventTarget
    public void onSidePerson(EventTransformSideFirstPerson event) {
        if (event.getEnumHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.translate(rightx.getValDouble(), righty.getValDouble(), rightz.getValDouble());
        }
        if (event.getEnumHandSide() == EnumHandSide.LEFT) {
            GlStateManager.translate(-leftx.getValDouble(), lefty.getValDouble(), leftz.getValDouble());
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}