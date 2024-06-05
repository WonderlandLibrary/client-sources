package tk.rektsky.Commands.Commands;

import tk.rektsky.Commands.*;
import com.mojang.realmsclient.gui.*;
import tk.rektsky.*;
import tk.rektsky.Utils.*;
import tk.rektsky.Module.*;
import java.util.*;

public class ReloadCommand extends Command
{
    public ReloadCommand() {
        super("reload", "", "Reload the setting file");
    }
    
    @Override
    public void onCommand(final String label, final String[] args) {
        Client.addClientChat(ChatFormatting.YELLOW + "Reloading...");
        new Thread("reload") {
            @Override
            public void run() {
                for (final YamlUtil.ConfiguredModule module : YamlUtil.getModuleSetting()) {
                    ModulesManager.loadModuleSetting(module);
                }
                Client.addClientChat(ChatFormatting.GREEN + "Reload Completed!");
            }
        }.start();
    }
}
