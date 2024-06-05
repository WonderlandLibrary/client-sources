package tk.rektsky.Commands.Commands;

import tk.rektsky.Commands.*;
import com.mojang.realmsclient.gui.*;
import tk.rektsky.*;
import tk.rektsky.Module.*;

public class ToggleCommand extends Command
{
    public ToggleCommand() {
        super("toggle", new String[] { "t" }, "<Module Name>", "Toggle the Module");
    }
    
    @Override
    public void onCommand(final String label, final String[] args) {
        if (args.length != 1) {
            HelpCommand.displayCommandInfomation(this);
            return;
        }
        for (final Module module : ModulesManager.getModules()) {
            if (module.name.equalsIgnoreCase(args[0])) {
                module.toggle();
                if (module.isToggled()) {
                    Client.addClientChat(ChatFormatting.GREEN + "Enabled Module: " + module.name);
                }
                else {
                    Client.addClientChat(ChatFormatting.RED + "Disabled Module: " + module.name);
                }
                return;
            }
        }
        Client.addClientChat(ChatFormatting.RED + "Module Not Found!");
    }
}
