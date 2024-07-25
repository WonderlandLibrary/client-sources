package club.bluezenith.command.commands;

import club.bluezenith.BlueZenith;
import club.bluezenith.command.Command;
import club.bluezenith.module.Module;

@SuppressWarnings("unused")
public class RenameCommand extends Command {

    public RenameCommand() {
        super("Rename", "Rename a module in the arraylist.",".rename <module> <new name>", "rn");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 1) {
            chat("Usage: " + this.syntax);
            return;
        }
        if(args.length == 2) {
            if(args[1].equalsIgnoreCase("reset")) {
                BlueZenith.getBlueZenith().getModuleManager().getModules().forEach(a -> a.displayName = a.defaultDisplayName);
                BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Command", "Reset all module names!", 2500);
            }
        } else {
            Module mod = BlueZenith.getBlueZenith().getModuleManager().getModule(args[1]);
            if (mod == null) {
                BlueZenith.getBlueZenith().getNotificationPublisher().postError("Command", "Couldn't find that module", 2500);
                return;
            }
            String newName = args[2];
            if (newName.equalsIgnoreCase("reset")) {
                mod.displayName = mod.defaultDisplayName;
                BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Command", "Reset name for " + mod.getName(), 2500);
            } else {
                mod.displayName = args[2].replaceAll("_", " ").replaceAll("&", "§");
                BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Command", "Renamed " + mod.getName() + " to " + mod.displayName, 2800);
            }
        }
    }
}
