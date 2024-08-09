/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.settings;

import java.util.function.Supplier;
import mpp.venusfr.functions.settings.ISetting;

public class Setting<Value>
implements ISetting {
    Value defaultVal;
    String settingName;
    public Supplier<Boolean> visible = Setting::lambda$new$0;

    public Setting(String string, Value Value2) {
        this.settingName = string;
        this.defaultVal = Value2;
    }

    public String getName() {
        return this.settingName;
    }

    public void set(Value Value2) {
        this.defaultVal = Value2;
    }

    @Override
    public Setting<?> setVisible(Supplier<Boolean> supplier) {
        this.visible = supplier;
        return this;
    }

    public Value get() {
        return this.defaultVal;
    }

    private static Boolean lambda$new$0() {
        return true;
    }
}

