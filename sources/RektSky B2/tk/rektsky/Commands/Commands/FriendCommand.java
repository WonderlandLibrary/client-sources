package tk.rektsky.Commands.Commands;

import tk.rektsky.Commands.*;
import tk.rektsky.Utils.Display.*;
import tk.rektsky.Module.Render.*;
import tk.rektsky.*;

public class FriendCommand extends Command
{
    public FriendCommand() {
        super("friend", new String[] { "f" }, "<add | remove | list> [player]", "Add friend so KillAura won't hit it");
    }
    
    @Override
    public void onCommand(final String label, final String[] args) {
        Client.notify(new Notification.PopupMessage("Friend System", "Coming Soon!!", ColorUtil.NotificationColors.GREEN, 40));
    }
}
