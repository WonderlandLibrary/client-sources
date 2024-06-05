package tk.rektsky.Commands.Commands;

import tk.rektsky.Commands.*;
import tk.rektsky.Files.*;
import com.mojang.realmsclient.gui.*;
import tk.rektsky.*;

public class SaveCommand extends Command
{
    public SaveCommand() {
        super("save", "", "Save settings file (Auto save by default but won't save if game crashed)");
    }
    
    @Override
    public void onCommand(final String label, final String[] args) {
        FileManager.replaceAndSaveSettings();
        Client.addClientChat(ChatFormatting.GREEN + "Setting file saved!");
    }
}
