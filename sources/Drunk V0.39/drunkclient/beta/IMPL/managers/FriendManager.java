/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.managers;

import drunkclient.beta.Client;
import drunkclient.beta.IMPL.managers.FileManager;
import drunkclient.beta.IMPL.managers.FriendManager$1;
import drunkclient.beta.IMPL.managers.Manager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FriendManager
implements Manager {
    private static HashMap friends;

    @Override
    public void init() {
        friends = new HashMap();
        List<String> frriends = FileManager.read("Friends.txt");
        Iterator<String> var3 = frriends.iterator();
        while (true) {
            if (!var3.hasNext()) {
                Client.instance.getCommandManager().add(new FriendManager$1(this, "f", new String[]{"friend", "fren", "fr"}, "add/del/list name alias", "Manage client friends"));
                return;
            }
            String v = var3.next();
            if (v.contains(":")) {
                String name = v.split(":")[0];
                String alias = v.split(":")[1];
                friends.put(name, alias);
                continue;
            }
            friends.put(v, v);
        }
    }

    public static boolean isFriend(String name) {
        return friends.containsKey(name);
    }

    public static String getAlias(Object friends2) {
        return (String)friends.get(friends2);
    }

    public static HashMap getFriends() {
        return friends;
    }

    static HashMap access$0() {
        return friends;
    }
}

