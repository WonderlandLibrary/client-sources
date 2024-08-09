package dev.darkmoon.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.command.Command;
import dev.darkmoon.client.command.CommandAbstract;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.impl.render.ClickGuiModule;
import org.lwjgl.input.Keyboard;

@Command(name = "bind", description = "Позволяет биндить модули")
public class BindCommand extends CommandAbstract {
    @Override
    public void error() {
        sendMessage(ChatFormatting.GRAY + "Ошибка в использовании" + ChatFormatting.WHITE + ":");
        sendMessage(ChatFormatting.WHITE + ".bind " + ChatFormatting.GRAY + "<"
            + ChatFormatting.RED + "name" + ChatFormatting.GRAY + "> <" + ChatFormatting.RED + "key" + ChatFormatting.GRAY + "> - забиндить модуль");
        sendMessage(ChatFormatting.WHITE + ".bind " + ChatFormatting.GRAY + "<"
                + ChatFormatting.RED + "name" + ChatFormatting.GRAY + "> " + ChatFormatting.WHITE + "none" + ChatFormatting.GRAY + " - разбиндить модуль");
        sendMessage(ChatFormatting.WHITE + ".bind list" + ChatFormatting.GRAY + " - список всех биндов");
        sendMessage(ChatFormatting.WHITE + ".bind clear"+ ChatFormatting.GRAY + " - очистить все бинды");
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length >= 2) {
            if (args[1].equals("clear")) {
                for (Module module : DarkMoon.getInstance().getModuleManager().getModules()) {
                    if (module instanceof ClickGuiModule || module.getBind() == 0) continue;
                    module.bind = 0;
                }
                sendMessage(ChatFormatting.GRAY + "Все бинды были очищены!");
                return;
            }
            if (args[1].equals("list")) {
                sendMessage(ChatFormatting.GREEN + "Список биндов: ");
                for (Module module : DarkMoon.getInstance().getModuleManager().getModules()) {
                    if (module.getBind() == 0) continue;
                    if (module.getBind() < 0) {
                        sendMessage(ChatFormatting.WHITE + "Модуль: " + ChatFormatting.RED
                                + module.getName() + ChatFormatting.WHITE + ", Кнопка: " + ChatFormatting.RED
                                + (module.getBind() + 100));
                    } else {
                        sendMessage(ChatFormatting.WHITE + "Модуль: " + ChatFormatting.RED
                                + module.getName() + ChatFormatting.WHITE + ", Клавиша: " + ChatFormatting.RED
                                + Keyboard.getKeyName(module.getBind()));
                    }
                }
                return;
            }
            Module module = DarkMoon.getInstance().getModuleManager().getModule(args[1]);
            if (module == null) {
                sendMessage(ChatFormatting.GRAY + "Модуль " + ChatFormatting.RED + args[1] + ChatFormatting.GRAY + " не существует!");
            } else {
                if (args[2].equalsIgnoreCase("none")) {
                    module.bind = 0;
                    sendMessage(ChatFormatting.GRAY + "Модуль " + ChatFormatting.RED + args[1] + ChatFormatting.GRAY + " был разбинжен!");
                } else {
                    int keyBind = Keyboard.getKeyIndex(args[2].toUpperCase());
                    if (keyBind == 0) {
                        sendMessage(ChatFormatting.GRAY + "Такой клавиши не существует!");
                    } else {
                        module.bind = keyBind;
                        sendMessage(ChatFormatting.GRAY + "Модуль " + ChatFormatting.RED + args[1] + ChatFormatting.GRAY + " был забинжен на клавишу " + ChatFormatting.RED + args[2].toUpperCase());
                    }
                }
            }
        } else {
            error();
        }
    }
}
