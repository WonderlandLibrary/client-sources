package club.bluezenith.command.commands;

import club.bluezenith.command.Command;
import club.bluezenith.module.Module;

import static club.bluezenith.BlueZenith.getBlueZenith;

@SuppressWarnings("unused")
public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("Toggle", "Toggle a module.",".t module","t", "enable");
    }
    @Override
    public void execute(String[] args) {
        if(args.length > 1) {
            Module m = getBlueZenith().getModuleManager().getModule(args[1]);
            if(m == null) {
                getBlueZenith().getNotificationPublisher().postError(
                        "Toggle",
                        "Couldn't find that module.",
                        2500
                );
                return;
            }
            m.toggle();

            getBlueZenith().getNotificationPublisher().postInfo(
                    "Toggle",
                    (m.getState() ? "Enabled " : "Disabled ") + m.getName(),
                    2500
            );
        } else {
            chat("Usage: " + this.syntax);
        }
    }
}
