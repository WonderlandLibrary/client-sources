// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Cmds;

import net.minecraft.Badlion;
import net.minecraft.client.network.badlion.Mod.Cmd;
import net.minecraft.client.network.badlion.Mod.Cmd.Info;
import net.minecraft.client.network.badlion.Utils.ChatUtils;

@Info(name = "friend", syntax = { "add <name>", "del <name>, list" }, help = "Manages your friends")
public class FriendCmd extends Cmd
{
    @Override
    public void execute(final String[] args) throws Error {
        if (args.length > 2) {
            ChatUtils.sendMessageToPlayer("add <name>, del <name>, list");
        }
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                Badlion.getWinter().friendUtils.addFriend(args[1]);
            }
            else if (args[0].equalsIgnoreCase("del")) {
                Badlion.getWinter().friendUtils.delFriend(args[1]);
            }
            else {
                ChatUtils.sendMessageToPlayer("Args: add <name>, del <name>, list");
            }
        }
        else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                ChatUtils.sendMessageToPlayer("Friends:");
                int i = 0;
                for (final String friend : Badlion.getWinter().friendUtils.getFriends()) {
                    ++i;
                    ChatUtils.sendMessageToPlayer(String.valueOf(i) + ". " + friend);
                }
            }
            else {
                ChatUtils.sendMessageToPlayer("add <name>, del <name>, list");
            }
        }
        else {
            ChatUtils.sendMessageToPlayer("add <name>, del <name>, list");
        }
    }
}
