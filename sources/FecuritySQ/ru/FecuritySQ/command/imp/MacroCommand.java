package ru.FecuritySQ.command.imp;


import net.minecraft.util.text.TextFormatting;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.command.Command;
import ru.FecuritySQ.command.CommandAbstract;
import ru.FecuritySQ.command.macro.Macro;


@Command(name = "macro", description = "Позволяет отправить команду по нажатию кнопки")
public class MacroCommand extends CommandAbstract {

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length > 1) {
            switch (args[1]) {
                case "add":
                    StringBuilder sb = new StringBuilder();
                    for (int i = 3; i < args.length; i++) {
                        sb.append(args[i]).append(" ");
                    }

                    FecuritySQ.get().getMacroManager().addMacros(new Macro(sb.toString(), getKeyCodeFromKey(args[2].toUpperCase())));
                    sendMessage(TextFormatting.GREEN + "Добавлен макрос для кнопки" + TextFormatting.RED + " \""
                            + args[2].toUpperCase() + TextFormatting.RED + "\" " + TextFormatting.WHITE + "с командой "
                            + TextFormatting.RED + sb);
                    break;
                case "clear":
                    if (FecuritySQ.get().getMacroManager().getMacros().isEmpty()) {
                        sendMessage(TextFormatting.RED + "Список макросов пуст");
                    } else {
                        sendMessage(TextFormatting.GREEN + "Список Макросов " + TextFormatting.WHITE + "успешно очищен");
                        FecuritySQ.get().getMacroManager().getMacros().clear();
                        FecuritySQ.get().getMacroManager().updateFile();
                    }
                    break;
                case "remove":
                    FecuritySQ.get().getMacroManager().deleteMacro(getKeyCodeFromKey(args[2].toUpperCase()));
                    sendMessage(TextFormatting.GREEN + "Макрос " + TextFormatting.WHITE + "был удален с кнопки "
                            + TextFormatting.RED + "\"" + args[2] + "\"");
                    break;
                case "list":
                    if (FecuritySQ.get().getMacroManager().getMacros().isEmpty()) {
                        sendMessage("Список макросов пуст");
                    } else {
                        sendMessage(TextFormatting.GREEN + "Список макросов: ");
                        FecuritySQ.get().getMacroManager().getMacros()
                                .forEach(macro -> sendMessage(TextFormatting.WHITE + "Команда: " + TextFormatting.RED
                                        + macro.getMessage() + TextFormatting.WHITE + ", Кнопка: " + TextFormatting.RED
                                        + GLFW.glfwGetKeyName(macro.getKey(), 0)));
                    }
                    break;
            }
        } else {
            error();
        }
    }

    @Override
    public void error() {
        sendMessage(TextFormatting.GRAY + "Ошибка в использовании" + TextFormatting.WHITE + ":");
        sendMessage(TextFormatting.WHITE + "." + "macro add " + TextFormatting.GRAY + "<"
                + TextFormatting.RED + "key" + TextFormatting.GRAY + ">" + TextFormatting.GRAY + " message");
        sendMessage(TextFormatting.WHITE + "." + "macro remove " + TextFormatting.GRAY + "<"
                + TextFormatting.RED + "key" + TextFormatting.GRAY + ">");
        sendMessage(TextFormatting.WHITE + "." + "macro list");
        sendMessage(TextFormatting.WHITE + "." + "macro clear");
    }

    public int getKeyCodeFromKey(String key) {
        for(int i = 0; i < 255; i++) {
            if (key.equalsIgnoreCase(GLFW.glfwGetKeyName(i, i)))
                return i;
        }
        return 0;
    }
}
