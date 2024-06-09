package host.kix.uzi.friends;

import host.kix.uzi.Uzi;
import host.kix.uzi.admin.Admin;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.management.ListManager;

import java.io.*;
import java.util.Optional;

/**
 * Created by myche on 3/1/2017.
 */
public class FriendManager extends ListManager<Friend> {


    public Friend findFriendThroughIdentifier(String identifier) {
        for (Friend friend : getContents()) {
            if (friend.getUsername().equalsIgnoreCase(identifier)) {
                return friend;
            }
        }
        return null;
    }

    public Optional<Friend> get(String username) {
        for (Friend friend : getContents())
            if (friend.getUsername().equals(username) || friend.getAlias().equals(username))
                return Optional.of(friend);

        return Optional.empty();
    }

}
