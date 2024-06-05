/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.menu;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import digital.rbq.utils.Stopwatch;
import digital.rbq.utils.render.AnimationUtils;
import digital.rbq.utils.render.RenderUtils;

public final class LicensingMenu
extends GuiScreen {
    private float p = -100.0f;
    private final Stopwatch stopwatch = new Stopwatch();
    private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.stopwatch.elapsed(10L)) {
            this.p = (float)AnimationUtils.animate(this.height, this.p, 0.25);
            this.stopwatch.reset();
        }
        LicensingMenu.drawRect(0.0, 0.0, this.width, this.height, -1);
        RenderUtils.drawImg(locationMojangPng, (double)this.width / 5.0, 0.0, (double)this.width - (double)this.width / 2.5, this.height);
        LicensingMenu.drawRect(0.0, 0.0, this.width, this.p, -13878632);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

