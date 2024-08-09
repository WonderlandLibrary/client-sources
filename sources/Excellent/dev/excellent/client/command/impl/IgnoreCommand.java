package dev.excellent.client.command.impl;

import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.command.Command;
import dev.excellent.impl.util.chat.ChatUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class IgnoreCommand extends Command {
    private final List<String> ignores = new ArrayList<>();

    public IgnoreCommand() {
        super("", "ignore");
    }

    @Override
    public void execute(String[] args) {

        if (args.length == 1)
            usage(TextFormatting.RED + """
                                        
                    .ignore add <name>
                    .ignore remove <name>
                    .ignore clear
                    .ignore list               \s""");

        // add
        if (args.length == 3 && args[1].equalsIgnoreCase("add")) {
            if (!isIgnore(args[2])) {
                addIgnore(args[2]);
                ChatUtil.addText(TextFormatting.GREEN + "\"" + args[2] + "\" добавлен в список игнорируемых.");
            } else {
                ChatUtil.addText(TextFormatting.RED + "\"" + args[2] + "\" уже находится в списке игнорируемых.");
            }
        }
        // remove
        else if (args.length == 3 && args[1].equalsIgnoreCase("remove")) {
            if (isIgnore(args[2])) {
                removeIgnore(args[2]);
                ChatUtil.addText(TextFormatting.GREEN + "\"" + args[2] + "\" удалён из списка игнорируемых.");
            } else {
                ChatUtil.addText(TextFormatting.RED + "\"" + args[2] + "\" не находится в списке игнорируемых.");
            }
        }
        // clear
        else if (args[1].equalsIgnoreCase("clear") && args.length == 2) {
            ChatUtil.addText(TextFormatting.GREEN + "Очищено игнорируемых: " + ignores.size());
            clearIgnores();
        }
        // list
        else if (args[1].equalsIgnoreCase("list") && args.length == 2) {
            if (ignores.isEmpty()) {
                ChatUtil.addText(TextFormatting.RED + "Список игнорируемых пуст.");
            } else {
                ChatUtil.addText(TextFormatting.GRAY + "Количество игнорируемых: " + ignores.size() + ".");
                ignores.forEach(ignore -> ChatUtil.addText(
                        TextFormatting.AQUA + ignore
                ));
            }
        } else usage(TextFormatting.RED + """
                                
                .ignore add <name>
                .ignore remove <name>
                .ignore clear
                .ignore list               \s""");
    }

    public void addIgnore(String name) {
        ignores.add(name);
    }


    public boolean isIgnore(String name) {
        return ignores.contains(name);
    }

    public void removeIgnore(String name) {
        ignores.removeIf(x -> x.equalsIgnoreCase(name));
    }

    public void clearIgnores() {
        ignores.clear();
    }

    private final Listener<PacketEvent> onPacket = event -> {
        if (event.isSent()) return;
        IPacket<?> packet = event.getPacket();
        if (packet instanceof SChatPacket wrapper) {
            String message = TextFormatting.getTextWithoutFormattingCodes(wrapper.getChatComponent().getString());
            if (message == null) return;

            String[] words = message.split("\\s+");
            for (String word : words) {
                if (isIgnore(word)) {
                    event.cancel();
                    break;
                }
            }
        }
    };

}
