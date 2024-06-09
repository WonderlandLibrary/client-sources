package us.dev.direkt.command.internal.core;

import org.lwjgl.input.Keyboard;
import us.dev.direkt.Direkt;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.file.internal.files.ModulesFile;
import us.dev.direkt.keybind.Keybind;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.ToggleableModule;

import java.util.Optional;

/**
 * @author Foundry
 */
public class Bind extends Command {
    public Bind() {
        super(Direkt.getInstance().getCommandManager(), "bind", "b");
    }

    @Executes("add|a")
    protected String addBind(String moduleName, String keyName) {
        final Optional<Module> modTry = Direkt.getInstance().getModuleManager().findModule(moduleName);
        if (modTry.isPresent()) {
            final Module module = modTry.get();
            if (!(module instanceof ToggleableModule)) {
                return String.format("%s is not a toggleable module.", moduleName);
            }
            final Keybind bind = ((ToggleableModule) module).getKeybind();
            final int keyIndex = Keyboard.getKeyIndex(keyName.toUpperCase());
            if (keyIndex == 0) {
                return String.format("%s is not a valid key to bind to.", keyName);
            } else if (keyIndex == bind.getKey()) {
                return String.format("%s is already bound to %s.", module.getLabel(), keyName.toUpperCase());
            }
            bind.setKey(keyIndex);
            Direkt.getInstance().getFileManager().getFile(ModulesFile.class).save();
            return String.format("%s has been bound to %s.", module.getLabel(), keyName.toUpperCase());
        } else {
            return String.format("'%s' is not a registered module name.", moduleName);
        }
    }

    @Executes("del|d")
    protected String removeBind(String modName) {
        final Optional<Module> modTry = Direkt.getInstance().getModuleManager().findModule(modName);
        if (modTry.isPresent()) {
            final Module module = modTry.get();
            if (!(module instanceof ToggleableModule)) {
                return String.format("%s is not a toggleable module.", modName);
            }
            ((ToggleableModule) module).getKeybind().setKey(-1);
            Direkt.getInstance().getFileManager().getFile(ModulesFile.class).save();
            return String.format("%s has been unbound.", module.getLabel());
        } else {
            return String.format("'%s' is not a registered module name.", modName);
        }
    }
}
