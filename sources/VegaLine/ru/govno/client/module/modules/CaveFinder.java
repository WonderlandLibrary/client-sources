/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import ru.govno.client.module.Module;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class CaveFinder
extends Module {
    public static CaveFinder get;
    public static boolean findEnabled;
    AnimationUtils darkerAlphaPC = new AnimationUtils(0.0f, 0.0f, 0.075f);

    public CaveFinder() {
        super("CaveFinder", 0, Module.Category.RENDER);
        get = this;
    }

    @Override
    public void onToggled(boolean actived) {
        this.darkerAlphaPC.to = 1.0f;
        super.onToggled(actived);
    }

    public void post2DDark(ScaledResolution sr) {
        float alphaPC = this.darkerAlphaPC.getAnim();
        if (alphaPC != 1.0f && (double)alphaPC > 0.95) {
            this.darkerAlphaPC.setAnim(1.0f);
            this.darkerAlphaPC.to = 0.0f;
            findEnabled = this.isActived();
            CaveFinder.mc.renderGlobal.loadRenderers();
        }
        if (alphaPC == 0.0f) {
            this.darkerAlphaPC.to = 0.0f;
        }
        if (this.darkerAlphaPC.to == 0.0f && alphaPC != 0.0f && (double)alphaPC < 0.05) {
            this.darkerAlphaPC.setAnim(0.0f);
        }
        if (alphaPC == 0.0f) {
            return;
        }
        GL11.glDisable(2929);
        RenderUtils.drawAlphedRect(0.0, 0.0, sr.getScaledWidth(), sr.getScaledHeight(), ColorUtils.getColor(10, 10, 10, 255.0f * alphaPC));
        GL11.glEnable(2929);
    }
}

