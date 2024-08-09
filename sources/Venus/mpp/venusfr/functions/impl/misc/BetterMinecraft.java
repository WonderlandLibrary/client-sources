/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;

@FunctionRegister(name="BetterMinecraft", type=Category.Misc)
public class BetterMinecraft
extends Function {
    public final BooleanSetting smoothCamera = new BooleanSetting("\u041f\u043b\u0430\u0432\u043d\u0430\u044f \u043a\u0430\u043c\u0435\u0440\u0430", true);
    public final BooleanSetting smoothTab = new BooleanSetting("\u041f\u043b\u0430\u0432\u043d\u044b\u0439 \u0442\u0430\u0431", true);
    public final BooleanSetting betterTab = new BooleanSetting("\u0423\u043b\u0443\u0447\u0448\u0435\u043d\u043d\u044b\u0439 \u0442\u0430\u0431", true);

    public BetterMinecraft() {
        this.addSettings(this.smoothCamera, this.betterTab, this.smoothTab);
    }
}

