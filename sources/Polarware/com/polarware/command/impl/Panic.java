package com.polarware.command.impl;

import com.polarware.Client;
import com.polarware.command.Command;

/**
 * @author Alan
 * @since 3/02/2022
 */
public final class Panic extends Command {

    public Panic() {
        super("command.panic.description", "panic", "p");
    }

    @Override
    public void execute(final String[] args) {
        Client.INSTANCE.getModuleManager().getAll().stream().filter(module ->
                !module.getModuleInfo().autoEnabled()).forEach(module -> module.setEnabled(false));
    }
}