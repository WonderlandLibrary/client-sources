/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget;

import java.util.List;
import java.util.Optional;
import net.minecraft.client.AbstractOption;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.IBidiTooltip;
import net.minecraft.client.gui.widget.GameSettingsSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.optifine.config.FloatOptions;
import net.optifine.gui.IOptionControl;

public class OptionSlider
extends GameSettingsSlider
implements IBidiTooltip,
IOptionControl {
    private final SliderPercentageOption option;
    private boolean supportAdjusting;
    private boolean adjusting;

    public OptionSlider(GameSettings gameSettings, int n, int n2, int n3, int n4, SliderPercentageOption sliderPercentageOption) {
        super(gameSettings, n, n2, n3, n4, (double)((float)sliderPercentageOption.normalizeValue(sliderPercentageOption.get(gameSettings))));
        this.option = sliderPercentageOption;
        this.func_230979_b_();
        this.supportAdjusting = FloatOptions.supportAdjusting(this.option);
        this.adjusting = false;
    }

    @Override
    protected void func_230972_a_() {
        if (!this.adjusting) {
            double d = this.option.get(this.settings);
            double d2 = this.option.denormalizeValue(this.sliderValue);
            if (d2 != d) {
                this.option.set(this.settings, this.option.denormalizeValue(this.sliderValue));
                this.settings.saveOptions();
            }
        }
    }

    @Override
    protected void func_230979_b_() {
        if (this.adjusting) {
            double d = this.option.denormalizeValue(this.sliderValue);
            ITextComponent iTextComponent = FloatOptions.getTextComponent(this.option, d);
            if (iTextComponent != null) {
                this.setMessage(iTextComponent);
            }
        } else {
            this.setMessage(this.option.func_238334_c_(this.settings));
        }
    }

    @Override
    public Optional<List<IReorderingProcessor>> func_241867_d() {
        return this.option.getOptionValues();
    }

    @Override
    public void onClick(double d, double d2) {
        if (this.supportAdjusting) {
            this.adjusting = true;
        }
        super.onClick(d, d2);
    }

    @Override
    protected void onDrag(double d, double d2, double d3, double d4) {
        if (this.supportAdjusting) {
            this.adjusting = true;
        }
        super.onDrag(d, d2, d3, d4);
    }

    @Override
    public void onRelease(double d, double d2) {
        if (this.adjusting) {
            this.adjusting = false;
            this.func_230972_a_();
            this.func_230979_b_();
        }
        super.onRelease(d, d2);
    }

    public static int getWidth(Widget widget) {
        return widget.width;
    }

    public static int getHeight(Widget widget) {
        return widget.height;
    }

    @Override
    public AbstractOption getControlOption() {
        return this.option;
    }
}

