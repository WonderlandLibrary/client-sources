/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Render;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class Fullbright
extends Module {
    private float gammaBefore;

    public Fullbright() {
        super("Fullbright", "Fullbright", 0, Category.RENDER);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (Fullbright.mc.gameSettings.gammaSetting != 100.0f) {
            Fullbright.mc.gameSettings.gammaSetting = Fullbright.mc.gameSettings.gammaSetting > 100.0f ? (Fullbright.mc.gameSettings.gammaSetting -= 10.0f) : (Fullbright.mc.gameSettings.gammaSetting += 10.0f);
        }
    }

    @Override
    public void onEnable() {
        this.gammaBefore = Fullbright.mc.gameSettings.gammaSetting + 0.0f;
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        Fullbright.mc.gameSettings.gammaSetting = this.gammaBefore + 0.0f;
        EventManager.unregister(this);
    }
}

