/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl;

import mpp.venusfr.command.AdviceCommandFactory;
import mpp.venusfr.command.CommandProvider;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.impl.AdviceCommand;

public class AdviceCommandFactoryImpl
implements AdviceCommandFactory {
    private final Logger logger;

    @Override
    public AdviceCommand adviceCommand(CommandProvider commandProvider) {
        return new AdviceCommand(commandProvider, this.logger);
    }

    public AdviceCommandFactoryImpl(Logger logger) {
        this.logger = logger;
    }
}

