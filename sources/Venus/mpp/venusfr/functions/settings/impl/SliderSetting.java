/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.settings.impl;

import java.util.function.Supplier;
import mpp.venusfr.functions.settings.Setting;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SliderSetting
extends Setting<Float> {
    public float min;
    public float max;
    public float increment;

    public SliderSetting(String string, float f, float f2, float f3, float f4) {
        super(string, Float.valueOf(f));
        this.min = f2;
        this.max = f3;
        this.increment = f4;
    }

    public SliderSetting setVisible(Supplier<Boolean> supplier) {
        return (SliderSetting)super.setVisible(supplier);
    }

    @Override
    public Setting setVisible(Supplier supplier) {
        return this.setVisible(supplier);
    }
}

