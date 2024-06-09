/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Movement;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import us.amerikan.amerikan;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.modules.ModuleManager;

public class Sprint
extends Module {
    public static boolean dont = false;

    public Sprint() {
        super("Sprint", "Sprint", 0, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (Sprint.isPressed()) {
            if (ModuleManager.getModuleByName("Scaffold").isEnabled() && !amerikan.setmgr.getSettingByName("Fast Mode (NCP)").getValBoolean()) {
                return;
            }
            if (dont) {
                return;
            }
            Minecraft.thePlayer.setSprinting(true);
        }
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
}

