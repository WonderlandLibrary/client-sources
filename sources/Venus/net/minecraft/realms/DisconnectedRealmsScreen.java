/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;

public class DisconnectedRealmsScreen
extends RealmsScreen {
    private final ITextComponent field_230713_a_;
    private final ITextComponent field_230714_b_;
    private IBidiRenderer field_243509_c = IBidiRenderer.field_243257_a;
    private final Screen field_230716_p_;
    private int field_230717_q_;

    public DisconnectedRealmsScreen(Screen screen, ITextComponent iTextComponent, ITextComponent iTextComponent2) {
        this.field_230716_p_ = screen;
        this.field_230713_a_ = iTextComponent;
        this.field_230714_b_ = iTextComponent2;
    }

    @Override
    public void init() {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.setConnectedToRealms(true);
        minecraft.getPackFinder().clearResourcePack();
        RealmsNarratorHelper.func_239550_a_(this.field_230713_a_.getString() + ": " + this.field_230714_b_.getString());
        this.field_243509_c = IBidiRenderer.func_243258_a(this.font, this.field_230714_b_, this.width - 50);
        this.field_230717_q_ = this.field_243509_c.func_241862_a() * 9;
        this.addButton(new Button(this.width / 2 - 100, this.height / 2 + this.field_230717_q_ / 2 + 9, 200, 20, DialogTexts.GUI_BACK, arg_0 -> this.lambda$init$0(minecraft, arg_0)));
    }

    @Override
    public void closeScreen() {
        Minecraft.getInstance().displayGuiScreen(this.field_230716_p_);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        DisconnectedRealmsScreen.drawCenteredString(matrixStack, this.font, this.field_230713_a_, this.width / 2, this.height / 2 - this.field_230717_q_ / 2 - 18, 0xAAAAAA);
        this.field_243509_c.func_241863_a(matrixStack, this.width / 2, this.height / 2 - this.field_230717_q_ / 2);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$0(Minecraft minecraft, Button button) {
        minecraft.displayGuiScreen(this.field_230716_p_);
    }
}

