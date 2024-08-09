package dev.excellent.client.command.impl;


import dev.excellent.Excellent;
import dev.excellent.client.command.Command;
import dev.excellent.client.macros.MacrosManager;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.keyboard.Keyboard;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

public class MacrosCommand extends Command {
    public MacrosCommand() {
        super("", "macro", "macros");
    }

    @Override
    public void execute(String[] args) {

        if (args.length == 1)
            usage(TextFormatting.RED + """
                                        
                    .macros add <name> <key> <message>
                    .macros remove <name>
                    .macros clear
                    .macros list               \s""");

        MacrosManager macros = Excellent.getInst().getMacrosManager();

        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (String arg : args) {
            if (i >= 4) {
                sb.append(arg);
                sb.append(" ");
            }
            i++;
        }

        String message = sb.toString();

        // add
        if (args[1].equalsIgnoreCase("add") && args.length >= 5) {
            int keyCode = Keyboard.keyCode(args[3]);
            if (keyCode == Keyboard.KEY_NONE.keyCode) {
                ChatUtil.addText(TextFormatting.RED + "Клавишы с названием \"" + args[3] + "\" не существует.");
                return;
            }
            if (keyCode != Keyboard.KEY_ESCAPE.keyCode && keyCode != Keyboard.KEY_DELETE.keyCode && keyCode != Keyboard.KEY_BACKSPACE.keyCode) {

                if (macros.getMacro(args[2]) == null) {
                    macros.addMacro(args[2], keyCode, message);
                    ChatUtil.addText(TextFormatting.GREEN + "Макрос \"" + args[2] + "\" был добавлен.");
                } else
                    ChatUtil.addText(TextFormatting.RED + "Макрос с названием \"" + args[2] + "\" уже существует.");
            } else ChatUtil.addText(TextFormatting.RED +
                    """
                            На эту клавишу нельзя привязывать макрос.\s
                            Пожалуйста, выберите другую.""");
        }

        // remove
        else if (args[1].equalsIgnoreCase("remove") && args.length == 3) {
            if (macros.getMacro(args[2]) != null) {
                macros.removeMacro(args[2]);
                ChatUtil.addText(TextFormatting.GREEN + "Макрос \"" + args[2] + "\" был удалён.");
            } else
                ChatUtil.addText(TextFormatting.RED + "Макрос с названием \"" + args[2] + "\" не существует.");
        }

        // clear
        else if (args[1].equalsIgnoreCase("clear") && args.length == 2) {
            ChatUtil.addText(TextFormatting.GREEN + "Очищено макросов: " + macros.size());
            macros.clearMacros();
        }

        // list
        else if (args[1].equalsIgnoreCase("list") && args.length == 2) {
            if (macros.isEmpty()) {
                ChatUtil.addText(TextFormatting.RED + "Список макросов пуст.");
            } else {
                ChatUtil.addText(TextFormatting.GRAY + "Количество макросов: " + macros.size() + ".");
                macros.forEach(macro -> {
                    String keyName = Arrays.stream(Keyboard.values()).filter(key -> key.keyCode == macro.getKeyCode()).findFirst().orElse(Keyboard.KEY_NONE).name;
                    ChatUtil.addText(
                            TextFormatting.AQUA + macro.getName() + " " +
                                    TextFormatting.DARK_AQUA + "[" + keyName + "]" +
                                    TextFormatting.GRAY + ": " + macro.getMessage()
                    );
                });
            }
        } else usage(TextFormatting.RED + """
                                
                .macros add <name> <key> <message>
                .macros remove <name>
                .macros clear
                .macros list               \s""");

    }
}
