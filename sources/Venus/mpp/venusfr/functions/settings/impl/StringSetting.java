/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.settings.impl;

import java.util.function.Supplier;
import mpp.venusfr.functions.settings.Setting;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StringSetting
extends Setting<String> {
    private final String description;
    private boolean onlyNumber;

    public StringSetting(String string, String string2, String string3) {
        super(string, string2);
        this.description = string3;
    }

    public StringSetting(String string, String string2, String string3, boolean bl) {
        super(string, string2);
        this.description = string3;
        this.onlyNumber = bl;
    }

    public StringSetting setVisible(Supplier<Boolean> supplier) {
        return (StringSetting)super.setVisible(supplier);
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isOnlyNumber() {
        return this.onlyNumber;
    }

    @Override
    public Setting setVisible(Supplier supplier) {
        return this.setVisible(supplier);
    }
}

