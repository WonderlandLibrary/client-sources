/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Misc;

import com.darkmagician6.eventapi.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class AutoWalk
extends Module {
    public AutoWalk() {
        super("AutoWalk", "AutoWalk", 0, Category.MISC);
    }

    @Override
    public void onUpdate() {
        if (this.isEnabled()) {
            AutoWalk.mc.gameSettings.keyBindForward.pressed = true;
        }
    }

    @Override
    public void onDisabled() {
        EventManager.register(this);
        AutoWalk.mc.gameSettings.keyBindForward.pressed = false;
    }
}

