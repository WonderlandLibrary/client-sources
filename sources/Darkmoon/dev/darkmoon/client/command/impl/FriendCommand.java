package dev.darkmoon.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.command.Command;
import dev.darkmoon.client.command.CommandAbstract;
import dev.darkmoon.client.command.CommandManager;
import net.minecraft.client.Minecraft;

@Command(name = "friend", description = "Позволяет управлять списком друзей")
public class FriendCommand extends CommandAbstract {
    @Override
    public void error() {
        sendMessage(ChatFormatting.GRAY + "Ошибка в использовании:");
        sendMessage(ChatFormatting.WHITE + CommandManager.getPrefix() + "friend add " + ChatFormatting.GRAY + "<"
                + ChatFormatting.RED + "name" + ChatFormatting.GRAY + "> - добавить игрока в друзья");
        sendMessage(ChatFormatting.WHITE + CommandManager.getPrefix() + "friend remove " + ChatFormatting.GRAY + "<"
                + ChatFormatting.RED + "name" + ChatFormatting.GRAY + "> - удалить игрока из друзей");
        sendMessage(ChatFormatting.WHITE + CommandManager.getPrefix() + "friend list" + ChatFormatting.GRAY + " - посмотреть список друзей");
        sendMessage(ChatFormatting.WHITE + CommandManager.getPrefix() + "friend clear" + ChatFormatting.GRAY + " - очистить список друзей");
    }

    @Override
    public void execute(String[] args) throws Exception {
        switch (args[1]) {
            case "add":
                if (args[2].equalsIgnoreCase(Minecraft.getMinecraft().getSession().getUsername())) {
                    sendMessage(ChatFormatting.GRAY + "Нельзя добавить самого себя в друзья :)");
                } else {
                    if (DarkMoon.getInstance().getFriendManager().getFriends().contains(args[2])) {
                        sendMessage(ChatFormatting.GRAY + "Игрок " + ChatFormatting.RED + args[2] + ChatFormatting.GRAY + " уже есть в списке друзей.");
                    } else {
                        DarkMoon.getInstance().getFriendManager().addFriend(args[2]);
                        sendMessage(ChatFormatting.GRAY + "Игрок " + ChatFormatting.RED + args[2]
                                + ChatFormatting.GRAY + " успешно добавлен в друзья!");
                    }
                }
                break;
            case "remove":
                if (DarkMoon.getInstance().getFriendManager().isFriend(args[2])) {
                    DarkMoon.getInstance().getFriendManager().removeFriend(args[2]);
                    sendMessage(ChatFormatting.GRAY + "Игрок " + ChatFormatting.RED + args[2] + ChatFormatting.GRAY + " успешно удален из списка друзей.");
                } else {
                    sendMessage(ChatFormatting.GRAY + "Игрока " + ChatFormatting.RED + args[2] + ChatFormatting.GRAY + " нет в списке друзей.");
                }
                break;
            case "clear":
                if (DarkMoon.getInstance().getFriendManager().getFriends().isEmpty()) {
                    sendMessage(ChatFormatting.GRAY + "Список друзей пуст :(");
                } else {
                    DarkMoon.getInstance().getFriendManager().clearFriend();
                    sendMessage(ChatFormatting.GRAY + "Список друзей успешно очищен!");
                }
                break;
            case "list":
                if (DarkMoon.getInstance().getFriendManager().getFriends().isEmpty()) {
                    sendMessage(ChatFormatting.GRAY + "Список друзей пуст :(");
                    return;
                }
                sendMessage(ChatFormatting.GRAY + "Список друзей: ");
                DarkMoon.getInstance().getFriendManager().getFriends().forEach(friend -> sendMessage(friend.getName()));
                break;
        }
    }
}
