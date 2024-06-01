package com.polarware.command.impl;

import com.polarware.Client;
import com.polarware.command.Command;
import com.polarware.module.api.DevelopmentFeature;
import com.polarware.util.chat.ChatUtil;

/**
 * @author Alan
 * @since 10/19/2021
 */
@DevelopmentFeature
public final class DeveloperReload extends Command {

    public DeveloperReload() {
        super("Reloads the client", "developerreload", "dr");
    }

    @Override
    public void execute(final String[] args) {
        Client.INSTANCE.terminate();
        Client.INSTANCE.initRise();
        ChatUtil.display("Reloaded Rise");
    }
}