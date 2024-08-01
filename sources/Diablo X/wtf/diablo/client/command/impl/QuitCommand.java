package wtf.diablo.client.command.impl;

import wtf.diablo.client.command.api.AbstractCommand;
import wtf.diablo.client.command.api.data.Command;

@Command(name = "quit", description = "Quits the game", aliases = {"exit", "shutdown", "stop"})
public final class QuitCommand extends AbstractCommand {
    @Override
    public void execute(String[] args) {
        mc.shutdown();
    }
}
