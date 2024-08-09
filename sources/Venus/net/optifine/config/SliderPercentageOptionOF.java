/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.GameSettings;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.ITextComponent;

public class SliderPercentageOptionOF
extends SliderPercentageOption {
    public SliderPercentageOptionOF(String string) {
        this(string, 0.0, 1.0, 0.0f);
    }

    public SliderPercentageOptionOF(String string, double d, double d2, float f) {
        super(string, d, d2, f, (Function<GameSettings, Double>)null, (BiConsumer<GameSettings, Double>)null, (BiFunction<GameSettings, SliderPercentageOption, ITextComponent>)null);
        this.getter = this::getOptionValue;
        this.setter = this::setOptionValue;
        this.getDisplayStringFunc = this::getOptionText;
    }

    public SliderPercentageOptionOF(String string, double d, double d2, double[] dArray) {
        super(string, d, d2, dArray, (Function<GameSettings, Double>)null, (BiConsumer<GameSettings, Double>)null, (BiFunction<GameSettings, SliderPercentageOption, ITextComponent>)null);
        this.getter = this::getOptionValue;
        this.setter = this::setOptionValue;
        this.getDisplayStringFunc = this::getOptionText;
    }

    private double getOptionValue(GameSettings gameSettings) {
        return gameSettings.getOptionFloatValueOF(this);
    }

    private void setOptionValue(GameSettings gameSettings, double d) {
        gameSettings.setOptionFloatValueOF(this, d);
    }

    private ITextComponent getOptionText(GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        return gameSettings.getKeyComponentOF(sliderPercentageOption);
    }
}

