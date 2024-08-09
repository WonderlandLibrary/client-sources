/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command;

import mpp.venusfr.command.CommandProvider;
import mpp.venusfr.command.impl.AdviceCommand;

public interface AdviceCommandFactory {
    public AdviceCommand adviceCommand(CommandProvider var1);
}

