/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.toasts;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class TutorialToast
implements IToast {
    private final Icons icon;
    private final ITextComponent title;
    private final ITextComponent subtitle;
    private IToast.Visibility visibility = IToast.Visibility.SHOW;
    private long lastDelta;
    private float displayedProgress;
    private float currentProgress;
    private final boolean hasProgressBar;

    public TutorialToast(Icons icons, ITextComponent iTextComponent, @Nullable ITextComponent iTextComponent2, boolean bl) {
        this.icon = icons;
        this.title = iTextComponent;
        this.subtitle = iTextComponent2;
        this.hasProgressBar = bl;
    }

    @Override
    public IToast.Visibility func_230444_a_(MatrixStack matrixStack, ToastGui toastGui, long l) {
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        RenderSystem.color3f(1.0f, 1.0f, 1.0f);
        toastGui.blit(matrixStack, 0, 0, 0, 96, this.func_230445_a_(), this.func_238540_d_());
        this.icon.func_238543_a_(matrixStack, toastGui, 6, 6);
        if (this.subtitle == null) {
            toastGui.getMinecraft().fontRenderer.func_243248_b(matrixStack, this.title, 30.0f, 12.0f, -11534256);
        } else {
            toastGui.getMinecraft().fontRenderer.func_243248_b(matrixStack, this.title, 30.0f, 7.0f, -11534256);
            toastGui.getMinecraft().fontRenderer.func_243248_b(matrixStack, this.subtitle, 30.0f, 18.0f, -16777216);
        }
        if (this.hasProgressBar) {
            AbstractGui.fill(matrixStack, 3, 28, 157, 29, -1);
            float f = (float)MathHelper.clampedLerp(this.displayedProgress, this.currentProgress, (float)(l - this.lastDelta) / 100.0f);
            int n = this.currentProgress >= this.displayedProgress ? -16755456 : -11206656;
            AbstractGui.fill(matrixStack, 3, 28, (int)(3.0f + 154.0f * f), 29, n);
            this.displayedProgress = f;
            this.lastDelta = l;
        }
        return this.visibility;
    }

    public void hide() {
        this.visibility = IToast.Visibility.HIDE;
    }

    public void setProgress(float f) {
        this.currentProgress = f;
    }

    public static enum Icons {
        MOVEMENT_KEYS(0, 0),
        MOUSE(1, 0),
        TREE(2, 0),
        RECIPE_BOOK(0, 1),
        WOODEN_PLANKS(1, 1),
        SOCIAL_INTERACTIONS(2, 1);

        private final int column;
        private final int row;

        private Icons(int n2, int n3) {
            this.column = n2;
            this.row = n3;
        }

        public void func_238543_a_(MatrixStack matrixStack, AbstractGui abstractGui, int n, int n2) {
            RenderSystem.enableBlend();
            abstractGui.blit(matrixStack, n, n2, 176 + this.column * 20, this.row * 20, 20, 20);
            RenderSystem.enableBlend();
        }
    }
}

