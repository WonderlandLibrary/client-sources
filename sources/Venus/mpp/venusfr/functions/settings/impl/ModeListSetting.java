/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.settings.impl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import mpp.venusfr.functions.settings.Setting;
import mpp.venusfr.functions.settings.impl.BooleanSetting;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ModeListSetting
extends Setting<List<BooleanSetting>> {
    public ModeListSetting(String string, BooleanSetting ... booleanSettingArray) {
        super(string, Arrays.asList(booleanSettingArray));
    }

    public BooleanSetting getValueByName(String string) {
        return ((List)this.get()).stream().filter(arg_0 -> ModeListSetting.lambda$getValueByName$0(string, arg_0)).findFirst().orElse(null);
    }

    public BooleanSetting get(int n) {
        return (BooleanSetting)((List)this.get()).get(n);
    }

    public ModeListSetting setVisible(Supplier<Boolean> supplier) {
        return (ModeListSetting)super.setVisible(supplier);
    }

    @Override
    public Setting setVisible(Supplier supplier) {
        return this.setVisible(supplier);
    }

    private static boolean lambda$getValueByName$0(String string, BooleanSetting booleanSetting) {
        return booleanSetting.getName().equalsIgnoreCase(string);
    }
}

