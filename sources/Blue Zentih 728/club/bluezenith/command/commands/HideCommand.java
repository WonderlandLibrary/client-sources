package club.bluezenith.command.commands;

import club.bluezenith.BlueZenith;
import club.bluezenith.command.Command;
import club.bluezenith.module.Module;

@SuppressWarnings("unused")
public class HideCommand extends Command {

    public HideCommand() {
        super("Hide", "Hide a module from the ArrayList.", ".hide modulename");
    }

    @Override
    public void execute(String[] args) {
        if(args.length < 2) {
            chat("Usage: " + this.syntax);
            return;
        }
        Module mod = BlueZenith.getBlueZenith().getModuleManager().getModule(args[1]);
        if(mod == null) {
            BlueZenith.getBlueZenith().getNotificationPublisher().postError("Command", "Couldn't find that module.", 2500);
            return;
        }
        mod.hidden = !mod.hidden;
        BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Command", mod.getName() + (mod.hidden ? " is now hidden." : " is now shown."), 2500);
    }
}
