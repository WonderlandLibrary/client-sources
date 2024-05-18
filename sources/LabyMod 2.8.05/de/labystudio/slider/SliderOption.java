package de.labystudio.slider;

import net.minecraft.util.MathHelper;

public class SliderOption
{
    private final boolean enumFloat;
    private final boolean enumBoolean;
    private final float valueStep;
    private float valueMin;
    private float valueMax;
    private SliderCallback callback;

    public SliderOption(boolean isFloat, boolean isBoolean, SliderCallback callback)
    {
        this(isFloat, isBoolean, 0.0F, 1.0F, 0.0F, callback);
    }

    public SliderOption(float min, float max, SliderCallback callback)
    {
        this(true, false, min, max, 0.0F, callback);
    }

    public SliderOption(boolean isFloat, boolean isBoolean, float valMin, float valMax, float valStep, SliderCallback callback)
    {
        this.enumFloat = isFloat;
        this.enumBoolean = isBoolean;
        this.valueMin = valMin;
        this.valueMax = valMax;
        this.valueStep = valStep;
        this.callback = callback;
    }

    public boolean getEnumFloat()
    {
        return this.enumFloat;
    }

    public boolean getEnumBoolean()
    {
        return this.enumBoolean;
    }

    public float getValueMin()
    {
        return this.valueMin;
    }

    public float getValueMax()
    {
        return this.valueMax;
    }

    public SliderCallback getCallback()
    {
        return this.callback;
    }

    public void setValueMax(float value)
    {
        this.valueMax = value;
    }

    public float normalizeValue(float value)
    {
        return MathHelper.clamp_float((this.snapToStepClamp(value) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
    }

    public float denormalizeValue(float value)
    {
        return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(value, 0.0F, 1.0F));
    }

    public float snapToStepClamp(float value)
    {
        value = this.snapToStep(value);
        return MathHelper.clamp_float(value, this.valueMin, this.valueMax);
    }

    protected float snapToStep(float value)
    {
        if (this.valueStep > 0.0F)
        {
            value = this.valueStep * (float)Math.round(value / this.valueStep);
        }

        return value;
    }
}
