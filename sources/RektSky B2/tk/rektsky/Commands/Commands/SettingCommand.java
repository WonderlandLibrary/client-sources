package tk.rektsky.Commands.Commands;

import tk.rektsky.Commands.*;
import com.mojang.realmsclient.gui.*;
import tk.rektsky.*;
import tk.rektsky.Module.Settings.*;
import tk.rektsky.Module.*;
import java.util.*;

public class SettingCommand extends Command
{
    public SettingCommand() {
        super("setting", "<Module> <Setting Name> [Value (Leave empty to reset)]", "Change setting for a specific Module");
    }
    
    @Override
    public void onCommand(final String label, final String[] args) {
        if (args.length != 3 && args.length != 2) {
            HelpCommand.displayCommandInfomation(this);
            return;
        }
        final Module module = ModulesManager.getModuleByName(args[0]);
        if (module == null) {
            Client.addClientChat(ChatFormatting.RED + "Invalid Module Name!");
            return;
        }
        Setting setting = null;
        for (final Setting s : module.settings) {
            if (s.name.equalsIgnoreCase(args[1])) {
                setting = s;
            }
        }
        if (setting == null) {
            Client.addClientChat(ChatFormatting.RED + "Invalid Setting Name!");
            return;
        }
        if (args.length == 2) {
            setting.setValue(setting.getDefaultValue());
            return;
        }
        setting.setValue(args[2]);
    }
}
