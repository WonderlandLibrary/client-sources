package club.bluezenith.command.commands;

import club.bluezenith.BlueZenith;
import club.bluezenith.command.Command;
import club.bluezenith.util.client.FileUtil;

public class IgnCommand extends Command {

    public IgnCommand() {
        super("IGN", "Copies your IGN to the clipboard.", ".ign", "ign");
    }

    @Override
    public void execute(String[] args) {
         try {
             FileUtil.setClipboardContents(mc.session.getUsername());
             BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Command", "Copied your username to the clipboard!", 2500);
         } catch (Exception exception) {
             BlueZenith.getBlueZenith().getNotificationPublisher().postError("Command", "Couldn't copy your username.\nReport this.", 2500);
             exception.printStackTrace();
         }
    }
}
