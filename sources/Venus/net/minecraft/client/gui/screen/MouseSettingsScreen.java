/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Arrays;
import java.util.stream.Stream;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.TranslationTextComponent;

public class MouseSettingsScreen
extends SettingsScreen {
    private OptionsRowList field_213045_b;
    private static final AbstractOption[] OPTIONS = new AbstractOption[]{AbstractOption.SENSITIVITY, AbstractOption.INVERT_MOUSE, AbstractOption.MOUSE_WHEEL_SENSITIVITY, AbstractOption.DISCRETE_MOUSE_SCROLL, AbstractOption.TOUCHSCREEN};

    public MouseSettingsScreen(Screen screen, GameSettings gameSettings) {
        super(screen, gameSettings, new TranslationTextComponent("options.mouse_settings.title"));
    }

    @Override
    protected void init() {
        this.field_213045_b = new OptionsRowList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        if (InputMappings.func_224790_a()) {
            this.field_213045_b.addOptions((AbstractOption[])Stream.concat(Arrays.stream(OPTIONS), Stream.of(AbstractOption.RAW_MOUSE_INPUT)).toArray(MouseSettingsScreen::lambda$init$0));
        } else {
            this.field_213045_b.addOptions(OPTIONS);
        }
        this.children.add(this.field_213045_b);
        this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, DialogTexts.GUI_DONE, this::lambda$init$1));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.field_213045_b.render(matrixStack, n, n2, f);
        MouseSettingsScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 5, 0xFFFFFF);
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$1(Button button) {
        this.gameSettings.saveOptions();
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    private static AbstractOption[] lambda$init$0(int n) {
        return new AbstractOption[n];
    }
}

