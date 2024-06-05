package gq.vapu.czfclient.Manager;

import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Util.Helper;
import net.minecraft.util.EnumChatFormatting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FriendManager implements Manager {
    private static HashMap friends;

    public static boolean isFriend(String name) {
        return friends.containsKey(name);
    }

    public static String getAlias(Object friends2) {
        return (String) friends.get(friends2);
    }

    public static HashMap getFriends() {
        return friends;
    }

    static HashMap access$0() {
        return friends;
    }

    public void init() {
        friends = new HashMap();
        List frriends = FileManager.read("Friends.txt");
        Iterator var3 = frriends.iterator();

        while (var3.hasNext()) {
            String v = (String) var3.next();
            if (v.contains(":")) {
                String name = v.split(":")[0];
                String alias = v.split(":")[1];
                friends.put(name, alias);
            } else {
                friends.put(v, v);
            }
        }

        Client.instance.getCommandManager().add(new FriendManager$1(this, "f", new String[]{"friend", "fren", "fr"},
                "add/del/list name alias", "Manage client friends"));
    }
}

class FriendManager$1 extends Command {
    final FriendManager this$0;
    private final FriendManager fm;

    FriendManager$1(FriendManager var1, String $anonymous0, String[] $anonymous1, String $anonymous2,
                    String $anonymous3) {
        super($anonymous0, $anonymous1, $anonymous2, $anonymous3);
        this.this$0 = var1;
        this.fm = var1;
    }

    public String execute(String[] args) {
        String friends;
        String fr;
        Iterator var4;
        int var5;
        if (args.length >= 3) {
            if (args[0].equalsIgnoreCase("add")) {
                friends = "";
                friends = friends + String.format("%s:%s%s", args[1], args[2], System.lineSeparator());
                FriendManager.access$0().put(args[1], args[2]);
                Helper.sendMessage(String.format("%s has been added as %s", args[1], args[2]));
                FileManager.save("Friends.txt", friends, true);
            } else if (args[0].equalsIgnoreCase("del")) {
                FriendManager.access$0().remove(args[1]);
                Helper.sendMessage(
                        String.format("%s has been removed from your friends list", args[1]));
            } else if (args[0].equalsIgnoreCase("list")) {
                if (FriendManager.access$0().size() > 0) {
                    var5 = 1;

                    for (var4 = FriendManager.access$0().values().iterator(); var4.hasNext(); ++var5) {
                        fr = (String) var4.next();
                        Helper.sendMessage(String.format("%s. %s", Integer.valueOf(var5), fr));
                    }
                } else {
                    Helper.sendMessage("Get some friends fag lmao");
                }
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                friends = "";
                friends = friends + String.format("%s%s", args[1], System.lineSeparator());
                FriendManager.access$0().put(args[1], args[1]);
                Helper.sendMessage(String.format("%s has been added", args[1], args[1]));
                FileManager.save("Friends.txt", friends, true);
            } else if (args[0].equalsIgnoreCase("del")) {
                FriendManager.access$0().remove(args[1]);
                Helper.sendMessage(
                        String.format("%s has been removed from your friends list", args[1]));
            } else if (args[0].equalsIgnoreCase("list")) {
                if (FriendManager.access$0().size() > 0) {
                    var5 = 1;

                    for (var4 = FriendManager.access$0().values().iterator(); var4.hasNext(); ++var5) {
                        fr = (String) var4.next();
                        Helper.sendMessage(String.format("%s. %s", Integer.valueOf(var5), fr));
                    }
                } else {
                    Helper.sendMessage("Your list don't have any player");
                }
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                if (FriendManager.access$0().size() > 0) {
                    var5 = 1;

                    for (var4 = FriendManager.access$0().values().iterator(); var4.hasNext(); ++var5) {
                        fr = (String) var4.next();
                        Helper.sendMessage(String.format("%s. %s", Integer.valueOf(var5), fr));
                    }
                } else {
                    Helper.sendMessage("Your list don't have any player");
                }
            } else if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("del")) {
                Helper.sendMessage("Correct usage -f add/del <player> | -f list");
            } else {
                Helper.sendMessage(EnumChatFormatting.GRAY + "Please enter a players name");
            }
        } else if (args.length == 0) {
            Helper.sendMessage("Correct usage -f add/del <player> | -f list");
        }

        return null;
    }
}
