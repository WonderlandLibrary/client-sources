package net.shoreline.client.impl.command;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.arguments.StringArgument;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.Globals;
import net.shoreline.client.util.chat.ChatUtil;

/**
 * @author linus
 * @since 1.0
 */
public class HClipCommand extends Command {
    //
    Argument<String> distanceArgument = new StringArgument("Distance", "The distance to horizontally clip");

    /**
     *
     */
    public HClipCommand() {
        super("HClip", "Horizontally clips the player");
    }

    /**
     * Runs when the command is inputted in chat
     */
    @Override
    public void onCommandInput() {
        final String distance = distanceArgument.getValue();
        try {
            double dist = Double.parseDouble(distance);
            double rad = Math.toRadians(Globals.mc.player.getYaw() + 90);
            double x = Math.cos(rad) * dist;
            double z = Math.sin(rad) * dist;
            Managers.POSITION.setPositionXZ(x, z);
        } catch (NumberFormatException ignored) {
            // e.printStackTrace();
            ChatUtil.error("Invalid distance!");
        }
    }
}
