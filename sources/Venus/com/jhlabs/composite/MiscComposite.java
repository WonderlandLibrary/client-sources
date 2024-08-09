/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.composite;

import com.jhlabs.composite.AddComposite;
import com.jhlabs.composite.AverageComposite;
import com.jhlabs.composite.BurnComposite;
import com.jhlabs.composite.ColorBurnComposite;
import com.jhlabs.composite.ColorComposite;
import com.jhlabs.composite.ColorDodgeComposite;
import com.jhlabs.composite.DarkenComposite;
import com.jhlabs.composite.DifferenceComposite;
import com.jhlabs.composite.DodgeComposite;
import com.jhlabs.composite.ExclusionComposite;
import com.jhlabs.composite.HardLightComposite;
import com.jhlabs.composite.HueComposite;
import com.jhlabs.composite.LightenComposite;
import com.jhlabs.composite.MiscCompositeContext;
import com.jhlabs.composite.MultiplyComposite;
import com.jhlabs.composite.NegationComposite;
import com.jhlabs.composite.OverlayComposite;
import com.jhlabs.composite.PinLightComposite;
import com.jhlabs.composite.SaturationComposite;
import com.jhlabs.composite.ScreenComposite;
import com.jhlabs.composite.SoftLightComposite;
import com.jhlabs.composite.SubtractComposite;
import com.jhlabs.composite.ValueComposite;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;

public final class MiscComposite
implements Composite {
    public static final int BLEND = 0;
    public static final int ADD = 1;
    public static final int SUBTRACT = 2;
    public static final int DIFFERENCE = 3;
    public static final int MULTIPLY = 4;
    public static final int DARKEN = 5;
    public static final int BURN = 6;
    public static final int COLOR_BURN = 7;
    public static final int SCREEN = 8;
    public static final int LIGHTEN = 9;
    public static final int DODGE = 10;
    public static final int COLOR_DODGE = 11;
    public static final int HUE = 12;
    public static final int SATURATION = 13;
    public static final int VALUE = 14;
    public static final int COLOR = 15;
    public static final int OVERLAY = 16;
    public static final int SOFT_LIGHT = 17;
    public static final int HARD_LIGHT = 18;
    public static final int PIN_LIGHT = 19;
    public static final int EXCLUSION = 20;
    public static final int NEGATION = 21;
    public static final int AVERAGE = 22;
    public static final int STENCIL = 23;
    public static final int SILHOUETTE = 24;
    private static final int MIN_RULE = 0;
    private static final int MAX_RULE = 24;
    public static String[] RULE_NAMES = new String[]{"Normal", "Add", "Subtract", "Difference", "Multiply", "Darken", "Burn", "Color Burn", "Screen", "Lighten", "Dodge", "Color Dodge", "Hue", "Saturation", "Brightness", "Color", "Overlay", "Soft Light", "Hard Light", "Pin Light", "Exclusion", "Negation", "Average", "Stencil", "Silhouette"};
    protected float extraAlpha;
    protected int rule;

    private MiscComposite(int n) {
        this(n, 1.0f);
    }

    private MiscComposite(int n, float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("alpha value out of range");
        }
        if (n < 0 || n > 24) {
            throw new IllegalArgumentException("unknown composite rule");
        }
        this.rule = n;
        this.extraAlpha = f;
    }

    public static Composite getInstance(int n, float f) {
        switch (n) {
            case 0: {
                return AlphaComposite.getInstance(3, f);
            }
            case 1: {
                return new AddComposite(f);
            }
            case 2: {
                return new SubtractComposite(f);
            }
            case 3: {
                return new DifferenceComposite(f);
            }
            case 4: {
                return new MultiplyComposite(f);
            }
            case 5: {
                return new DarkenComposite(f);
            }
            case 6: {
                return new BurnComposite(f);
            }
            case 7: {
                return new ColorBurnComposite(f);
            }
            case 8: {
                return new ScreenComposite(f);
            }
            case 9: {
                return new LightenComposite(f);
            }
            case 10: {
                return new DodgeComposite(f);
            }
            case 11: {
                return new ColorDodgeComposite(f);
            }
            case 12: {
                return new HueComposite(f);
            }
            case 13: {
                return new SaturationComposite(f);
            }
            case 14: {
                return new ValueComposite(f);
            }
            case 15: {
                return new ColorComposite(f);
            }
            case 16: {
                return new OverlayComposite(f);
            }
            case 17: {
                return new SoftLightComposite(f);
            }
            case 18: {
                return new HardLightComposite(f);
            }
            case 19: {
                return new PinLightComposite(f);
            }
            case 20: {
                return new ExclusionComposite(f);
            }
            case 21: {
                return new NegationComposite(f);
            }
            case 22: {
                return new AverageComposite(f);
            }
            case 23: {
                return AlphaComposite.getInstance(6, f);
            }
            case 24: {
                return AlphaComposite.getInstance(8, f);
            }
        }
        return new MiscComposite(n, f);
    }

    @Override
    public CompositeContext createContext(ColorModel colorModel, ColorModel colorModel2, RenderingHints renderingHints) {
        return new MiscCompositeContext(this.rule, this.extraAlpha, colorModel, colorModel2);
    }

    public float getAlpha() {
        return this.extraAlpha;
    }

    public int getRule() {
        return this.rule;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.extraAlpha) * 31 + this.rule;
    }

    public boolean equals(Object object) {
        if (!(object instanceof MiscComposite)) {
            return true;
        }
        MiscComposite miscComposite = (MiscComposite)object;
        if (this.rule != miscComposite.rule) {
            return true;
        }
        return this.extraAlpha != miscComposite.extraAlpha;
    }
}

