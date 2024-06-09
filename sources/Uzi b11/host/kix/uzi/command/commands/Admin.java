package host.kix.uzi.command.commands;

import host.kix.uzi.Uzi;
import host.kix.uzi.command.Command;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.Logger;

/**
 * Created by Kix on 5/31/2017.
 * Made for the eclipse project.
 */
public class Admin extends Command {

    public Admin() {
        super("Admin", "Allows the user to add and remove admins", "admin", "aa", "ar");
    }

    @Override
    public void dispatch(String message) {
        String[] args = message.split(" ");
        if (!Uzi.getInstance().getAdminManager().get(args[1]).isPresent()) {
            Uzi.getInstance().getAdminManager().addContent(new host.kix.uzi.admin.Admin(args[1]));
            Logger.logToChat(args[1] + " has been added to your admin list.");
        } else {
            Uzi.getInstance().getAdminManager().removeContent(Uzi.getInstance().getAdminManager().get(args[1]).get());
            Logger.logToChat(args[1] + " has been removed from your admin list.");
        }
    }
}
