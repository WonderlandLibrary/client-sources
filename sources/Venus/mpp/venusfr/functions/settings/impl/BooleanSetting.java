/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.settings.impl;

import java.util.function.Supplier;
import mpp.venusfr.functions.settings.Setting;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BooleanSetting
extends Setting<Boolean> {
    public BooleanSetting(String string, Boolean bl) {
        super(string, bl);
    }

    public BooleanSetting setVisible(Supplier<Boolean> supplier) {
        return (BooleanSetting)super.setVisible(supplier);
    }

    @Override
    public Setting setVisible(Supplier supplier) {
        return this.setVisible(supplier);
    }
}

