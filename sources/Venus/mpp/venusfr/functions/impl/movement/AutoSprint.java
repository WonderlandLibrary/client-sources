/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;

@FunctionRegister(name="AutoSprint", type=Category.Movement)
public class AutoSprint
extends Function {
    public BooleanSetting saveSprint = new BooleanSetting("\u0421\u043e\u0445\u0440\u0430\u043d\u044f\u0442\u044c \u0441\u043f\u0440\u0438\u043d\u0442", true);

    public AutoSprint() {
        this.addSettings(this.saveSprint);
    }
}

