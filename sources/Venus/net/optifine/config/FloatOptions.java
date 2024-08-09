/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.config;

import net.minecraft.client.AbstractOption;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.optifine.Config;
import net.optifine.Lang;

public class FloatOptions {
    public static ITextComponent getTextComponent(AbstractOption abstractOption, double d) {
        if (abstractOption == AbstractOption.RENDER_DISTANCE) {
            return abstractOption.getGenericValueComponent(new TranslationTextComponent("options.chunks", (int)d));
        }
        if (abstractOption == AbstractOption.MIPMAP_LEVELS) {
            if (d >= 4.0) {
                return abstractOption.getGenericValueComponent(new TranslationTextComponent("of.general.max"));
            }
            return d == 0.0 ? DialogTexts.getComposedOptionMessage(abstractOption.getBaseMessageTranslation(), false) : abstractOption.getMessageWithValue((int)d);
        }
        if (abstractOption == AbstractOption.BIOME_BLEND_RADIUS) {
            int n = (int)d * 2 + 1;
            return abstractOption.getGenericValueComponent(new TranslationTextComponent("options.biomeBlendRadius." + n));
        }
        String string = FloatOptions.getText(abstractOption, d);
        return string != null ? new StringTextComponent(string) : null;
    }

    public static String getText(AbstractOption abstractOption, double d) {
        String string = I18n.format(abstractOption.getResourceKey(), new Object[0]) + ": ";
        if (abstractOption == AbstractOption.AO_LEVEL) {
            return d == 0.0 ? string + I18n.format("options.off", new Object[0]) : string + (int)(d * 100.0) + "%";
        }
        if (abstractOption == AbstractOption.MIPMAP_TYPE) {
            int n = (int)d;
            switch (n) {
                case 0: {
                    return string + Lang.get("of.options.mipmap.nearest");
                }
                case 1: {
                    return string + Lang.get("of.options.mipmap.linear");
                }
                case 2: {
                    return string + Lang.get("of.options.mipmap.bilinear");
                }
                case 3: {
                    return string + Lang.get("of.options.mipmap.trilinear");
                }
            }
            return string + "of.options.mipmap.nearest";
        }
        if (abstractOption == AbstractOption.AA_LEVEL) {
            int n = (int)d;
            Object object = "";
            if (n != Config.getAntialiasingLevel()) {
                object = " (" + Lang.get("of.general.restart") + ")";
            }
            return n == 0 ? string + Lang.getOff() + (String)object : string + n + (String)object;
        }
        if (abstractOption == AbstractOption.AF_LEVEL) {
            int n = (int)d;
            return n == 1 ? string + Lang.getOff() : string + n;
        }
        return null;
    }

    public static boolean supportAdjusting(SliderPercentageOption sliderPercentageOption) {
        ITextComponent iTextComponent = FloatOptions.getTextComponent(sliderPercentageOption, 0.0);
        return iTextComponent != null;
    }
}

