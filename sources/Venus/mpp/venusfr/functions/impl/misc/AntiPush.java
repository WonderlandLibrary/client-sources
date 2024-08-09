/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;

@FunctionRegister(name="AntiPush", type=Category.Player)
public class AntiPush
extends Function {
    private final ModeListSetting modes = new ModeListSetting("\u0422\u0438\u043f", new BooleanSetting("\u0418\u0433\u0440\u043e\u043a\u0438", true), new BooleanSetting("\u0412\u043e\u0434\u0430", false), new BooleanSetting("\u0411\u043b\u043e\u043a\u0438", true));

    public AntiPush() {
        this.addSettings(this.modes);
    }

    public ModeListSetting getModes() {
        return this.modes;
    }
}

