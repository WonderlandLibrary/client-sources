/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.toasts;

import javax.annotation.Nullable;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class TutorialToast
implements IToast {
    private final Icons field_193671_c;
    private final String field_193672_d;
    private final String field_193673_e;
    private IToast.Visibility field_193674_f = IToast.Visibility.SHOW;
    private long field_193675_g;
    private float field_193676_h;
    private float field_193677_i;
    private final boolean field_193678_j;

    public TutorialToast(Icons p_i47487_1_, ITextComponent p_i47487_2_, @Nullable ITextComponent p_i47487_3_, boolean p_i47487_4_) {
        this.field_193671_c = p_i47487_1_;
        this.field_193672_d = p_i47487_2_.getFormattedText();
        this.field_193673_e = p_i47487_3_ == null ? null : p_i47487_3_.getFormattedText();
        this.field_193678_j = p_i47487_4_;
    }

    @Override
    public IToast.Visibility func_193653_a(GuiToast p_193653_1_, long p_193653_2_) {
        p_193653_1_.func_192989_b().getTextureManager().bindTexture(field_193654_a);
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        p_193653_1_.drawTexturedModalRect(0, 0, 0, 96, 160, 32);
        this.field_193671_c.func_193697_a(p_193653_1_, 6, 6);
        if (this.field_193673_e == null) {
            p_193653_1_.func_192989_b().fontRendererObj.drawString(this.field_193672_d, 30.0f, 12.0, -11534256);
        } else {
            p_193653_1_.func_192989_b().fontRendererObj.drawString(this.field_193672_d, 30.0f, 7.0, -11534256);
            p_193653_1_.func_192989_b().fontRendererObj.drawString(this.field_193673_e, 30.0f, 18.0, -16777216);
        }
        if (this.field_193678_j) {
            Gui.drawRect(3, 28.0, 157.0, 29.0, -1);
            float f = (float)MathHelper.clampedLerp(this.field_193676_h, this.field_193677_i, (float)(p_193653_2_ - this.field_193675_g) / 100.0f);
            int i = this.field_193677_i >= this.field_193676_h ? -16755456 : -11206656;
            Gui.drawRect(3, 28.0, (double)((int)(3.0f + 154.0f * f)), 29.0, i);
            this.field_193676_h = f;
            this.field_193675_g = p_193653_2_;
        }
        return this.field_193674_f;
    }

    public void func_193670_a() {
        this.field_193674_f = IToast.Visibility.HIDE;
    }

    public void func_193669_a(float p_193669_1_) {
        this.field_193677_i = p_193669_1_;
    }

    public static enum Icons {
        MOVEMENT_KEYS(0, 0),
        MOUSE(1, 0),
        TREE(2, 0),
        RECIPE_BOOK(0, 1),
        WOODEN_PLANKS(1, 1);

        private final int field_193703_f;
        private final int field_193704_g;

        private Icons(int p_i47576_3_, int p_i47576_4_) {
            this.field_193703_f = p_i47576_3_;
            this.field_193704_g = p_i47576_4_;
        }

        public void func_193697_a(Gui p_193697_1_, int p_193697_2_, int p_193697_3_) {
            GlStateManager.enableBlend();
            p_193697_1_.drawTexturedModalRect(p_193697_2_, p_193697_3_, 176 + this.field_193703_f * 20, this.field_193704_g * 20, 20, 20);
            GlStateManager.enableBlend();
        }
    }
}

