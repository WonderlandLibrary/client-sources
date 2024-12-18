/*
 * Decompiled with CFR 0.152.
 */
package net.dev.important.modules.module.modules.color;

import net.dev.important.modules.module.modules.color.ColorMixer;
import net.dev.important.value.IntegerValue;

public class ColorElement
extends IntegerValue {
    public ColorElement(int counter, Material m, IntegerValue basis) {
        super("Color" + counter + "-" + m.getColorName(), 255, 0, 255, () -> (Integer)basis.get() >= counter);
    }

    public ColorElement(int counter, Material m) {
        super("Color" + counter + "-" + m.getColorName(), 255, 0, 255);
    }

    @Override
    protected void onChanged(Integer oldValue, Integer newValue) {
        ColorMixer.regenerateColors(true);
    }

    static enum Material {
        RED("Red"),
        GREEN("Green"),
        BLUE("Blue");

        private final String colName;

        private Material(String name) {
            this.colName = name;
        }

        public String getColorName() {
            return this.colName;
        }
    }
}

