/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.managers;

import drunkclient.beta.API.commands.Command;
import drunkclient.beta.IMPL.managers.FileManager;
import drunkclient.beta.IMPL.managers.FriendManager;
import drunkclient.beta.UTILS.helper.Helper;
import java.util.Iterator;
import net.minecraft.util.EnumChatFormatting;

class FriendManager$1
extends Command {
    private final FriendManager fm;
    final FriendManager this$0;

    FriendManager$1(FriendManager var1, String $anonymous0, String[] $anonymous1, String $anonymous2, String $anonymous3) {
        super($anonymous0, $anonymous1, $anonymous2, $anonymous3);
        this.this$0 = var1;
        this.fm = var1;
    }

    @Override
    public String execute(String[] args) {
        if (args.length >= 3) {
            if (args[0].equalsIgnoreCase("add")) {
                String friends = "";
                friends = friends + String.format("%s:%s%s", args[1], args[2], System.lineSeparator());
                FriendManager.access$0().put(args[1], args[2]);
                Helper.sendMessage(String.format("%s has been added as %s", args[1], args[2]));
                FileManager.save("Friends.txt", friends, true);
                return null;
            }
            if (args[0].equalsIgnoreCase("del")) {
                FriendManager.access$0().remove(args[1]);
                Helper.sendMessage(String.format("%s has been removed from your friends list", args[1]));
                return null;
            }
            if (!args[0].equalsIgnoreCase("list")) return null;
            if (FriendManager.access$0().size() <= 0) {
                Helper.sendMessage("Get some friends fag lmao");
                return null;
            }
            int var5 = 1;
            Iterator var4 = FriendManager.access$0().values().iterator();
            while (var4.hasNext()) {
                String fr = (String)var4.next();
                Helper.sendMessage(String.format("%s. %s", var5, fr));
                ++var5;
            }
            return null;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                String friends = "";
                friends = friends + String.format("%s%s", args[1], System.lineSeparator());
                FriendManager.access$0().put(args[1], args[1]);
                Helper.sendMessage(String.format("%s has been added", args[1], args[1]));
                FileManager.save("Friends.txt", friends, true);
                return null;
            }
            if (args[0].equalsIgnoreCase("del")) {
                FriendManager.access$0().remove(args[1]);
                Helper.sendMessage(String.format("%s has been removed from your friends list", args[1]));
                return null;
            }
            if (!args[0].equalsIgnoreCase("list")) return null;
            if (FriendManager.access$0().size() <= 0) {
                Helper.sendMessage("Your list don't have any player");
                return null;
            }
            int var5 = 1;
            Iterator var4 = FriendManager.access$0().values().iterator();
            while (var4.hasNext()) {
                String fr = (String)var4.next();
                Helper.sendMessage(String.format("%s. %s", var5, fr));
                ++var5;
            }
            return null;
        }
        if (args.length != 1) {
            if (args.length != 0) return null;
            Helper.sendMessage("Correct usage -f add/del <player> | -f list");
            return null;
        }
        if (args[0].equalsIgnoreCase("list")) {
            if (FriendManager.access$0().size() <= 0) {
                Helper.sendMessage("Your list don't have any player");
                return null;
            }
            int var5 = 1;
            Iterator var4 = FriendManager.access$0().values().iterator();
            while (var4.hasNext()) {
                String fr = (String)var4.next();
                Helper.sendMessage(String.format("%s. %s", var5, fr));
                ++var5;
            }
            return null;
        }
        if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("del")) {
            Helper.sendMessage("Correct usage -f add/del <player> | -f list");
            return null;
        }
        Helper.sendMessage((Object)((Object)EnumChatFormatting.GRAY) + "Please enter a players name");
        return null;
    }
}

