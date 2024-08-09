package dev.luvbeeq.baritone.api.command;

import dev.luvbeeq.baritone.api.command.argparser.IArgParserManager;

/**
 * @author Brady
 * @since 10/4/2019
 */
public interface ICommandSystem {

    IArgParserManager getParserManager();
}
