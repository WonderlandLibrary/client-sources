package com.alan.clients.command.impl;

import com.alan.clients.Client;
import com.alan.clients.command.Command;

public final class Panic extends Command {

    public Panic() {
        super("command.panic.description", "panic", "p", "myau");
    }

    @Override
    public void execute(final String[] args) {
        Client.INSTANCE.getModuleManager().getAll().stream().filter(module ->
                !module.getModuleInfo().autoEnabled()).forEach(module -> module.setEnabled(false));
    }
}