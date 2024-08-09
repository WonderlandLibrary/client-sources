package dev.luvbeeq.baritone.command;

import dev.luvbeeq.baritone.api.command.ICommandSystem;
import dev.luvbeeq.baritone.api.command.argparser.IArgParserManager;
import dev.luvbeeq.baritone.command.argparser.ArgParserManager;

/**
 * @author Brady
 * @since 10/4/2019
 */
public enum CommandSystem implements ICommandSystem {
    INSTANCE;

    @Override
    public IArgParserManager getParserManager() {
        return ArgParserManager.INSTANCE;
    }
}
