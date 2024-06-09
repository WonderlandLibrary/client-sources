/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import java.io.PrintStream;
import winter.console.cmds.Command;
import winter.utils.friend.FriendUtil;

public class Friend
extends Command {
    public Friend() {
        super("friend");
        this.desc("Adds player to friends");
        this.use("friend [add/remove] [name] [OPTIONAL: alias]");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        System.out.println(args.length);
        if (args[0].equalsIgnoreCase("friend")) {
            if (args.length > 2) {
                if (args[1].equalsIgnoreCase("add")) {
                    if (args.length > 3) {
                        if (!FriendUtil.isAFriend(args[2])) {
                            FriendUtil.addWithAlias(args[2], args[3]);
                            this.printChat("Added " + args[2] + " as a friend.");
                        }
                    } else if (!FriendUtil.isAFriend(args[2])) {
                        FriendUtil.addWithoutAlias(args[2]);
                        this.printChat("Added " + args[2] + " as a friend.");
                    }
                } else if (args[1].equalsIgnoreCase("remove") && FriendUtil.isAFriend(args[2])) {
                    FriendUtil.removeFriend(args[2]);
                    this.printChat("Removed " + args[2] + " from friends.");
                }
            } else {
                this.printChat("Not enough arguments. Required arguments: 3");
            }
        }
    }
}

