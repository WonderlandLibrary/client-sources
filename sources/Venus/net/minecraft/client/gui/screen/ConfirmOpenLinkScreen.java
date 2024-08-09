/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ConfirmOpenLinkScreen
extends ConfirmScreen {
    private final ITextComponent openLinkWarning;
    private final ITextComponent copyLinkButtonText;
    private final String linkText;
    private final boolean showSecurityWarning;

    public ConfirmOpenLinkScreen(BooleanConsumer booleanConsumer, String string, boolean bl) {
        super(booleanConsumer, new TranslationTextComponent(bl ? "chat.link.confirmTrusted" : "chat.link.confirm"), new StringTextComponent(string));
        this.confirmButtonText = bl ? new TranslationTextComponent("chat.link.open") : DialogTexts.GUI_YES;
        this.cancelButtonText = bl ? DialogTexts.GUI_CANCEL : DialogTexts.GUI_NO;
        this.copyLinkButtonText = new TranslationTextComponent("chat.copy");
        this.openLinkWarning = new TranslationTextComponent("chat.link.warning");
        this.showSecurityWarning = !bl;
        this.linkText = string;
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        this.children.clear();
        this.addButton(new Button(this.width / 2 - 50 - 105, this.height / 6 + 96, 100, 20, this.confirmButtonText, this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 50, this.height / 6 + 96, 100, 20, this.copyLinkButtonText, this::lambda$init$1));
        this.addButton(new Button(this.width / 2 - 50 + 105, this.height / 6 + 96, 100, 20, this.cancelButtonText, this::lambda$init$2));
    }

    public void copyLinkToClipboard() {
        this.minecraft.keyboardListener.setClipboardString(this.linkText);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        super.render(matrixStack, n, n2, f);
        if (this.showSecurityWarning) {
            ConfirmOpenLinkScreen.drawCenteredString(matrixStack, this.font, this.openLinkWarning, this.width / 2, 110, 0xFFCCCC);
        }
    }

    private void lambda$init$2(Button button) {
        this.callbackFunction.accept(false);
    }

    private void lambda$init$1(Button button) {
        this.copyLinkToClipboard();
        this.callbackFunction.accept(false);
    }

    private void lambda$init$0(Button button) {
        this.callbackFunction.accept(true);
    }
}

