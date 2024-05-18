// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import com.klintos.twelve.utils.FileUtils;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.handlers.friend.Friend;

public class Friends extends Cmd
{
    public Friends() {
        super("friend", "Add or remove a player to your friends.", ".friend <Add/Del/Clear/List> <Username> <Alias> ");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        try {
            if (args[1].equalsIgnoreCase("add")) {
                final Friend friend = new Friend(args[2], "");
                if (!Twelve.getInstance().getFriendHandler().isFriend(args[2])) {
                    Twelve.getInstance().getFriendHandler().addFriend(friend);
                    this.addMessage("§c" + args[2] + "§f has been added to the friends.");
                }
                else {
                    this.addMessage("§c" + args[2] + "§f is already your friend.");
                }
            }
            else if (args[1].equalsIgnoreCase("del")) {
                if (Twelve.getInstance().getFriendHandler().isFriend(args[2])) {
                    this.addMessage("§c" + args[2] + "§f has been removed from friends.");
                    Twelve.getInstance().getFriendHandler().delFriend(args[2]);
                }
                else {
                    this.addMessage("§c" + args[2] + "§f is not your friend.");
                }
            }
            else if (args[1].equalsIgnoreCase("clear")) {
                Twelve.getInstance().getFriendHandler().clearFriends();
                this.addMessage("Friends has been cleared.");
            }
            else if (args[1].equalsIgnoreCase("list")) {
                if (Twelve.getInstance().getFriendHandler().getFriendsArray().size() > 0) {
                    this.addMessage("Current friends are " + Twelve.getInstance().getFriendHandler().getFriends() + ".");
                }
                else {
                    this.addMessage("No current friends.");
                }
            }
            else if (args[1].equalsIgnoreCase("rl")) {
                Twelve.getInstance().getFriendHandler().clearFriends();
                FileUtils.loadFriends();
                this.addMessage("Reloaded friends.");
            }
            else {
                this.runHelp();
            }
            FileUtils.saveFriends();
        }
        catch (Exception e) {
            this.runHelp();
        }
    }
}
