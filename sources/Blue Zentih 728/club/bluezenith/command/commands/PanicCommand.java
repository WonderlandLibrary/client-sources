package club.bluezenith.command.commands;

import club.bluezenith.BlueZenith;
import club.bluezenith.command.Command;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;

public class PanicCommand extends Command {

    public PanicCommand() {
        super("Panic", "Disable all modules.", "panic (norender)");
    }

    @Override
    public void execute(String[] args) {
        boolean norender = false;
        if(args.length >= 2) {
            if(args[1].equalsIgnoreCase("norender"))
                norender = true;
        }
        for(Module m : BlueZenith.getBlueZenith().getModuleManager().getModules()) {
            if(m.getCategory() != ModuleCategory.RENDER || !norender) {
                m.setState(false);
            }
        }
        BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Panic", "Disabled all modules!", 2500);
    }
}
