/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.GameSettings;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.ITextComponent;

public class SliderMultiplierOption
extends SliderPercentageOption {
    public SliderMultiplierOption(String string, double d, double d2, float f, Function<GameSettings, Double> function, BiConsumer<GameSettings, Double> biConsumer, BiFunction<GameSettings, SliderPercentageOption, ITextComponent> biFunction) {
        super(string, d, d2, f, function, biConsumer, biFunction);
    }

    @Override
    public double normalizeValue(double d) {
        return Math.log(d / this.minValue) / Math.log(this.maxValue / this.minValue);
    }

    @Override
    public double denormalizeValue(double d) {
        return this.minValue * Math.pow(Math.E, Math.log(this.maxValue / this.minValue) * d);
    }
}

