package host.kix.uzi.command.commands;

import host.kix.uzi.Uzi;
import host.kix.uzi.command.Command;
import host.kix.uzi.friends.Friend;
import host.kix.uzi.utilities.minecraft.Logger;

import java.util.Optional;

/**
 * Created by myche on 3/1/2017.
 */
public class Remove extends Command {

    public Remove() {
        super("remove", "Allows the user to remove a friend.","delete", "del", "fdel", "attack");
    }

    @Override
    public void dispatch(String p0) {
        String username = p0.split(" ")[1];

        if(!Uzi.getInstance().getFriendManager().get(username).isPresent()) {
            Logger.logToChat(username + " is not your friend");
            return;
        }

        Optional<Friend> foundFriend = Uzi.getInstance().getFriendManager().get(username);

        if(foundFriend.isPresent()){
            Uzi.getInstance().getFriendManager().getContents().remove(foundFriend.get());
            Logger.logToChat("Removed friend \247b" + username);
        }

    }

}
