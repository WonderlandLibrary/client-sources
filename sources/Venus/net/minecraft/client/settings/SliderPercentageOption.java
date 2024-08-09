/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.OptionSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.optifine.Config;

public class SliderPercentageOption
extends AbstractOption {
    protected final float stepSize;
    protected final double minValue;
    protected double maxValue;
    protected Function<GameSettings, Double> getter;
    protected BiConsumer<GameSettings, Double> setter;
    protected BiFunction<GameSettings, SliderPercentageOption, ITextComponent> getDisplayStringFunc;
    protected double[] stepValues;

    public SliderPercentageOption(String string, double d, double d2, float f, Function<GameSettings, Double> function, BiConsumer<GameSettings, Double> biConsumer, BiFunction<GameSettings, SliderPercentageOption, ITextComponent> biFunction) {
        super(string);
        this.minValue = d;
        this.maxValue = d2;
        this.stepSize = f;
        this.getter = function;
        this.setter = biConsumer;
        this.getDisplayStringFunc = biFunction;
    }

    public SliderPercentageOption(String string, double d, double d2, double[] dArray, Function<GameSettings, Double> function, BiConsumer<GameSettings, Double> biConsumer, BiFunction<GameSettings, SliderPercentageOption, ITextComponent> biFunction) {
        super(string);
        this.minValue = d;
        this.maxValue = d2;
        this.stepSize = 0.0f;
        this.getter = function;
        this.setter = biConsumer;
        this.getDisplayStringFunc = biFunction;
        this.stepValues = dArray;
        if (dArray != null) {
            dArray = (double[])dArray.clone();
            Arrays.sort(dArray);
        }
    }

    @Override
    public Widget createWidget(GameSettings gameSettings, int n, int n2, int n3) {
        return new OptionSlider(gameSettings, n, n2, n3, 20, this);
    }

    public double normalizeValue(double d) {
        return MathHelper.clamp((this.snapToStepClamp(d) - this.minValue) / (this.maxValue - this.minValue), 0.0, 1.0);
    }

    public double denormalizeValue(double d) {
        return this.snapToStepClamp(MathHelper.lerp(MathHelper.clamp(d, 0.0, 1.0), this.minValue, this.maxValue));
    }

    private double snapToStepClamp(double d) {
        if (this.stepSize > 0.0f) {
            d = this.stepSize * (float)Math.round(d / (double)this.stepSize);
        }
        if (this.stepValues != null) {
            for (int i = 0; i < this.stepValues.length; ++i) {
                double d2;
                double d3 = i <= 0 ? -1.7976931348623157E308 : (this.stepValues[i - 1] + this.stepValues[i]) / 2.0;
                double d4 = d2 = i >= this.stepValues.length - 1 ? Double.MAX_VALUE : (this.stepValues[i] + this.stepValues[i + 1]) / 2.0;
                if (!Config.between(d, d3, d2)) continue;
                d = this.stepValues[i];
                break;
            }
        }
        return MathHelper.clamp(d, this.minValue, this.maxValue);
    }

    public double getMinValue() {
        return this.minValue;
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(float f) {
        this.maxValue = f;
    }

    public void set(GameSettings gameSettings, double d) {
        this.setter.accept(gameSettings, d);
    }

    public double get(GameSettings gameSettings) {
        return this.getter.apply(gameSettings);
    }

    public ITextComponent func_238334_c_(GameSettings gameSettings) {
        return this.getDisplayStringFunc.apply(gameSettings, this);
    }
}

