/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature;

import de.dietrichpaul.clientbase.ClientBase;

import java.util.Set;
import java.util.TreeSet;

public class FriendList {
    private final Set<String> friends = new TreeSet<>();

    public void add(String name) {
        friends.add(name);
        ClientBase.INSTANCE.getConfigList().friend.save();
    }

    public void remove(String name) {
        friends.remove(name);
        ClientBase.INSTANCE.getConfigList().friend.save();
    }

    public Set<String> getFriends() {
        return friends;
    }
}
