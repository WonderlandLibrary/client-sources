package us.dev.direkt.command.internal.misc;

import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;

/**
 * @author Foundry
 */
public class Say extends Command {
    public Say() {
        super(Direkt.getInstance().getCommandManager(), "say", "put");
    }

    @Executes
    public void say(String message) {
        Wrapper.sendChatMessage(message);
    }
}
