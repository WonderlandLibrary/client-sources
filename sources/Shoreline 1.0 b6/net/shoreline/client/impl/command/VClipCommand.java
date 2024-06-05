package net.shoreline.client.impl.command;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.arguments.StringArgument;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.chat.ChatUtil;

/**
 * @author linus
 * @since 1.0
 */
public class VClipCommand extends Command {
    //
    Argument<String> distanceArgument = new StringArgument("Distance", "The " +
            "distance to vertically clip");

    /**
     *
     */
    public VClipCommand() {
        super("VClip", "Vertically clips the player");
    }

    /**
     * Runs when the command is inputted in chat
     */
    @Override
    public void onCommandInput() {
        final String distance = distanceArgument.getValue();
        try {
            double dist = Double.parseDouble(distance);
            double y = Managers.POSITION.getY();
            if (Math.abs(y) != 256) {
                Managers.POSITION.setPositionY(y + dist);
            }
        } catch (NumberFormatException ignored) {
            // e.printStackTrace();
            ChatUtil.error("Invalid distance!");
        }
    }
}
