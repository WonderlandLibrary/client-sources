/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.text.ITextComponent;

public class BooleanOption
extends AbstractOption {
    private final Predicate<GameSettings> getter;
    private final BiConsumer<GameSettings, Boolean> setter;
    @Nullable
    private final ITextComponent field_244785_aa;

    public BooleanOption(String string, Predicate<GameSettings> predicate, BiConsumer<GameSettings, Boolean> biConsumer) {
        this(string, null, predicate, biConsumer);
    }

    public BooleanOption(String string, @Nullable ITextComponent iTextComponent, Predicate<GameSettings> predicate, BiConsumer<GameSettings, Boolean> biConsumer) {
        super(string);
        this.getter = predicate;
        this.setter = biConsumer;
        this.field_244785_aa = iTextComponent;
    }

    public void set(GameSettings gameSettings, String string) {
        this.set(gameSettings, "true".equals(string));
    }

    public void nextValue(GameSettings gameSettings) {
        this.set(gameSettings, !this.get(gameSettings));
        gameSettings.saveOptions();
    }

    private void set(GameSettings gameSettings, boolean bl) {
        this.setter.accept(gameSettings, bl);
    }

    public boolean get(GameSettings gameSettings) {
        return this.getter.test(gameSettings);
    }

    @Override
    public Widget createWidget(GameSettings gameSettings, int n, int n2, int n3) {
        if (this.field_244785_aa != null) {
            this.setOptionValues(Minecraft.getInstance().fontRenderer.trimStringToWidth(this.field_244785_aa, 200));
        }
        return new OptionButton(n, n2, n3, 20, this, this.func_238152_c_(gameSettings), arg_0 -> this.lambda$createWidget$0(gameSettings, arg_0));
    }

    public ITextComponent func_238152_c_(GameSettings gameSettings) {
        return DialogTexts.getComposedOptionMessage(this.getBaseMessageTranslation(), this.get(gameSettings));
    }

    private void lambda$createWidget$0(GameSettings gameSettings, Button button) {
        this.nextValue(gameSettings);
        button.setMessage(this.func_238152_c_(gameSettings));
    }
}

