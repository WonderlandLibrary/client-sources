/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class MultiplayerWarningScreen
extends Screen {
    private final Screen field_230156_a_;
    private static final ITextComponent field_230157_b_ = new TranslationTextComponent("multiplayerWarning.header").mergeStyle(TextFormatting.BOLD);
    private static final ITextComponent field_230158_c_ = new TranslationTextComponent("multiplayerWarning.message");
    private static final ITextComponent field_230159_d_ = new TranslationTextComponent("multiplayerWarning.check");
    private static final ITextComponent field_238858_q_ = field_230157_b_.deepCopy().appendString("\n").append(field_230158_c_);
    private CheckboxButton field_230162_g_;
    private IBidiRenderer field_243364_s = IBidiRenderer.field_243257_a;

    public MultiplayerWarningScreen(Screen screen) {
        super(NarratorChatListener.EMPTY);
        this.field_230156_a_ = screen;
    }

    @Override
    protected void init() {
        super.init();
        this.field_243364_s = IBidiRenderer.func_243258_a(this.font, field_230158_c_, this.width - 50);
        int n = (this.field_243364_s.func_241862_a() + 1) * 9 * 2;
        this.addButton(new Button(this.width / 2 - 155, 100 + n, 150, 20, DialogTexts.GUI_PROCEED, this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 155 + 160, 100 + n, 150, 20, DialogTexts.GUI_BACK, this::lambda$init$1));
        this.field_230162_g_ = new CheckboxButton(this.width / 2 - 155 + 80, 76 + n, 150, 20, field_230159_d_, false);
        this.addButton(this.field_230162_g_);
    }

    @Override
    public String getNarrationMessage() {
        return field_238858_q_.getString();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderDirtBackground(0);
        MultiplayerWarningScreen.drawString(matrixStack, this.font, field_230157_b_, 25, 30, 0xFFFFFF);
        this.field_243364_s.func_241865_b(matrixStack, 25, 70, 18, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(this.field_230156_a_);
    }

    private void lambda$init$0(Button button) {
        if (this.field_230162_g_.isChecked()) {
            this.minecraft.gameSettings.skipMultiplayerWarning = true;
            this.minecraft.gameSettings.saveOptions();
        }
        this.minecraft.displayGuiScreen(new MultiplayerScreen(this.field_230156_a_));
    }
}

