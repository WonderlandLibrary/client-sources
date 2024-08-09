/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuRecipient;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class SpectatorGui
extends AbstractGui
implements ISpectatorMenuRecipient {
    private static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");
    public static final ResourceLocation SPECTATOR_WIDGETS = new ResourceLocation("textures/gui/spectator_widgets.png");
    private final Minecraft mc;
    private long lastSelectionTime;
    private SpectatorMenu menu;

    public SpectatorGui(Minecraft minecraft) {
        this.mc = minecraft;
    }

    public void onHotbarSelected(int n) {
        this.lastSelectionTime = Util.milliTime();
        if (this.menu != null) {
            this.menu.selectSlot(n);
        } else {
            this.menu = new SpectatorMenu(this);
        }
    }

    private float getHotbarAlpha() {
        long l = this.lastSelectionTime - Util.milliTime() + 5000L;
        return MathHelper.clamp((float)l / 2000.0f, 0.0f, 1.0f);
    }

    public void func_238528_a_(MatrixStack matrixStack, float f) {
        if (this.menu != null) {
            float f2 = this.getHotbarAlpha();
            if (f2 <= 0.0f) {
                this.menu.exit();
            } else {
                int n = this.mc.getMainWindow().getScaledWidth() / 2;
                int n2 = this.getBlitOffset();
                this.setBlitOffset(-90);
                int n3 = MathHelper.floor((float)this.mc.getMainWindow().getScaledHeight() - 22.0f * f2);
                SpectatorDetails spectatorDetails = this.menu.getCurrentPage();
                this.func_238529_a_(matrixStack, f2, n, n3, spectatorDetails);
                this.setBlitOffset(n2);
            }
        }
    }

    protected void func_238529_a_(MatrixStack matrixStack, float f, int n, int n2, SpectatorDetails spectatorDetails) {
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, f);
        this.mc.getTextureManager().bindTexture(WIDGETS);
        this.blit(matrixStack, n - 91, n2, 0, 0, 182, 22);
        if (spectatorDetails.getSelectedSlot() >= 0) {
            this.blit(matrixStack, n - 91 - 1 + spectatorDetails.getSelectedSlot() * 20, n2 - 1, 0, 22, 24, 22);
        }
        for (int i = 0; i < 9; ++i) {
            this.func_238530_a_(matrixStack, i, this.mc.getMainWindow().getScaledWidth() / 2 - 90 + i * 20 + 2, n2 + 3, f, spectatorDetails.getObject(i));
        }
        RenderSystem.disableRescaleNormal();
        RenderSystem.disableBlend();
    }

    private void func_238530_a_(MatrixStack matrixStack, int n, int n2, float f, float f2, ISpectatorMenuObject iSpectatorMenuObject) {
        this.mc.getTextureManager().bindTexture(SPECTATOR_WIDGETS);
        if (iSpectatorMenuObject != SpectatorMenu.EMPTY_SLOT) {
            int n3 = (int)(f2 * 255.0f);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(n2, f, 0.0f);
            float f3 = iSpectatorMenuObject.isEnabled() ? 1.0f : 0.25f;
            RenderSystem.color4f(f3, f3, f3, f2);
            iSpectatorMenuObject.func_230485_a_(matrixStack, f3, n3);
            RenderSystem.popMatrix();
            if (n3 > 3 && iSpectatorMenuObject.isEnabled()) {
                ITextComponent iTextComponent = this.mc.gameSettings.keyBindsHotbar[n].func_238171_j_();
                this.mc.fontRenderer.func_243246_a(matrixStack, iTextComponent, n2 + 19 - 2 - this.mc.fontRenderer.getStringPropertyWidth(iTextComponent), f + 6.0f + 3.0f, 0xFFFFFF + (n3 << 24));
            }
        }
    }

    public void func_238527_a_(MatrixStack matrixStack) {
        int n = (int)(this.getHotbarAlpha() * 255.0f);
        if (n > 3 && this.menu != null) {
            ITextComponent iTextComponent;
            ISpectatorMenuObject iSpectatorMenuObject = this.menu.getSelectedItem();
            ITextComponent iTextComponent2 = iTextComponent = iSpectatorMenuObject == SpectatorMenu.EMPTY_SLOT ? this.menu.getSelectedCategory().getPrompt() : iSpectatorMenuObject.getSpectatorName();
            if (iTextComponent != null) {
                int n2 = (this.mc.getMainWindow().getScaledWidth() - this.mc.fontRenderer.getStringPropertyWidth(iTextComponent)) / 2;
                int n3 = this.mc.getMainWindow().getScaledHeight() - 35;
                RenderSystem.pushMatrix();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                this.mc.fontRenderer.func_243246_a(matrixStack, iTextComponent, n2, n3, 0xFFFFFF + (n << 24));
                RenderSystem.disableBlend();
                RenderSystem.popMatrix();
            }
        }
    }

    @Override
    public void onSpectatorMenuClosed(SpectatorMenu spectatorMenu) {
        this.menu = null;
        this.lastSelectionTime = 0L;
    }

    public boolean isMenuActive() {
        return this.menu != null;
    }

    public void onMouseScroll(double d) {
        int n = this.menu.getSelectedSlot() + (int)d;
        while (!(n < 0 || n > 8 || this.menu.getItem(n) != SpectatorMenu.EMPTY_SLOT && this.menu.getItem(n).isEnabled())) {
            n = (int)((double)n + d);
        }
        if (n >= 0 && n <= 8) {
            this.menu.selectSlot(n);
            this.lastSelectionTime = Util.milliTime();
        }
    }

    public void onMiddleClick() {
        this.lastSelectionTime = Util.milliTime();
        if (this.isMenuActive()) {
            int n = this.menu.getSelectedSlot();
            if (n != -1) {
                this.menu.selectSlot(n);
            }
        } else {
            this.menu = new SpectatorMenu(this);
        }
    }
}

