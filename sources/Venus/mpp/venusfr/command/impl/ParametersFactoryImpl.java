/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl;

import mpp.venusfr.command.Parameters;
import mpp.venusfr.command.ParametersFactory;
import mpp.venusfr.command.impl.ParametersImpl;

public class ParametersFactoryImpl
implements ParametersFactory {
    @Override
    public Parameters createParameters(String string, String string2) {
        return new ParametersImpl(string.split(string2));
    }
}

