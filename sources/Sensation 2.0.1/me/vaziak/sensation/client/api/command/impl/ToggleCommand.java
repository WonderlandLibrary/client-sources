package me.vaziak.sensation.client.api.command.impl;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.command.Command;
import me.vaziak.sensation.utils.client.ChatUtils;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", "Toggles a module", "<module>", new String[]{"t"});
    }

    @Override
    public boolean onCommand(String[] args) {
        if (args.length == 2) {
            List<Module> cheats = Sensation.instance.cheatManager.searchRegistryReplaceSpaces(args[1]);

            for (Module module : cheats) {
                if (module.getId().equalsIgnoreCase(args[1])) {
                    module.setState(!module.getState(), true);
                    ChatUtils.log("Toggled " + EnumChatFormatting.RED + module.getId());
                }
            }
        } else {
            return false;
        }

        return true;
    }
}
