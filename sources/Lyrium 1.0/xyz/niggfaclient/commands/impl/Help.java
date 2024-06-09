// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.commands.impl;

import xyz.niggfaclient.utils.other.Printer;
import xyz.niggfaclient.commands.Command;

public class Help extends Command
{
    public Help() {
        super("Help", "Helps u on the command list and the description", "h", new String[0]);
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length < 1) {
            Printer.addMessage("§7-------------- §fCommand List: §7--------------\n§f- §cBind§r | Description: §7Binds a module by a key name.\n§f- §cConfig§r | Description: §7Loads or saves a config of the modules.\n§f- §cFriend§r | Description: §7Friends someone so you wont attack him.\n§f- §cHClip§r | Description: §7Horizontal Clips\n§f- §cHelp§r | Description: §Shows every command description.\n§f- §cHide§r | Description: §7Hides or unhides a module by name.\n§f- §cName§r | Description: §7Copies your minecraft user to clipboard.\n§f- §cRename§r | Description: §7Renames the client name.\n§f- §cTeleport§r | Description: §7Teleports to a specify coordinates.\n§f- §cVClip§r | Description: §7Vertical Clips");
        }
    }
}
