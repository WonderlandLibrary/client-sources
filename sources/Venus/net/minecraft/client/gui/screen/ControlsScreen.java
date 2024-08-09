/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.MouseSettingsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.KeyBindingList;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

public class ControlsScreen
extends SettingsScreen {
    public KeyBinding buttonId;
    public long time;
    private KeyBindingList keyBindingList;
    private Button buttonReset;

    public ControlsScreen(Screen screen, GameSettings gameSettings) {
        super(screen, gameSettings, new TranslationTextComponent("controls.title"));
    }

    @Override
    protected void init() {
        this.addButton(new Button(this.width / 2 - 155, 18, 150, 20, new TranslationTextComponent("options.mouse_settings"), this::lambda$init$0));
        this.addButton(AbstractOption.AUTO_JUMP.createWidget(this.gameSettings, this.width / 2 - 155 + 160, 18, 150));
        this.keyBindingList = new KeyBindingList(this, this.minecraft);
        this.children.add(this.keyBindingList);
        this.buttonReset = this.addButton(new Button(this.width / 2 - 155, this.height - 29, 150, 20, new TranslationTextComponent("controls.resetAll"), this::lambda$init$1));
        this.addButton(new Button(this.width / 2 - 155 + 160, this.height - 29, 150, 20, DialogTexts.GUI_DONE, this::lambda$init$2));
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.buttonId != null) {
            this.gameSettings.setKeyBindingCode(this.buttonId, InputMappings.Type.MOUSE.getOrMakeInput(n));
            this.buttonId = null;
            KeyBinding.resetKeyBindingArrayAndHash();
            return false;
        }
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (this.buttonId != null) {
            if (n == 256) {
                this.gameSettings.setKeyBindingCode(this.buttonId, InputMappings.INPUT_INVALID);
            } else {
                this.gameSettings.setKeyBindingCode(this.buttonId, InputMappings.getInputByCode(n, n2));
            }
            this.buttonId = null;
            this.time = Util.milliTime();
            KeyBinding.resetKeyBindingArrayAndHash();
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        this.keyBindingList.render(matrixStack, n, n2, f);
        ControlsScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        boolean bl = false;
        for (KeyBinding keyBinding : this.gameSettings.keyBindings) {
            if (keyBinding.isDefault()) continue;
            bl = true;
            break;
        }
        this.buttonReset.active = bl;
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$init$2(Button button) {
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    private void lambda$init$1(Button button) {
        for (KeyBinding keyBinding : this.gameSettings.keyBindings) {
            keyBinding.bind(keyBinding.getDefault());
        }
        KeyBinding.resetKeyBindingArrayAndHash();
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(new MouseSettingsScreen(this, this.gameSettings));
    }
}

