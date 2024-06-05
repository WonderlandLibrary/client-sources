/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.command.impl;

import cc.diablo.command.Command;
import cc.diablo.event.impl.ChatEvent;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.manager.friend.Friend;
import cc.diablo.manager.friend.FriendManager;
import com.google.common.eventbus.Subscribe;

public class FriendCommand
extends Command {
    public FriendCommand() {
        super("Friend", "Manages friends for you");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("friend")) {
            switch (message[1]) {
                case "add": {
                    FriendManager.addFriendToList(message[2], message[2]);
                    break;
                }
                case "remove": {
                    FriendManager.removeFriend(message[2]);
                    break;
                }
                case "list": {
                    for (Friend f : FriendManager.friendsList) {
                        ChatHelper.addChat(f.name);
                    }
                    break;
                }
            }
        }
    }
}

