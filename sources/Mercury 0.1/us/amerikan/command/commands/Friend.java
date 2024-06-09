/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.command.commands;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import us.amerikan.amerikan;
import us.amerikan.command.Command;
import us.amerikan.manager.FriendManager;

public class Friend
extends Command {
    public Friend() {
        super("friend", "Helps");
    }

    public static void messageWithoutPrefix(String msg) {
        ChatComponentText chat = new ChatComponentText(msg);
        if (msg != null) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(chat);
        }
    }

    public static void messageWithPrefix(String msg) {
        Friend.messageWithoutPrefix(String.valueOf(amerikan.instance.Client_Prefix) + msg);
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 0) {
            Friend.messageWithPrefix("\u00a7c Invalid arguments.");
            return;
        }
        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("add")) {
                boolean successful;
                boolean bl2 = successful = !amerikan.friendmgr.isFriend(args[1]);
                if (args.length == 2) {
                    if (successful) {
                        amerikan.friendmgr.addFriend(args[1]);
                    }
                    Friend.messageWithPrefix(" \u00a77'\u00a7a" + args[1] + "\u00a7a" + "\u00a77'" + (successful ? " \u00a77added to your friend list" : " \u00a77already in your friends list"));
                }
            }
            if (args[0].equalsIgnoreCase("del")) {
                if (amerikan.friendmgr.isFriend(args[1])) {
                    amerikan.friendmgr.removeFriend(args[1]);
                    Friend.messageWithPrefix(" \u00a7a" + args[1] + "\u00a77has been removed from your friends list");
                } else {
                    Friend.messageWithPrefix(" \u00a7a" + args[1] + "\u00a77is not in your friends list");
                }
            }
        } else if (args.length == 1 && (!args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("clear"))) {
            Friend.messageWithPrefix("\u00a7c Invalid arguments.");
            Friend.messageWithPrefix("\u00a77 .friend add \u00a7c\u00a78<\u00a72username\u00a78>");
            Friend.messageWithPrefix("\u00a77 .friend del\u00a7c \u00a78<\u00a72username\u00a78>");
            Friend.messageWithPrefix("\u00a77 .friend list");
            Friend.messageWithPrefix("\u00a77 .friend clear");
        }
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("list")) {
                for (String username : amerikan.friendmgr.getFriends()) {
                    Friend.messageWithPrefix(" \u00a77" + username);
                }
            }
            if (args[0].equalsIgnoreCase("clear")) {
                amerikan.friendmgr.getFriends().clear();
                Friend.messageWithPrefix("\u00a72 Cleared Friends List");
            }
        } else {
            Friend.messageWithPrefix("\u00a7c Invalid arguments.");
            Friend.messageWithPrefix("\u00a77 .friend add \u00a7c\u00a78<\u00a72username\u00a78>");
            Friend.messageWithPrefix("\u00a77 .friend del\u00a7c \u00a78<\u00a72username\u00a78>");
            Friend.messageWithPrefix("\u00a77 .friend list");
            Friend.messageWithPrefix("\u00a77 .friend clear");
        }
    }
}

