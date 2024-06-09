/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Misc;

import com.darkmagician6.eventapi.EventTarget;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.Timer;
import us.amerikan.amerikan;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.TimeHelper;

public class Phase
extends Module {
    TimeHelper time = new TimeHelper();

    public Phase() {
        super("Phase", "Phase", 0, Category.MISC);
        ArrayList<String> options = new ArrayList<String>();
        options.add("AAC");
        options.add("Vanilla");
        amerikan.setmgr.rSetting(new Setting("Phase Mode", this, "AAC", options));
    }

    public void AAC() {
        if (Minecraft.thePlayer.isCollidedHorizontally) {
            Phase.mc.gameSettings.keyBindForward.pressed = false;
            Timer.timerSpeed = 0.2f;
            if (this.time.isDelayComplete(5000.0f)) {
                Timer.timerSpeed = 1.0f;
                double yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
                double speed = 1.0;
                double xm2 = -Math.sin(yaw) * speed;
                double zm2 = Math.cos(yaw) * speed;
                Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + xm2, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + zm2);
            }
        } else {
            Timer.timerSpeed = 1.0f;
        }
    }

    public void Van() {
        if (Minecraft.thePlayer.isCollidedHorizontally) {
            Timer.timerSpeed = 1.0f;
            double yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
            double speed = 1.0;
            double xm2 = -Math.sin(yaw) * speed;
            double zm2 = Math.cos(yaw) * speed;
            Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + xm2, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + zm2);
        }
    }

    @EventTarget
    @Override
    public void onUpdate() {
        if (amerikan.setmgr.getSettingByName("Phase Mode").getValString().equalsIgnoreCase("AAC")) {
            this.setAddon("AAC");
            this.AAC();
        } else if (amerikan.setmgr.getSettingByName("Phase Mode").getValString().equalsIgnoreCase("Vanilla")) {
            this.setAddon("Vanilla");
            this.Van();
        }
    }
}

