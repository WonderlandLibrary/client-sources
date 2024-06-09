package us.dev.direkt.command.internal.core;

import us.dev.direkt.Direkt;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.ToggleableModule;

import java.util.Optional;

/**
 * @author BFCE
 */
public class Lock extends Command {
    public Lock() {
        super(Direkt.getInstance().getCommandManager(), "lock", "l");
    }

    @Executes
    public String run(String moduleName) {
        if (moduleName.equalsIgnoreCase("all")) {
            Direkt.getInstance().getModuleManager().getModules().stream()
                    .filter(ToggleableModule.class::isInstance)
                    .map(ToggleableModule.class::cast)
                    .forEach(module -> module.setLocked(!module.isLocked()));
            return "All bound modules lock\'s toggled";
        } else if (moduleName.equalsIgnoreCase("binds") || moduleName.equalsIgnoreCase("keybinds")) {
            Direkt.getInstance().getModuleManager().getModules().stream()
                    .filter(ToggleableModule.class::isInstance)
                    .map(ToggleableModule.class::cast)
                    .forEach(module -> module.setLocked(!module.isLocked()));
            return "All bound modules lock\'s toggled";
        } else {
            final Optional<Module> modTry = Direkt.getInstance().getModuleManager().findModule(moduleName);
            if (modTry.isPresent()) {
                if (modTry.get() instanceof ToggleableModule) {
                    final ToggleableModule module = (ToggleableModule) modTry.get();
                    module.setLocked(!module.isLocked());
                    return "Module " + module.getDisplayName() + (module.isLocked() ? " Locked" : " Unlocked");
                } else {
                    return "Module " + modTry.get().getLabel() + " is not a toggable module.";
                }
            } else {
                return String.format("No modules were found matching %s", moduleName);
            }
        }
    }
}
