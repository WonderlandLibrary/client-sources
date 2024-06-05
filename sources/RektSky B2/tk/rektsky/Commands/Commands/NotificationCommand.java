package tk.rektsky.Commands.Commands;

import tk.rektsky.Commands.*;
import tk.rektsky.Module.Render.*;

public class NotificationCommand extends Command
{
    public NotificationCommand() {
        super("noti", "<Message>", "Send notification message");
    }
    
    @Override
    public void onCommand(final String label, final String[] args) {
        if (args.length < 1) {
            HelpCommand.displayCommandInfomation(this);
            return;
        }
        final StringBuilder builder = new StringBuilder();
        for (final String arg : args) {
            builder.append(arg + " ");
        }
        Notification.displayNotification(new Notification.PopupMessage("Command", builder.toString(), 10944353, -9240676, 60));
    }
}
