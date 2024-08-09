/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.OptionButton;
import net.minecraft.util.text.ITextComponent;

public class IteratableOption
extends AbstractOption {
    protected BiConsumer<GameSettings, Integer> setter;
    protected BiFunction<GameSettings, IteratableOption, ITextComponent> getter;

    public IteratableOption(String string, BiConsumer<GameSettings, Integer> biConsumer, BiFunction<GameSettings, IteratableOption, ITextComponent> biFunction) {
        super(string);
        this.setter = biConsumer;
        this.getter = biFunction;
    }

    public void setValueIndex(GameSettings gameSettings, int n) {
        this.setter.accept(gameSettings, n);
        gameSettings.saveOptions();
    }

    @Override
    public Widget createWidget(GameSettings gameSettings, int n, int n2, int n3) {
        return new OptionButton(n, n2, n3, 20, this, this.getName(gameSettings), arg_0 -> this.lambda$createWidget$0(gameSettings, arg_0));
    }

    public ITextComponent getName(GameSettings gameSettings) {
        return this.getter.apply(gameSettings, this);
    }

    private void lambda$createWidget$0(GameSettings gameSettings, Button button) {
        this.setValueIndex(gameSettings, 1);
        button.setMessage(this.getName(gameSettings));
    }
}

