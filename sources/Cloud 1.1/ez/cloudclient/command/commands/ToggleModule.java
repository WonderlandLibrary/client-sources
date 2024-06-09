package ez.cloudclient.command.commands;

import ez.cloudclient.command.Command;
import ez.cloudclient.module.Module;
import ez.cloudclient.module.ModuleManager;
import ez.cloudclient.util.MessageUtil;

public class ToggleModule extends Command {

    /*
     * Added by Wnuke 11/07/20
     * Modified by RemainingToast 12/07/20
     */


    public ToggleModule() {
        super("Module Toggle", "Toggles modules on and off.", "t", "toggle");
    }

    @Override
    public void call(String[] args) {
        if (args.length >= 1) {
            if (args[0] != null) {
                for (Module module : ModuleManager.modules) {
                    String moduleName = args[0].replaceAll(" ", "");
                    Module mod = ModuleManager.getModuleByName(moduleName.toLowerCase());
                    if (mod != null) {
                        mod.toggle();
                        if (mod.isEnabled()) {
                            MessageUtil.sendMessage(mod.getDisplayName() + " has been toggled.", MessageUtil.Color.GREEN);
                        } else {
                            MessageUtil.sendMessage(mod.getDisplayName() + " has been disabled.", MessageUtil.Color.RED);
                        }

                    } else {
                        MessageUtil.sendMessage(moduleName + " is not a module!", MessageUtil.Color.RED);
                    }
                    break;
                }
            } else {
                MessageUtil.sendMessage("Invalid Arguments!", MessageUtil.Color.RED);
            }
        } else {
            MessageUtil.sendMessage("Invalid Arguments!", MessageUtil.Color.RED);
        }
    }
}

