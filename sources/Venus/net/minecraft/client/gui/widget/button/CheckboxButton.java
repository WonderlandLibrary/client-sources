/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class CheckboxButton
extends AbstractButton {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
    private boolean checked;
    private final boolean field_238499_c_;

    public CheckboxButton(int n, int n2, int n3, int n4, ITextComponent iTextComponent, boolean bl) {
        this(n, n2, n3, n4, iTextComponent, bl, true);
    }

    public CheckboxButton(int n, int n2, int n3, int n4, ITextComponent iTextComponent, boolean bl, boolean bl2) {
        super(n, n2, n3, n4, iTextComponent);
        this.checked = bl;
        this.field_238499_c_ = bl2;
    }

    @Override
    public void onPress() {
        this.checked = !this.checked;
    }

    public boolean isChecked() {
        return this.checked;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(TEXTURE);
        RenderSystem.enableDepthTest();
        FontRenderer fontRenderer = minecraft.fontRenderer;
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        CheckboxButton.blit(matrixStack, this.x, this.y, this.isFocused() ? 20.0f : 0.0f, this.checked ? 20.0f : 0.0f, 20, this.height, 64, 64);
        this.renderBg(matrixStack, minecraft, n, n2);
        if (this.field_238499_c_) {
            CheckboxButton.drawString(matrixStack, fontRenderer, this.getMessage(), this.x + 24, this.y + (this.height - 8) / 2, 0xE0E0E0 | MathHelper.ceil(this.alpha * 255.0f) << 24);
        }
    }
}

