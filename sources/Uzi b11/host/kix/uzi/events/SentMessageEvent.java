package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import host.kix.uzi.Uzi;
import host.kix.uzi.utilities.minecraft.Logger;
import host.kix.uzi.command.Command;

/**
 * Created by myche on 1/23/2017.
 */
public class SentMessageEvent extends EventCancellable {

    public String message;

    public SentMessageEvent(String message) {
        this.message = message;
    }

    public void checkCommands() {
        if (message.startsWith(".")) {
            for (final Command command : Uzi.getInstance().getCommandManager().getContents()) {
                if (message.split(" ")[0].equalsIgnoreCase("."
                        + command.getLabel())) {
                    try {
                        command.dispatch(message);
                    } catch (final Exception e) {
                        Logger.logToChat("Invalid arguments!");
                    }
                    setCancelled(true);
                } else {
                    for (final String alias : command.getAlias()) {
                        if (message.split(" ")[0].equalsIgnoreCase("." + alias)) {
                            try {
                                command.dispatch(message);
                            } catch (final Exception e) {
                                Logger.logToChat("Invalid arguments!");
                            }
                            setCancelled(true);
                        }
                    }
                }
            }

            if (!isCancelled()) {
                Logger.logToChat("Command \"" + message + "\" was not found!");
                setCancelled(true);
            }
        }
    }
}
