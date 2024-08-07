package me.r.touchgrass.module.modules.combat;

import com.darkmagician6.eventapi.EventTarget;
import me.r.touchgrass.module.Category;
import me.r.touchgrass.module.Info;
import me.r.touchgrass.module.Module;
import net.minecraft.client.Minecraft;
import me.r.touchgrass.events.EventUpdate;
import me.r.touchgrass.settings.Setting;
import me.r.touchgrass.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by r on 05/02/2021
 */

@Info(name = "AutoClicker", description = "Automatically clicks for you", category = Category.Combat)
public class AutoClicker extends Module {

    long leftLastSwing;
    long leftDelay;
    long rightLastSwing;
    long rightDelay;

    public AutoClicker() {
        ArrayList<String> mode = new ArrayList<>();
        mode.add("Left Click");
        mode.add("Right Click");
        addSetting(new Setting("Type", this, "Left Click", mode));
        addSetting(new Setting("Min. CPS", this, 4, 1, 20, true));
        addSetting(new Setting("Max. CPS", this, 8, 1, 20, true));
    }


    @EventTarget
    public void onUpdate(EventUpdate e) {
        int minCPS = (int) h2.settingsManager.getSettingByName(this, "Min. CPS").getValue();
        int maxCPS = (int) h2.settingsManager.getSettingByName(this, "Min. CPS").getValue();
        boolean leftClick = h2.settingsManager.getSettingByName(this, "Type").getMode().equalsIgnoreCase("Left Click");
        boolean rightClick = h2.settingsManager.getSettingByName(this, "Type").getMode().equalsIgnoreCase("Right Click");

        if (System.currentTimeMillis() - this.leftLastSwing >= this.leftDelay) {
            if (mc.gameSettings.keyBindAttack.isKeyDown() && leftClick && System.currentTimeMillis() - this.leftLastSwing >= this.leftDelay) {
                mc.clickMouse();
                this.leftLastSwing = System.currentTimeMillis();
                this.leftDelay = TimeUtils.randomDelay(minCPS, maxCPS);
            }
            if (mc.gameSettings.keyBindUseItem.isKeyDown() && !mc.thePlayer.isUsingItem() && rightClick && System.currentTimeMillis() - this.rightLastSwing >= this.rightDelay) {
                mc.rightClickMouse();
                this.rightLastSwing = System.currentTimeMillis();
                this.rightDelay = TimeUtils.randomDelay(minCPS, maxCPS);
            }
        }
    }
}
