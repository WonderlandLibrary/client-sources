package arsenic.command.impl;

import org.lwjgl.input.Keyboard;

import arsenic.command.Command;
import arsenic.command.CommandInfo;
import arsenic.main.Nexus;
import arsenic.utils.minecraft.PlayerUtils;

@CommandInfo(name = "binds", help = "shows all modules bound to a key")
public class BindsCommand extends Command {

    @Override
    public void execute(String[] args) {
        Nexus.getNexus().getModuleManager().getModules().forEach(module -> {
            if (module.getKeybind() != 0) {
                PlayerUtils.addWaterMarkedMessageToChat(
                        module.getName() + " is bound to " + Keyboard.getKeyName(module.getKeybind()));
            }
        });
    }

}
