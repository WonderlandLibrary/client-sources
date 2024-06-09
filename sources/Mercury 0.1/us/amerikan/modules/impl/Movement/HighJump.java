/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Movement;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Timer;
import us.amerikan.amerikan;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.CCSelfDamage;

public class HighJump
extends Module {
    CCSelfDamage self = new CCSelfDamage();

    public HighJump() {
        super("HighJump", "HighJump", 0, Category.MOVEMENT);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Cubecraft");
        options.add("Vanilla");
        amerikan.setmgr.rSetting(new Setting("Highjump Mode", this, "Cubecraft", options));
    }

    public void CC() {
        if (Minecraft.thePlayer.onGround) {
            this.self.CCDamage(0.5);
        }
        if (Minecraft.thePlayer.hurtTime > 0) {
            Timer.timerSpeed = 0.3f;
            Minecraft.thePlayer.motionY = 3.700000047683716;
        } else if (Minecraft.thePlayer.ticksExisted % 20 != 0) {
            Timer.timerSpeed = 1.0f;
        }
    }

    public void Van() {
        if (Minecraft.thePlayer.onGround) {
            Minecraft.thePlayer.motionY = 4.0;
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (amerikan.setmgr.getSettingByName("Highjump Mode").getValString().equalsIgnoreCase("Cubecraft")) {
            this.setAddon("Cubecraft");
            this.CC();
        } else if (amerikan.setmgr.getSettingByName("Highjump Mode").getValString().equalsIgnoreCase("Vanilla")) {
            this.setAddon("Vanilla");
            this.Van();
        }
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        Timer.timerSpeed = 1.0f;
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        Timer.timerSpeed = 1.0f;
    }
}

