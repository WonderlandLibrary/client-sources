/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.settings.impl;

import java.util.function.Supplier;
import mpp.venusfr.functions.settings.Setting;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ModeSetting
extends Setting<String> {
    public String[] strings;

    public ModeSetting(String string, String string2, String ... stringArray) {
        super(string, string2);
        this.strings = stringArray;
    }

    public int getIndex() {
        int n = 0;
        for (String string : this.strings) {
            if (string.equalsIgnoreCase((String)this.get())) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public boolean is(String string) {
        return ((String)this.get()).equalsIgnoreCase(string);
    }

    public ModeSetting setVisible(Supplier<Boolean> supplier) {
        return (ModeSetting)super.setVisible(supplier);
    }

    @Override
    public Setting setVisible(Supplier supplier) {
        return this.setVisible(supplier);
    }
}

