/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;

@FunctionRegister(name="FullBright", type=Category.Visual)
public class FullBright
extends Function {
    private double previousGamma;
    private static boolean enabled = false;

    @Override
    public void onEnable() {
        enabled = true;
        this.previousGamma = FullBright.mc.gameSettings.gamma;
        FullBright.mc.gameSettings.gamma = 1000.0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        enabled = false;
        FullBright.mc.gameSettings.gamma = this.previousGamma;
        super.onDisable();
    }
}

