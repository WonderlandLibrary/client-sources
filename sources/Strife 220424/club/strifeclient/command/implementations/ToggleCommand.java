package club.strifeclient.command.implementations;

import club.strifeclient.Client;
import club.strifeclient.command.Command;
import club.strifeclient.command.CommandInfo;
import club.strifeclient.module.Module;
import club.strifeclient.util.player.ChatUtil;

@CommandInfo(name = "Toggle", description = "Toggle an module.", aliases = {"t"})
public class ToggleCommand extends Command {
    @Override
    public void execute(String[] args, String name) {
        if (args.length == 1) {
            Module module = Client.INSTANCE.getModuleManager().getModule(args[0]);
            if (module != null) {
                module.setEnabled(!module.isEnabled());
                ChatUtil.sendMessageWithPrefix("&c" + module.getName() + " &7has been &c" + ((module.isEnabled()) ? "Enabled" : "Disabled"));
            } else ChatUtil.sendMessageWithPrefix("Module &c" + args[0] + " &7not found.");
        }
    }
}
