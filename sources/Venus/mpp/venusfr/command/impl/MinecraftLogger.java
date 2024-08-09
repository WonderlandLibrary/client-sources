/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl;

import mpp.venusfr.command.Logger;
import mpp.venusfr.utils.client.IMinecraft;

public class MinecraftLogger
implements Logger,
IMinecraft {
    @Override
    public void log(String string) {
        this.print(string);
    }
}

