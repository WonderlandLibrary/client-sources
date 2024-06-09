/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import Monix.Category.Category;
import Monix.Event.EventTarget;
import Monix.Event.events.EventUpdate;
import Monix.Mod.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class FullBright
extends Mod {
    public FullBright() {
        super("FullBright", "FullBright", 48, Category.RENDER);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        FullBright.mc.gameSettings.gammaSetting = 100.0f;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        FullBright.mc.gameSettings.gammaSetting = 0.0f;
    }
}

