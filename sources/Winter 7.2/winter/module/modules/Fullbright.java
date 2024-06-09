/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.settings.GameSettings;
import winter.module.Module;

public class Fullbright
extends Module {
    float oldGammaSetting;

    public Fullbright() {
        super("Fullbright", Module.Category.Render, -197171);
        this.setBind(50);
    }

    @Override
    public void onEnable() {
        this.oldGammaSetting = this.mc.gameSettings.gammaSetting;
        this.mc.gameSettings.gammaSetting = 1000.0f;
        this.mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        this.mc.gameSettings.gammaSetting = this.oldGammaSetting;
        this.mc.renderGlobal.loadRenderers();
    }
}

