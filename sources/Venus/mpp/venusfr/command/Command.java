/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command;

import mpp.venusfr.command.Parameters;

public interface Command {
    public void execute(Parameters var1);

    public String name();

    public String description();
}

