/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl.feature;

import java.util.List;
import mpp.venusfr.MacroManager;
import mpp.venusfr.command.Command;
import mpp.venusfr.command.CommandWithAdvice;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.MultiNamedCommand;
import mpp.venusfr.command.Parameters;
import mpp.venusfr.command.Prefix;
import mpp.venusfr.command.impl.CommandException;
import mpp.venusfr.utils.client.KeyStorage;
import mpp.venusfr.venusfr;
import net.minecraft.util.text.TextFormatting;

public class MacroCommand
implements Command,
MultiNamedCommand,
CommandWithAdvice {
    private final MacroManager macroManager;
    private final Prefix prefix;
    private final Logger logger;

    @Override
    public void execute(Parameters parameters) {
        String string;
        switch (string = (String)parameters.asString(0).orElseThrow()) {
            case "add": {
                this.addMacro(parameters);
                break;
            }
            case "remove": {
                this.removeMacro(parameters);
                break;
            }
            case "clear": {
                this.clearMacros();
                break;
            }
            case "list": {
                this.printMacrosList();
                break;
            }
            default: {
                throw new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0442\u0438\u043f \u043a\u043e\u043c\u0430\u043d\u0434\u044b:" + TextFormatting.GRAY + " add, remove, clear, list");
            }
        }
    }

    @Override
    public String name() {
        return "macro";
    }

    @Override
    public String description() {
        return "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0443\u043f\u0440\u0430\u0432\u043b\u044f\u0442\u044c \u043c\u0430\u043a\u0440\u043e\u0441\u0430\u043c\u0438";
    }

    @Override
    public List<String> adviceMessage() {
        String string = this.prefix.get();
        return List.of((Object)(string + "macro add <name> <key> <message> - \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043d\u043e\u0432\u044b\u0439 \u043c\u0430\u043a\u0440\u043e\u0441"), (Object)(string + "macro remove <name> - \u0423\u0434\u0430\u043b\u0438\u0442\u044c \u043c\u0430\u043a\u0440\u043e\u0441"), (Object)(string + "macro list - \u041f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u043c\u0430\u043a\u0440\u043e\u0441\u043e\u0432"), (Object)(string + "macro clear - \u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u043c\u0430\u043a\u0440\u043e\u0441\u043e\u0432"), (Object)("\u041f\u0440\u0438\u043c\u0435\u0440: " + TextFormatting.RED + string + "macro add home H /home home"));
    }

    @Override
    public List<String> aliases() {
        return List.of((Object)"macros");
    }

    private void addMacro(Parameters parameters) {
        String string = parameters.asString(1).orElseThrow(MacroCommand::lambda$addMacro$0);
        String string2 = parameters.asString(2).orElseThrow(MacroCommand::lambda$addMacro$1);
        String string3 = parameters.collectMessage(3);
        if (string3.isEmpty()) {
            throw new CommandException(TextFormatting.RED + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435, \u043a\u043e\u0442\u043e\u0440\u043e\u0435 \u0431\u0443\u0434\u0435\u0442 \u043f\u0438\u0441\u0430\u0442\u044c \u043c\u0430\u043a\u0440\u043e\u0441.");
        }
        Integer n = KeyStorage.getKey(string2.toUpperCase());
        if (n == null) {
            this.logger.log("\u041a\u043b\u0430\u0432\u0438\u0448\u0430 " + string2 + " \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u0430!");
            return;
        }
        this.checkMacroExist(string);
        this.macroManager.addMacro(string, string3, n);
        this.logger.log(TextFormatting.GREEN + "\u0414\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u043c\u0430\u043a\u0440\u043e\u0441 \u0441 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435\u043c " + TextFormatting.RED + string + TextFormatting.GREEN + " \u0441 \u043a\u043d\u043e\u043f\u043a\u043e\u0439 " + TextFormatting.RED + string2 + TextFormatting.GREEN + " \u0441 \u043a\u043e\u043c\u0430\u043d\u0434\u043e\u0439 " + TextFormatting.RED + string3);
    }

    private void removeMacro(Parameters parameters) {
        String string = parameters.asString(1).orElseThrow(MacroCommand::lambda$removeMacro$2);
        venusfr.getInstance().getMacroManager().deleteMacro(string);
        this.logger.log(TextFormatting.GREEN + "\u041c\u0430\u043a\u0440\u043e\u0441 " + TextFormatting.RED + string + TextFormatting.GREEN + " \u0431\u044b\u043b \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0443\u0434\u0430\u043b\u0435\u043d!");
    }

    private void clearMacros() {
        venusfr.getInstance().getMacroManager().clearList();
        this.logger.log(TextFormatting.GREEN + "\u0412\u0441\u0435 \u043c\u0430\u043a\u0440\u043e\u0441\u044b \u0431\u044b\u043b\u0438 \u0443\u0434\u0430\u043b\u0435\u043d\u044b.");
    }

    private void printMacrosList() {
        if (venusfr.getInstance().getMacroManager().isEmpty()) {
            this.logger.log(TextFormatting.RED + "\u0421\u043f\u0438\u0441\u043e\u043a \u043f\u0443\u0441\u0442\u043e\u0439");
            return;
        }
        venusfr.getInstance().getMacroManager().macroList.forEach(this::lambda$printMacrosList$3);
    }

    private void checkMacroExist(String string) {
        if (this.macroManager.hasMacro(string)) {
            throw new CommandException(TextFormatting.RED + "\u041c\u0430\u043a\u0440\u043e\u0441 \u0441 \u0442\u0430\u043a\u0438\u043c \u0438\u043c\u0435\u043d\u0435\u043c \u0443\u0436\u0435 \u0435\u0441\u0442\u044c \u0432 \u0441\u043f\u0438\u0441\u043a\u0435!");
        }
    }

    public MacroCommand(MacroManager macroManager, Prefix prefix, Logger logger) {
        this.macroManager = macroManager;
        this.prefix = prefix;
        this.logger = logger;
    }

    private void lambda$printMacrosList$3(MacroManager.Macro macro) {
        this.logger.log(TextFormatting.WHITE + "\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435: " + TextFormatting.GRAY + macro.getName() + TextFormatting.WHITE + ", \u041a\u043e\u043c\u0430\u043d\u0434\u0430: " + TextFormatting.GRAY + macro.getMessage() + TextFormatting.WHITE + ", \u041a\u043d\u043e\u043f\u043a\u0430: " + TextFormatting.GRAY + macro.getKey());
    }

    private static CommandException lambda$removeMacro$2() {
        return new CommandException(TextFormatting.GRAY + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043c\u0430\u043a\u0440\u043e\u0441\u0430.");
    }

    private static CommandException lambda$addMacro$1() {
        return new CommandException(TextFormatting.GRAY + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u043a\u043d\u043e\u043f\u043a\u0443 \u043f\u0440\u0438 \u043d\u0430\u0436\u0430\u0442\u0438\u0438 \u043a\u043e\u0442\u043e\u0440\u043e\u0439 \u0441\u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u043c\u0430\u043a\u0440\u043e\u0441.");
    }

    private static CommandException lambda$addMacro$0() {
        return new CommandException(TextFormatting.GRAY + "\u0423\u043a\u0430\u0436\u0438\u0442\u0435 \u043d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043c\u0430\u043a\u0440\u043e\u0441\u0430.");
    }
}

