/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.settings;

import java.util.function.Supplier;
import mpp.venusfr.functions.settings.Setting;

public interface ISetting {
    public Setting<?> setVisible(Supplier<Boolean> var1);
}

