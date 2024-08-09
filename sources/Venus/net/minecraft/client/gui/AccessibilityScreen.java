/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.ConfirmOpenLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WithNarratorSettingsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

public class AccessibilityScreen
extends WithNarratorSettingsScreen {
    private static final AbstractOption[] OPTIONS = new AbstractOption[]{AbstractOption.NARRATOR, AbstractOption.SHOW_SUBTITLES, AbstractOption.ACCESSIBILITY_TEXT_BACKGROUND_OPACITY, AbstractOption.ACCESSIBILITY_TEXT_BACKGROUND, AbstractOption.CHAT_OPACITY, AbstractOption.LINE_SPACING, AbstractOption.DELAY_INSTANT, AbstractOption.AUTO_JUMP, AbstractOption.SNEAK, AbstractOption.SPRINT, AbstractOption.SCREEN_EFFECT_SCALE_SLIDER, AbstractOption.FOV_EFFECT_SCALE_SLIDER};

    public AccessibilityScreen(Screen screen, GameSettings gameSettings) {
        super(screen, gameSettings, new TranslationTextComponent("options.accessibility.title"), OPTIONS);
    }

    @Override
    protected void func_244718_c() {
        this.addButton(new Button(this.width / 2 - 155, this.height - 27, 150, 20, new TranslationTextComponent("options.accessibility.link"), this::lambda$func_244718_c$1));
        this.addButton(new Button(this.width / 2 + 5, this.height - 27, 150, 20, DialogTexts.GUI_DONE, this::lambda$func_244718_c$2));
    }

    private void lambda$func_244718_c$2(Button button) {
        this.minecraft.displayGuiScreen(this.parentScreen);
    }

    private void lambda$func_244718_c$1(Button button) {
        this.minecraft.displayGuiScreen(new ConfirmOpenLinkScreen(this::lambda$func_244718_c$0, "https://aka.ms/MinecraftJavaAccessibility", true));
    }

    private void lambda$func_244718_c$0(boolean bl) {
        if (bl) {
            Util.getOSType().openURI("https://aka.ms/MinecraftJavaAccessibility");
        }
        this.minecraft.displayGuiScreen(this);
    }
}

