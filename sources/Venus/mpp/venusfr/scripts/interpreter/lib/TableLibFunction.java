/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.lib;

import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.lib.LibFunction;

class TableLibFunction
extends LibFunction {
    TableLibFunction() {
    }

    @Override
    public LuaValue call() {
        return TableLibFunction.argerror(1, "table expected, got no value");
    }
}

