package dev.excellent.client.command.impl;

import dev.excellent.Excellent;
import dev.excellent.client.command.Command;
import dev.excellent.client.staff.StaffManager;
import dev.excellent.impl.util.chat.ChatUtil;
import net.minecraft.util.text.TextFormatting;

public class StaffCommand extends Command {
    public StaffCommand() {
        super("", "staff");
    }

    @Override
    public void execute(String[] args) {
        StaffManager staffs = Excellent.getInst().getStaffManager();

        if (args.length == 1)
            usage(TextFormatting.RED + """
                                        
                    .staff add <name>
                    .staff remove <name>
                    .staff clear
                    .staff list               \s""");

        // add
        if (args.length == 3 && args[1].equalsIgnoreCase("add")) {
            if (!staffs.isStaff(args[2])) {
                staffs.addStaff(args[2]);
                ChatUtil.addText(TextFormatting.GREEN + "\"" + args[2] + "\" добавлен в список стаффов.");
            } else {
                ChatUtil.addText(TextFormatting.RED + "\"" + args[2] + "\" уже находится в списке стаффов.");
            }
        }
        // remove
        else if (args.length == 3 && args[1].equalsIgnoreCase("remove")) {
            if (staffs.isStaff(args[2])) {
                staffs.removeStaff(args[2]);
                ChatUtil.addText(TextFormatting.GREEN + "\"" + args[2] + "\" удалён из списка стаффов.");
            } else {
                ChatUtil.addText(TextFormatting.RED + "\"" + args[2] + "\" не находится в списке стаффов.");
            }
        }
        // clear
        else if (args[1].equalsIgnoreCase("clear") && args.length == 2) {
            ChatUtil.addText(TextFormatting.GREEN + "Очищено стаффов: " + staffs.size());
            staffs.clearStaffs();
        }
        // list
        else if (args[1].equalsIgnoreCase("list") && args.length == 2) {
            if (staffs.isEmpty()) {
                ChatUtil.addText(TextFormatting.RED + "Список стаффов пуст.");
            } else {
                ChatUtil.addText(TextFormatting.GRAY + "Количество стаффов: " + staffs.size() + ".");
                staffs.forEach(staff -> ChatUtil.addText(
                        TextFormatting.AQUA + staff
                ));
            }
        } else usage(TextFormatting.RED + """
                                
                .staff add <name>
                .staff remove <name>
                .staff clear
                .staff list               \s""");
    }
}
