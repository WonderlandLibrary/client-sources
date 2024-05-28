package arsenic.command.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.input.Keyboard;

import arsenic.command.Command;
import arsenic.command.CommandInfo;
import arsenic.main.Nexus;
import arsenic.module.Module;
import arsenic.utils.minecraft.PlayerUtils;

@CommandInfo(name = "bind", args = { "name", "key" }, aliases = { "b" }, help = "binds a module to a key", minArgs = 2)
public class BindCommand extends Command {

    @Override
    public void execute(String[] args) {
        Module module = Nexus.getNexus().getModuleManager().getModuleByName(args[0]);
        if (module == null) {
            PlayerUtils.addWaterMarkedMessageToChat(args[0] + " is not a valid module");
            return;
        }
        int bind = Keyboard.getKeyIndex(args[1].toUpperCase());
        PlayerUtils.addWaterMarkedMessageToChat("Bound " + module.getName() + " to " + Keyboard.getKeyName(bind));
        module.setKeybind(Keyboard.getKeyIndex(args[1].toUpperCase()));

        Nexus.getNexus().getConfigManager().saveConfig();
    }

    @Override
    public List<String> getAutoComplete(String str, int arg, List<String> list) {
        return arg == 0 ? Nexus.getNexus().getModuleManager().getModules().stream()
                .filter(m -> m.getName().toLowerCase().startsWith(str.toLowerCase())
                        && m.getName().length() > str.length())
                .map(Module::getName).collect(Collectors.toList()) : list;
    }
}
