package us.dev.direkt.command.internal.core;

import us.dev.direkt.Direkt;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.file.internal.files.ModulesFile;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.ToggleableModule;

import java.util.Optional;

/**
 * @author Foundry
 */
public class Toggle extends Command {
    public Toggle() {
        super(Direkt.getInstance().getCommandManager(), "toggle", "t");
    }

    @Executes
    public String run(String moduleName) {
        final Optional<Module> modTry = Direkt.getInstance().getModuleManager().findModule(moduleName);
        if (modTry.isPresent()) {
            final Module module = modTry.get();
            if (!(module instanceof ToggleableModule)) {
                return String.format("%s is not a toggleable module", moduleName);
            }
            final ToggleableModule toggleable = (ToggleableModule) module;
            toggleable.toggle();
            Direkt.getInstance().getFileManager().getFile(ModulesFile.class).save();
            return module.getLabel() + " has been" + (!toggleable.isRunning() ? "\2474 disabled." : "\2472 enabled.");
        }
        else {
            return String.format("%s is not a registered module name", moduleName);
        }
    }
}
