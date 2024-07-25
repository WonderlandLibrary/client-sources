package club.bluezenith.command.commands;

import club.bluezenith.command.Command;

import static club.bluezenith.BlueZenith.getBlueZenith;

public class FontCommand extends Command {

    public FontCommand() {
        super("Font", "Manage custom fonts.", ".font <action>");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 2 && args[1].equalsIgnoreCase("refresh")) {
            getBlueZenith().getFontRepository().refresh();
            getBlueZenith().getNotificationPublisher().postSuccess(getBlueZenith().getName(), "Reloaded fonts!", 2500);
        }
    }
}
