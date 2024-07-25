package club.bluezenith.command.commands;

import club.bluezenith.command.Command;
import club.bluezenith.util.player.MovementUtil;

public class HClipCommand extends Command {

    public HClipCommand() {
        super("HClip", "Teleports forward.", ".hclip <dist>");
    }

    @Override
    public void execute(String[] args) {
        if(args.length < 2) {
            chat("Usage: " + syntax);
            return;
        }
        try {
            final float dist = Float.parseFloat(args[1]);
            MovementUtil.hClip(dist);
        } catch (NumberFormatException exception) {
            chat("Input a valid number.");
        }
    }
}
