package dev.excellent.client.command.impl;


import dev.excellent.Excellent;
import dev.excellent.client.command.Command;
import dev.excellent.client.friend.FriendManager;
import dev.excellent.impl.util.chat.ChatUtil;
import net.minecraft.util.text.TextFormatting;

public class FriendCommand extends Command {
    public FriendCommand() {
        super("", "friend", "friends");

    }

    @Override
    public void execute(String[] args) {
        FriendManager friends = Excellent.getInst().getFriendManager();

        if (args.length == 1)
            usage(TextFormatting.RED + """
                                        
                    .friend add <name>
                    .friend remove <name>
                    .friend clear
                    .friend list               \s""");

        // add
        if (args.length == 3 && args[1].equalsIgnoreCase("add")) {
            if (!friends.isFriend(args[2])) {
                friends.addFriend(args[2]);
                ChatUtil.addText(TextFormatting.GREEN + "\"" + args[2] + "\" добавлен в список друзей.");
            } else {
                ChatUtil.addText(TextFormatting.RED + "\"" + args[2] + "\" уже находится в списке друзей.");
            }
        }
        // remove
        else if (args.length == 3 && args[1].equalsIgnoreCase("remove")) {
            if (friends.isFriend(args[2])) {
                friends.removeFriend(args[2]);
                ChatUtil.addText(TextFormatting.GREEN + "\"" + args[2] + "\" удалён из списка друзей.");
            } else {
                ChatUtil.addText(TextFormatting.RED + "\"" + args[2] + "\" не находится в списке друзей.");
            }
        }
        // clear
        else if (args[1].equalsIgnoreCase("clear") && args.length == 2) {
            ChatUtil.addText(TextFormatting.GREEN + "Очищено друзей: " + friends.size());
            friends.clearFriends();
        }
        // list
        else if (args[1].equalsIgnoreCase("list") && args.length == 2) {
            if (friends.isEmpty()) {
                ChatUtil.addText(TextFormatting.RED + "Список друзей пуст.");
            } else {
                ChatUtil.addText(TextFormatting.GRAY + "Количество друзей: " + friends.size() + ".");
                friends.forEach(friend -> ChatUtil.addText(
                        TextFormatting.AQUA + friend.getName()
                ));
            }
        } else usage(TextFormatting.RED + """
                                
                .friend add <name>
                .friend remove <name>
                .friend clear
                .friend list               \s""");
    }
}
