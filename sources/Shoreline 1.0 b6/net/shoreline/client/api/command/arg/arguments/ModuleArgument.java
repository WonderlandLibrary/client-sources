package net.shoreline.client.api.command.arg.arguments;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.chat.ChatUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author linus
 * @since 1.0
 */
public class ModuleArgument extends Argument<Module> {
    //
    private ArrayList<String> moduleNames;

    /**
     * @param name
     * @param desc
     */
    public ModuleArgument(String name, String desc) {
        super(name, desc);
    }

    /**
     * @see Command#onCommandInput()
     */
    @Override
    public Module getValue() {
        String literal = getLiteral();
        if (literal == null) {
            return null;
        }
        String id = String.format(Module.MODULE_ID_FORMAT, literal.toLowerCase());
        Module module = Managers.MODULE.getModule(id);
        if (module != null) {
            return module;
        }
        ChatUtil.error("Failed to parse Module argument!");
        return null;
    }

    /**
     * @return
     */
    @Override
    public Collection<String> getSuggestions() {
        if (moduleNames != null) {
            return moduleNames;
        }
        moduleNames = new ArrayList<>();
        for (Module module : Managers.MODULE.getModules()) {
            moduleNames.add(module.getName().toLowerCase());
        }
        return moduleNames;
    }
}
