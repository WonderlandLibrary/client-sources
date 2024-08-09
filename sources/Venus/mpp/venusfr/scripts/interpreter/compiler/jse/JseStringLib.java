/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter.compiler.jse;

import mpp.venusfr.scripts.interpreter.lib.StringLib;

public class JseStringLib
extends StringLib {
    @Override
    protected String format(String string, double d) {
        String string2;
        try {
            string2 = String.format(string, d);
        } catch (Throwable throwable) {
            string2 = super.format(string, d);
        }
        return string2;
    }
}

