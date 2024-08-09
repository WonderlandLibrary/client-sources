package ru.FecuritySQ.command.imp;


import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.command.Command;
import ru.FecuritySQ.command.CommandAbstract;

@Command(name = "friend", description = "Позволяет добавлять игроков в друзья")
public class FriendCommand extends CommandAbstract {

    @Override
    public void execute(String[] args) throws Exception {
        switch (args[1]) {
            case "add":
                if (args[2].equalsIgnoreCase(Minecraft.getInstance().session.getUsername())) {
                    sendMessage(TextFormatting.WHITE + "втф ты серьезно пытаешься добавить сам себя в друзья?");
                } else {
                    if (FecuritySQ.get().getFriendManager().getFriends().contains(args[2])) {
                        sendMessage(TextFormatting.RED + args[2] + TextFormatting.GRAY + " уже есть в списке друзей");
                    } else {
                        FecuritySQ.get().getFriendManager().addFriend(args[2]);
                        sendMessage(TextFormatting.GRAY + "Успешно добавил " + TextFormatting.RED + args[2]
                                + TextFormatting.GRAY + " в друзья");
                    }
                }
                break;
            case "remove":
                if (FecuritySQ.get().getFriendManager().isFriend(args[2])) {
                    FecuritySQ.get().getFriendManager().removeFriend(args[2]);
                    sendMessage(TextFormatting.RED + args[2] + " " + TextFormatting.GRAY + " был удален из списка друзей");
                } else {
                    sendMessage(TextFormatting.RED + args[2] + " " + TextFormatting.GRAY + " нету в списке друзей");
                }
                break;
            case "clear":
                if (FecuritySQ.get().getFriendManager().getFriends().isEmpty()) {
                    sendMessage("Список друзей пуст");
                } else {
                    FecuritySQ.get().getFriendManager().clearFriend();
                    sendMessage("Список друзей успешно очищен");
                }
                break;
            case "list":
                if (FecuritySQ.get().getFriendManager().getFriends().isEmpty()) {
                    sendMessage("Список друзей пуст");
                    return;
                }
                sendMessage(TextFormatting.GREEN + "Список друзей: ");
                FecuritySQ.get().getFriendManager().getFriends().forEach(friend -> sendMessage(friend.getName()));

                break;
        }
    }

    @Override
    public void error() {
        sendMessage(TextFormatting.GRAY + "Ошибка в использовании" + TextFormatting.WHITE + ":");
        sendMessage(TextFormatting.WHITE + "." + "friend add " + TextFormatting.GRAY + "<"
                + TextFormatting.RED + "name" + TextFormatting.GRAY + ">");
        sendMessage(TextFormatting.WHITE + "." + "friend remove " + TextFormatting.GRAY + "<"
                + TextFormatting.RED + "name" + TextFormatting.GRAY + ">");
        sendMessage(TextFormatting.WHITE + "." + "friend list" + TextFormatting.GRAY + " - показывает список всех фриендов");
        sendMessage(TextFormatting.WHITE + "." + "friend clear" + TextFormatting.GRAY + " - очищает всех фриендов");
    }
}