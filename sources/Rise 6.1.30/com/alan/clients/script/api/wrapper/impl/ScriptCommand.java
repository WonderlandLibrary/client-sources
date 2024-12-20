package com.alan.clients.script.api.wrapper.impl;

import com.alan.clients.Client;
import com.alan.clients.command.Command;
import com.alan.clients.script.api.wrapper.ScriptHandlerWrapper;

public final class ScriptCommand extends ScriptHandlerWrapper<Command> {

    public ScriptCommand(final Command wrapped) {
        super(wrapped);
    }

    public void unregister() {
        Client.INSTANCE.getCommandManager().getCommandList().remove(this.wrapped);
    }

    // TODO: Make command execution again

    public String getName() {
        return this.wrapped.getExpressions()[0];
    }

    public String getDescription() {
        return this.wrapped.getDescription();
    }
}
