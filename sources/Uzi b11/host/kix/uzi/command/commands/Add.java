package host.kix.uzi.command.commands;

import host.kix.uzi.Uzi;
import host.kix.uzi.command.Command;
import host.kix.uzi.friends.Friend;
import host.kix.uzi.utilities.minecraft.Logger;

/**
 * Created by myche on 3/1/2017.
 */
public class Add extends Command {

    public Add() {
        super("add", "Allows the user to add friends to the KillAura blacklist and fix modules to not attack them.","fadd", "createfran", "ally", "dontattack");
    }

    @Override
    public void dispatch(String p0) {
        final String[] args = p0.split(" ");
        if(args.length > 1){
            String username = args[1];
            String alias = username;

            if(Uzi.getInstance().getFriendManager().get(username).isPresent()) {
                Logger.logToChat("\247b" + username + " is already your friend");
                return;
            }

            if(args.length > 2)
                alias = p0.substring(args[0].length() + args[1].length() + 2);

            Uzi.getInstance().getFriendManager().addContent(new Friend(username, alias));
            Logger.logToChat("Added friend \247b" + username);

        }else{
            Logger.logToChat(args[0]);
        }
    }

}
