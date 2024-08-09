/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;

public class GPUWarningScreen
extends Screen {
    private final ITextProperties warnings;
    private final ImmutableList<Option> options;
    private IBidiRenderer warningRenderer = IBidiRenderer.field_243257_a;
    private int field_241588_p_;
    private int field_241589_q_;

    protected GPUWarningScreen(ITextComponent iTextComponent, List<ITextProperties> list, ImmutableList<Option> immutableList) {
        super(iTextComponent);
        this.warnings = ITextProperties.func_240654_a_(list);
        this.options = immutableList;
    }

    @Override
    public String getNarrationMessage() {
        return super.getNarrationMessage() + ". " + this.warnings.getString();
    }

    @Override
    public void init(Minecraft minecraft, int n, int n2) {
        super.init(minecraft, n, n2);
        for (Option option : this.options) {
            this.field_241589_q_ = Math.max(this.field_241589_q_, 20 + this.font.getStringPropertyWidth(option.field_241590_a_) + 20);
        }
        int n3 = 5 + this.field_241589_q_ + 5;
        int n4 = n3 * this.options.size();
        this.warningRenderer = IBidiRenderer.func_243258_a(this.font, this.warnings, n4);
        int n5 = this.warningRenderer.func_241862_a() * 9;
        this.field_241588_p_ = (int)((double)n2 / 2.0 - (double)n5 / 2.0);
        int n6 = this.field_241588_p_ + n5 + 18;
        int n7 = (int)((double)n / 2.0 - (double)n4 / 2.0);
        for (Option option : this.options) {
            this.addButton(new Button(n7, n6, this.field_241589_q_, 20, option.field_241590_a_, option.field_241591_b_));
            n7 += n3;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderDirtBackground(0);
        GPUWarningScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, this.field_241588_p_ - 18, -1);
        this.warningRenderer.func_241863_a(matrixStack, this.width / 2, this.field_241588_p_);
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    public static final class Option {
        private final ITextComponent field_241590_a_;
        private final Button.IPressable field_241591_b_;

        public Option(ITextComponent iTextComponent, Button.IPressable iPressable) {
            this.field_241590_a_ = iTextComponent;
            this.field_241591_b_ = iPressable;
        }
    }
}

