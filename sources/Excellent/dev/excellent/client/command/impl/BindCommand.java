package dev.excellent.client.command.impl;

import dev.excellent.Excellent;
import dev.excellent.client.command.Command;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.impl.render.ClickGui;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.keyboard.Keyboard;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.KeyValue;
import net.minecraft.util.text.TextFormatting;

public final class BindCommand extends Command {

    public BindCommand() {
        super("", "bind", "keybind");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("clear")) {
                for (Module module : Excellent.getInst().getModuleManager()) {
                    if (module instanceof ClickGui) continue;
                    for (Value<?> value : module.getValues()) {
                        if (value instanceof KeyValue keyValue) {
                            keyValue.setValue(Keyboard.KEY_NONE.keyCode);
                        }
                    }
                    module.setKeyCode(Keyboard.KEY_NONE.keyCode);
                }
                ChatUtil.addText("Бинды модулей очищены");
                return;
            }
            if (args[1].equalsIgnoreCase("list")) {
                if (Excellent.getInst().getModuleManager().stream().filter(module -> module.getKeyCode() != -1).toList().isEmpty()) {
                    ChatUtil.addText("Список биндов пуст");
                    return;
                }
                StringBuilder bindList = new StringBuilder();
                bindList.append("Список биндов:\n");
                for (Module module : Excellent.getInst().getModuleManager()) {
                    if (module.getKeyCode() != -1) {
                        bindList.append(TextFormatting.GRAY)
                                .append(module.getDisplayName())
                                .append(": ")
                                .append(TextFormatting.WHITE)
                                .append(Keyboard.keyName(module.getKeyCode()));
                        if (!Excellent.getInst().getModuleManager().isEmpty() && Excellent.getInst().getModuleManager().get(Excellent.getInst().getModuleManager().size() - 1).equals(module))
                            bindList.append(".");
                        else bindList.append(",\n");

                    }
                }
                ChatUtil.addText(bindList.toString());
                return;
            }
        }
        if (args.length == 3) {
            final Module module = Excellent.getInst().getModuleManager().get(args[1]);

            if (module == null) {
                ChatUtil.addText("Модуль не найден");
                return;
            }

            int keyCode = Keyboard.keyCode(args[2]);

            if (args[2].equalsIgnoreCase("none") || args[2].equalsIgnoreCase("unknown") || args[2].equalsIgnoreCase("clear")) {
                ChatUtil.addText("Бинд модуля " + module.getDisplayName() + " сброшен");
                module.setKeyCode(Keyboard.KEY_NONE.keyCode);
                return;
            }
            if (keyCode != Keyboard.KEY_ESCAPE.keyCode && keyCode != Keyboard.KEY_DELETE.keyCode && keyCode != Keyboard.KEY_BACKSPACE.keyCode) {
                if (keyCode == Keyboard.KEY_NONE.keyCode) {
                    ChatUtil.addText("Неверная клавиша, бинд сброшен");
                } else {
                    ChatUtil.addText(module.getDisplayName() + " забинджен на клавишу " + args[2].toUpperCase());
                }
                module.setKeyCode(keyCode);
            } else ChatUtil.addText(TextFormatting.RED +
                    """
                            На эту клавишу нельзя привязывать функцию.\s
                            Пожалуйста, выберите другую.""");

        } else {
            usage(TextFormatting.RED + ".bind <модуль> <клавиша>");
        }
    }
}