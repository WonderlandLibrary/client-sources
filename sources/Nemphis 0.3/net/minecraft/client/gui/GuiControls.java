/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class GuiControls
extends GuiScreen {
    private static final GameSettings.Options[] optionsArr = new GameSettings.Options[]{GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN};
    private GuiScreen parentScreen;
    protected String screenTitle = "Controls";
    private GameSettings options;
    public KeyBinding buttonId = null;
    public long time;
    private GuiKeyBindingList keyBindingList;
    private GuiButton buttonReset;
    private static final String __OBFID = "CL_00000736";

    public GuiControls(GuiScreen p_i1027_1_, GameSettings p_i1027_2_) {
        this.parentScreen = p_i1027_1_;
        this.options = p_i1027_2_;
    }

    @Override
    public void initGui() {
        this.keyBindingList = new GuiKeyBindingList(this, this.mc);
        this.buttonList.add(new GuiButton(200, this.width / 2 - 155, this.height - 29, 150, 20, I18n.format("gui.done", new Object[0])));
        this.buttonReset = new GuiButton(201, this.width / 2 - 155 + 160, this.height - 29, 150, 20, I18n.format("controls.resetAll", new Object[0]));
        this.buttonList.add(this.buttonReset);
        this.screenTitle = I18n.format("controls.title", new Object[0]);
        int var1 = 0;
        GameSettings.Options[] var2 = optionsArr;
        int var3 = var2.length;
        int var4 = 0;
        while (var4 < var3) {
            GameSettings.Options var5 = var2[var4];
            if (var5.getEnumFloat()) {
                this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5));
            } else {
                this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5, this.options.getKeyBinding(var5)));
            }
            ++var1;
            ++var4;
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.keyBindingList.func_178039_p();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 200) {
            this.mc.displayGuiScreen(this.parentScreen);
        } else if (button.id == 201) {
            KeyBinding[] var2 = this.mc.gameSettings.keyBindings;
            int var3 = var2.length;
            int var4 = 0;
            while (var4 < var3) {
                KeyBinding var5 = var2[var4];
                var5.setKeyCode(var5.getKeyCodeDefault());
                ++var4;
            }
            KeyBinding.resetKeyBindingArrayAndHash();
        } else if (button.id < 100 && button instanceof GuiOptionButton) {
            this.options.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
            button.displayString = this.options.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.buttonId != null) {
            this.options.setOptionKeyBinding(this.buttonId, -100 + mouseButton);
            this.buttonId = null;
            KeyBinding.resetKeyBindingArrayAndHash();
        } else if (mouseButton != 0 || !this.keyBindingList.func_148179_a(mouseX, mouseY, mouseButton)) {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state != 0 || !this.keyBindingList.func_148181_b(mouseX, mouseY, state)) {
            super.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.buttonId != null) {
            if (keyCode == 1) {
                this.options.setOptionKeyBinding(this.buttonId, 0);
            } else if (keyCode != 0) {
                this.options.setOptionKeyBinding(this.buttonId, keyCode);
            } else if (typedChar > '\u0000') {
                this.options.setOptionKeyBinding(this.buttonId, typedChar + 256);
            }
            this.buttonId = null;
            this.time = Minecraft.getSystemTime();
            KeyBinding.resetKeyBindingArrayAndHash();
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.keyBindingList.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 8, 16777215);
        boolean var4 = true;
        KeyBinding[] var5 = this.options.keyBindings;
        int var6 = var5.length;
        int var7 = 0;
        while (var7 < var6) {
            KeyBinding var8 = var5[var7];
            if (var8.getKeyCode() != var8.getKeyCodeDefault()) {
                var4 = false;
                break;
            }
            ++var7;
        }
        this.buttonReset.enabled = !var4;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

