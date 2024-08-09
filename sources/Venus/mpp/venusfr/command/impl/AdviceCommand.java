/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.command.impl;

import mpp.venusfr.command.Command;
import mpp.venusfr.command.CommandProvider;
import mpp.venusfr.command.CommandWithAdvice;
import mpp.venusfr.command.Logger;
import mpp.venusfr.command.Parameters;
import mpp.venusfr.command.impl.CommandException;
import net.minecraft.util.text.TextFormatting;

public class AdviceCommand
implements Command {
    private final CommandProvider commandProvider;
    private final Logger logger;

    @Override
    public void execute(Parameters parameters) {
        String string = parameters.asString(0).orElseThrow(AdviceCommand::lambda$execute$0);
        Command command = this.commandProvider.command(string);
        if (!(command instanceof CommandWithAdvice)) {
            throw new CommandException(TextFormatting.RED + "\u041a \u0434\u0430\u043d\u043d\u043e\u0439 \u043a\u043e\u043c\u0430\u043d\u0434\u0435 \u043d\u0435\u0442 \u0441\u043e\u0432\u0435\u0442\u043e\u0432!");
        }
        CommandWithAdvice commandWithAdvice = (CommandWithAdvice)((Object)command);
        this.logger.log(TextFormatting.WHITE + "\u041f\u0440\u0438\u043c\u0435\u0440 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u044f \u043a\u043e\u043c\u0430\u043d\u0434\u044b:");
        for (String string2 : commandWithAdvice.adviceMessage()) {
            this.logger.log(TextFormatting.GRAY + string2);
        }
    }

    @Override
    public String name() {
        return "advice";
    }

    @Override
    public String description() {
        return "null";
    }

    public AdviceCommand(CommandProvider commandProvider, Logger logger) {
        this.commandProvider = commandProvider;
        this.logger = logger;
    }

    private static CommandException lambda$execute$0() {
        return new CommandException("\u0412\u044b \u043d\u0435 \u0443\u043a\u0430\u0437\u0430\u043b\u0438 \u0438\u043c\u044f \u043a\u043e\u043c\u0430\u043d\u0434\u044b");
    }
}

