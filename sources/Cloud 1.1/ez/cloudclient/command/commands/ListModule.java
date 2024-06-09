package ez.cloudclient.command.commands;

import ez.cloudclient.command.Command;
import ez.cloudclient.module.Module;
import ez.cloudclient.module.ModuleManager;
import ez.cloudclient.util.MessageUtil;

public class ListModule extends Command {

    StringBuilder sb = new StringBuilder();
    Integer i = 0;

    public ListModule() {
        super("ListModule", "Lists all the modules", "list", "modules");
    }

    @Override
    public void call(String[] args) {
        if (mc.player == null) return;
        if (args.length == 0) {
            MessageUtil.sendMessage("List of categories:", MessageUtil.Color.GRAY);
            MessageUtil.sendMessage("  ALL", MessageUtil.Color.GRAY);
            for (Module.Category category : Module.Category.values()) {
                MessageUtil.sendMessage("  " + category.name(), MessageUtil.Color.GRAY);
            }
        } else {
            for (Module.Category category : Module.Category.values()) {
                if (category.name().equalsIgnoreCase(args[0])) {
                    MessageUtil.sendMessage("Modules in " + args[0] + ":", MessageUtil.Color.GRAY);
                    for (Module module : ModuleManager.getModulesInCat(Module.Category.valueOf(args[0].toUpperCase()))) {
                        MessageUtil.sendMessage("  " + module.getDisplayName() + ": " + module.getDesc(), MessageUtil.Color.GRAY);
                    }
                    return;
                } else if (args[0].equalsIgnoreCase("ALL")) {
                    sb.replace(0, sb.capacity(), "");
                    for (Module module : ModuleManager.modules) {
                        i++;
                        sb.append(module.getDisplayName()).append(", ");
                        if (ModuleManager.modules.size() == i) {
                            i = 0;
                            break;
                        }
                    }
                    sb.replace(sb.lastIndexOf(", "), sb.lastIndexOf(", ") + 1, "");
                    MessageUtil.sendMessage(sb.toString(), MessageUtil.Color.GRAY);
                    sb.replace(0, sb.capacity(), "");
                    return;
                }
            }
            MessageUtil.sendMessage(args[0] + " is not a valid category.", MessageUtil.Color.RED);
        }
    }
}
