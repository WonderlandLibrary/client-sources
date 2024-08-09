package fun.ellant.command.impl.feature;

import fun.ellant.command.Command;
import fun.ellant.command.Logger;
import fun.ellant.command.Parameters;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemoryCommand implements Command {

    final Logger logger;

    @Override
    public void execute(Parameters parameters) {
        System.gc();
        logger.log("Очистил память.");
    }

    @Override
    public String name() {
        return "memory";
    }

    @Override
    public String description() {
        return "Очищает память";
    }
}
