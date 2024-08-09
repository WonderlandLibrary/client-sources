/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ConfirmBackupScreen
extends Screen {
    @Nullable
    private final Screen parentScreen;
    protected final ICallback callback;
    private final ITextComponent message;
    private final boolean field_212994_d;
    private IBidiRenderer field_243275_q = IBidiRenderer.field_243257_a;
    private CheckboxButton field_212996_j;

    public ConfirmBackupScreen(@Nullable Screen screen, ICallback iCallback, ITextComponent iTextComponent, ITextComponent iTextComponent2, boolean bl) {
        super(iTextComponent);
        this.parentScreen = screen;
        this.callback = iCallback;
        this.message = iTextComponent2;
        this.field_212994_d = bl;
    }

    @Override
    protected void init() {
        super.init();
        this.field_243275_q = IBidiRenderer.func_243258_a(this.font, this.message, this.width - 50);
        int n = (this.field_243275_q.func_241862_a() + 1) * 9;
        this.addButton(new Button(this.width / 2 - 155, 100 + n, 150, 20, new TranslationTextComponent("selectWorld.backupJoinConfirmButton"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 155 + 160, 100 + n, 150, 20, new TranslationTextComponent("selectWorld.backupJoinSkipButton"), this::lambda$init$1));
        this.addButton(new Button(this.width / 2 - 155 + 80, 124 + n, 150, 20, DialogTexts.GUI_CANCEL, this::lambda$init$2));
        this.field_212996_j = new CheckboxButton(this.width / 2 - 155 + 80, 76 + n, 150, 20, new TranslationTextComponent("selectWorld.backupEraseCache"), false);
        if (this.field_212994_d) {
            this.addButton(this.field_212996_j);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        ConfirmBackupScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 50, 0xFFFFFF);
        this.field_243275_q.func_241863_a(matrixStack, this.width / 2, 70);
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.parentScreen);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private void lambda$init$2(Button button) {
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    private void lambda$init$1(Button button) {
        this.callback.proceed(false, this.field_212996_j.isChecked());
    }

    private void lambda$init$0(Button button) {
        this.callback.proceed(true, this.field_212996_j.isChecked());
    }

    public static interface ICallback {
        public void proceed(boolean var1, boolean var2);
    }
}

