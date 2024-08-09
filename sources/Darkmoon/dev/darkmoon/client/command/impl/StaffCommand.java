package dev.darkmoon.client.command.impl;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.command.Command;
import dev.darkmoon.client.command.CommandAbstract;
import dev.darkmoon.client.command.CommandManager;
import dev.darkmoon.client.module.impl.render.StaffList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;

@Command(name = "staff", description = "Позволяет добавить игроков в StaffList")
public class StaffCommand extends CommandAbstract {
    @Override
    public void error() {
        sendMessage(TextFormatting.GRAY + "Ошибка в использовании:");
        sendMessage(TextFormatting.WHITE + CommandManager.getPrefix() + "staff add " + TextFormatting.GRAY + "<"
                + TextFormatting.RED + "name" + TextFormatting.GRAY + "> - добавить игрока в StaffList");
        sendMessage(TextFormatting.WHITE + CommandManager.getPrefix() + "staff remove " + TextFormatting.GRAY + "<"
                + TextFormatting.RED + "name" + TextFormatting.GRAY + "> - удалить игрока из StaffList");
        sendMessage(TextFormatting.WHITE + CommandManager.getPrefix() + "staff list" + TextFormatting.GRAY + " - посмотреть StaffList");
        sendMessage(TextFormatting.WHITE + CommandManager.getPrefix() + "staff clear" + TextFormatting.GRAY + " - очистить StaffList");
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length > 1) {
            switch (args[1]) {
                case "add":
                    if (args[2].equalsIgnoreCase(Minecraft.getMinecraft().getSession().getUsername())) {
                        sendMessage(TextFormatting.GRAY + "Нельзя добавить самого себя в StaffList");
                    } else {
                        if (DarkMoon.getInstance().getStaffManager().getStaff().contains(args[2])) {
                            sendMessage(TextFormatting.GRAY + "Игрок " + TextFormatting.RED + args[2] + TextFormatting.GRAY + " уже есть в StaffList.");
                        } else {
                            DarkMoon.getInstance().getStaffManager().addStaff(args[2]);
                            sendMessage(TextFormatting.GRAY + "Игрок " + TextFormatting.RED + args[2]
                                    + TextFormatting.GRAY + " успешно добавлен в StaffList!");
                            StaffList.updateList();
                        }
                    }
                    break;
                case "remove":
                    if (DarkMoon.getInstance().getStaffManager().isStaff(args[2])) {
                        DarkMoon.getInstance().getStaffManager().removeStaff(args[2]);
                        sendMessage(TextFormatting.GRAY + "Игрок " + TextFormatting.RED + args[2] + TextFormatting.GRAY + " успешно удален из StaffList.");
                        StaffList.updateList();
                    } else {
                        sendMessage(TextFormatting.GRAY + "Игрока " + TextFormatting.RED + args[2] + TextFormatting.GRAY + " нет в StaffList.");
                    }
                    break;
                case "clear":
                    if (DarkMoon.getInstance().getStaffManager().getStaff().isEmpty()) {
                        sendMessage(TextFormatting.GRAY + "StaffList пуст!");
                    } else {
                        DarkMoon.getInstance().getStaffManager().clearStaff();
                        sendMessage(TextFormatting.GRAY + "StaffList очищен!");
                        StaffList.updateList();
                    }
                    break;
                case "list":
                    if (DarkMoon.getInstance().getStaffManager().getStaff().isEmpty()) {
                        sendMessage(TextFormatting.GRAY + "StaffList пуст!");
                        return;
                    }
                    sendMessage(TextFormatting.GRAY + "Staff: ");
                    DarkMoon.getInstance().getStaffManager().getStaff().forEach(this::sendMessage);
                    break;
                default: {
                    error();
                    break;
                }
            }
        } else {
            error();
        }
    }
}
