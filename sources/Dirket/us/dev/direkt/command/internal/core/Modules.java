package us.dev.direkt.command.internal.core;

import us.dev.direkt.Direkt;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.ToggleableModule;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Foundry
 */
public class Modules extends Command {
    public Modules() {
        super(Direkt.getInstance().getCommandManager(), "modules", "mods", "modlist", "m");
    }

    @Executes
    public String run(Optional<ModCategory> moduleCategory) {
        if (!moduleCategory.isPresent()) {
            final int[] count = new int[1];
            final String resultString = Direkt.getInstance().getModuleManager().getModules().stream()
                    .filter(module -> module.getCategory() != ModCategory.CORE)
                    .sorted(((m1, m2) -> m1.getLabel().compareTo(m2.getLabel())))
                    .peek(module -> count[0]++)
                    .map(module -> {
                        if (module instanceof ToggleableModule) {
                            return (((ToggleableModule) module).isRunning() ? "\u00A72" : "\u00A74") + module.getLabel().replaceAll(" ", "");
                        } else {
                            return "\u00A77" + module.getLabel().replaceAll(" ", "");
                        }
                    }).collect(Collectors.joining(", ", "\u00A78[\u00A7r", "\u00A78]"));
            return "\u00A7lModules \u00A7r\u00A77(\u00A7f" + count[0] + "\u00A77):" + System.lineSeparator() + resultString;
        } else {
            final int[] count = new int[1];
            final String resultString = Direkt.getInstance().getModuleManager().getModules().stream()
                    .filter(module -> module.getCategory() == moduleCategory.get())
                    .sorted(((m1, m2) -> m1.getLabel().compareTo(m2.getLabel())))
                    .peek(module -> count[0]++)
                    .map(module -> {
                        if (module instanceof ToggleableModule) {
                            return (((ToggleableModule) module).isRunning() ? "\u00A72" : "\u00A74") + module.getLabel().replaceAll(" ", "");
                        } else {
                            return "\u00A77" + module.getLabel().replaceAll(" ", "");
                        }
                    }).collect(Collectors.joining(", ", "\u00A78[\u00A7r", "\u00A78]"));
            return "\u00A7lModules \u00A7r\u00A77(\u00A7f" + count[0] + "\u00A77):" + System.lineSeparator() + resultString;
        }
    }
}
