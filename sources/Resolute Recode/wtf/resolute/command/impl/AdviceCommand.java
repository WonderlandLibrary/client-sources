package wtf.resolute.command.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.text.TextFormatting;
import wtf.resolute.command.*;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdviceCommand implements Command {

    final CommandProvider commandProvider;
    final Logger logger;

    @Override
    public void execute(Parameters parameters) {
        String commandName = parameters.asString(0).orElseThrow(() -> new CommandException("Вы не указали имя команды"));
        Command command = commandProvider.command(commandName);

        if (!(command instanceof CommandWithAdvice commandWithAdvice)) {
            throw new CommandException(TextFormatting.RED + "К данной команде нет советов!");
        }

        logger.log(TextFormatting.WHITE + "Пример использования команды:");
        for (String advice : commandWithAdvice.adviceMessage()) {
            logger.log(TextFormatting.GRAY + advice);
        }
    }

    @Override
    public String name() {
        return "info";
    }

    @Override
    public String description() {
        return "null";
    }
}
