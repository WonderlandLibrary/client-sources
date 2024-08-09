/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl.feature;

import java.util.List;
import mpp.venusfr.command.Command;
import mpp.venusfr.command.CommandWithAdvice;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.Parameters;
import mpp.venusfr.command.Prefix;
import mpp.venusfr.command.impl.CommandException;
import mpp.venusfr.command.staffs.StaffStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;

public class StaffCommand
implements Command,
CommandWithAdvice {
    private final Prefix prefix;
    private final Logger logger;

    @Override
    public void execute(Parameters parameters) {
        String string;
        switch (string = (String)parameters.asString(0).orElseThrow()) {
            case "add": {
                this.addStaffToList(parameters, this.logger);
                break;
            }
            case "remove": {
                this.removeStaffFromList(parameters, this.logger);
                break;
            }
            case "clear": {
                this.clearStaffList(this.logger);
                break;
            }
            case "list": {
                this.getStaffList(this.logger);
                break;
            }
            default: {
                throw new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0442\u0438\u043f \u043a\u043e\u043c\u0430\u043d\u0434\u044b:" + TextFormatting.GRAY + " add, remove, clear, list");
            }
        }
    }

    private void addStaffToList(Parameters parameters, Logger logger) {
        String string = parameters.asString(1).orElseThrow(StaffCommand::lambda$addStaffToList$0);
        if (string.equalsIgnoreCase(Minecraft.getInstance().player.getName().getString())) {
            logger.log(TextFormatting.RED + "\u0412\u044b \u043d\u0435 \u043c\u043e\u0436\u0435\u0442\u0435 \u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0441\u0435\u0431\u044f \u0432 \u0441\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0435\u0440\u0430\u0442\u043e\u0440\u043e\u0432, \u043a\u0430\u043a \u0431\u044b \u0432\u0430\u043c \u043d\u0435 \u0445\u043e\u0442\u0435\u043b\u043e\u0441\u044c");
            return;
        }
        if (StaffStorage.isStaff(string)) {
            logger.log(TextFormatting.RED + "\u042d\u0442\u043e\u0442 \u0438\u0433\u0440\u043e\u043a \u0443\u0436\u0435 \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0441\u044f \u0432 \u0441\u043f\u0438\u0441\u043a\u0435.");
            return;
        }
        StaffStorage.add(string);
        logger.log(TextFormatting.GRAY + "\u0412\u044b \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0434\u043e\u0431\u0430\u0432\u0438\u043b\u0438 " + TextFormatting.GRAY + string + TextFormatting.GRAY + " \u0432 \u0441\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0435\u0440\u0430\u0442\u043e\u0440\u043e\u0432!");
    }

    private void removeStaffFromList(Parameters parameters, Logger logger) {
        String string = parameters.asString(1).orElseThrow(StaffCommand::lambda$removeStaffFromList$1);
        if (StaffStorage.isStaff(string)) {
            StaffStorage.remove(string);
            logger.log(TextFormatting.GRAY + "\u0412\u044b \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0443\u0434\u0430\u043b\u0438\u043b\u0438 " + TextFormatting.GRAY + string + TextFormatting.GRAY + " \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430!");
            return;
        }
        logger.log(TextFormatting.RED + string + " \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d \u0432 \u0441\u043f\u0438\u0441\u043a\u0435 \u0434\u0440\u0443\u0437\u0435\u0439");
    }

    private void getStaffList(Logger logger) {
        if (StaffStorage.getStaffs().isEmpty()) {
            logger.log(TextFormatting.RED + "\u0421\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0435\u0440\u0430\u0442\u043e\u0440\u043e\u0432 \u043f\u0443\u0441\u0442\u043e\u0439.");
            return;
        }
        logger.log(TextFormatting.GRAY + "\u0421\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0435\u0440\u0430\u0442\u043e\u0440\u043e\u0432:");
        for (String string : StaffStorage.getStaffs()) {
            logger.log(TextFormatting.GRAY + string);
        }
    }

    private void clearStaffList(Logger logger) {
        if (StaffStorage.getStaffs().isEmpty()) {
            logger.log(TextFormatting.RED + "\u0421\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0435\u0440\u0430\u0442\u043e\u0440\u043e\u0432 \u043f\u0443\u0441\u0442\u043e\u0439.");
            return;
        }
        StaffStorage.clear();
        logger.log(TextFormatting.GRAY + "\u0421\u043f\u0438\u0441\u043e\u043a \u043c\u043e\u0434\u0435\u0440\u0430\u0442\u043e\u0440\u043e\u0432 \u043e\u0447\u0438\u0449\u0435\u043d.");
    }

    @Override
    public String name() {
        return "staff";
    }

    @Override
    public String description() {
        return "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0443\u043f\u0440\u0430\u0432\u043b\u044f\u0442\u044c \u0441\u043f\u0438\u0441\u043a\u043e\u043c \u0441 \u043d\u0438\u043a\u0430\u043c\u0438 \u043c\u043e\u0434\u0435\u0440\u0430\u0446\u0438\u0438";
    }

    @Override
    public List<String> adviceMessage() {
        String string = this.prefix.get();
        return List.of((Object)(string + "staff add <name> - \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043d\u0438\u043a \u0432 \u0441\u043f\u0438\u0441\u043e\u043a"), (Object)(string + "staff remove <name> - \u0423\u0434\u0430\u043b\u0438\u0442\u044c \u043d\u0438\u043a \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430"), (Object)(string + "staff list - \u041f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u043d\u0438\u043a\u043e\u0432 \u043c\u043e\u0434\u0435\u0440\u0430\u0446\u0438\u0438"), (Object)(string + "staff clear - \u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u043d\u0438\u043a\u043e\u0432 \u043c\u043e\u0434\u0435\u0440\u0430\u0446\u0438\u0438"), (Object)("\u041f\u0440\u0438\u043c\u0435\u0440: " + TextFormatting.RED + string + "staff add Sosiloon"));
    }

    public StaffCommand(Prefix prefix, Logger logger) {
        this.prefix = prefix;
        this.logger = logger;
    }

    private static CommandException lambda$removeStaffFromList$1() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0438\u043c\u044f \u043c\u043e\u0434\u0435\u0440\u0430\u0442\u043e\u0440\u0430 \u0434\u043b\u044f \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0438\u044f/\u0443\u0434\u0430\u043b\u0435\u043d\u0438\u044f.");
    }

    private static CommandException lambda$addStaffToList$0() {
        return new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0438\u043c\u044f \u043c\u043e\u0434\u0435\u0440\u0430\u0442\u043e\u0440\u0430 \u0434\u043b\u044f \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0438\u044f/\u0443\u0434\u0430\u043b\u0435\u043d\u0438\u044f.");
    }
}

