package dev.excellent.client.command.impl;

import dev.excellent.Excellent;
import dev.excellent.client.command.Command;

public final class PanicCommand extends Command {
    public static boolean stopClient;

    public PanicCommand() {
        super("", "panic", "p");
    }

    @Override
    public void execute(final String[] args) {
        Excellent.getInst().getModuleManager().stream().filter(module ->
                !module.getModuleInfo().autoEnabled()).forEach(module -> module.setEnabled(false, false));
    }
}