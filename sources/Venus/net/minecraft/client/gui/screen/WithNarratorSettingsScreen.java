/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;

public abstract class WithNarratorSettingsScreen
extends SettingsScreen {
    private final AbstractOption[] field_243313_c;
    @Nullable
    private Widget field_243314_p;
    private OptionsRowList field_243315_q;

    public WithNarratorSettingsScreen(Screen screen, GameSettings gameSettings, ITextComponent iTextComponent, AbstractOption[] abstractOptionArray) {
        super(screen, gameSettings, iTextComponent);
        this.field_243313_c = abstractOptionArray;
    }

    @Override
    protected void init() {
        this.field_243315_q = new OptionsRowList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        this.field_243315_q.addOptions(this.field_243313_c);
        this.children.add(this.field_243315_q);
        this.func_244718_c();
        this.field_243314_p = this.field_243315_q.func_243271_b(AbstractOption.NARRATOR);
        if (this.field_243314_p != null) {
            this.field_243314_p.active = NarratorChatListener.INSTANCE.isActive();
        }
    }

    protected void func_244718_c() {
        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, DialogTexts.GUI_DONE, this::lambda$func_244718_c$0));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.field_243315_q.render(matrixStack, n, n2, f);
        WithNarratorSettingsScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
        List<IReorderingProcessor> list = WithNarratorSettingsScreen.func_243293_a(this.field_243315_q, n, n2);
        if (list != null) {
            this.renderTooltip(matrixStack, list, n, n2);
        }
    }

    public void func_243317_i() {
        if (this.field_243314_p != null) {
            this.field_243314_p.setMessage(AbstractOption.NARRATOR.getName(this.gameSettings));
        }
    }

    private void lambda$func_244718_c$0(Button button) {
        this.minecraft.displayGuiScreen(this.parentScreen);
    }
}

