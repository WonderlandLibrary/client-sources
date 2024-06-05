package white.floor.features.impl.movement;

import clickgui.setting.Setting;
import net.minecraft.potion.Potion;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.movement.MovementHelper;

public class WaterSpeed extends Feature {

    public WaterSpeed() {
        super("WaterSpeed", "boost u in water.", 0, Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("Speed", this, 0.3, 0.1, 1, false));
        Main.settingsManager.rSetting(new Setting("SpeedPotion Check", this, true));
    }

    @EventTarget
    public void water(EventUpdate eventUpdate) {
        if (mc.player.isInWater()) {
            if (Main.settingsManager.getSettingByName(Main.featureDirector.getModule(WaterSpeed.class), "SpeedPotion Check").getValBoolean()) {
                if (mc.player.isPotionActive(Potion.getPotionById(1))) {
                    MovementHelper.setSpeed(Main.settingsManager.getSettingByName(Main.featureDirector.getModule(WaterSpeed.class), "Speed").getValDouble());
                }
            } else {
                MovementHelper.setSpeed(Main.settingsManager.getSettingByName(Main.featureDirector.getModule(WaterSpeed.class), "Speed").getValDouble());
            }
        }
    }
}
