package net.shoreline.client.impl.command;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.init.Managers;

/**
 * @author Shoreline
 * @since 1.0
 */
public class DisableAllCommand extends Command {
    /**
     *
     */
    public DisableAllCommand() {
        super("DisableAll", "Disables all enabled modules");
    }

    @Override
    public void onCommandInput() {
        for (Module module : Managers.MODULE.getModules()) {
            if (module instanceof ToggleModule toggleModule && toggleModule.isEnabled()) {
                toggleModule.disable();
            }
        }
    }
}
