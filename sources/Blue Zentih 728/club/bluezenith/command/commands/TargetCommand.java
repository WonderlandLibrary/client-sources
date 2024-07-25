package club.bluezenith.command.commands;

import club.bluezenith.command.Command;

import java.util.List;

import static club.bluezenith.BlueZenith.getBlueZenith;

public class TargetCommand extends Command {
    
    public TargetCommand() {
        super("Target", "Manage your targets.", ".target <(name) | add (name) | remove (name) | clear | list>", "tar");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 1)
            chat("Usage: " + this.syntax);
        else if(args.length == 2) {
            switch (args[1].toLowerCase()) {
                case "clear":
                    getBlueZenith().getTargetManager().clearList();
                    getBlueZenith().getNotificationPublisher().postSuccess("Targets", "Cleared your targets list.", 2500);
                    break;

                case "list":
                    final List<String> targets = getBlueZenith().getTargetManager().getTargetsList();
                    if(targets.isEmpty())
                        getBlueZenith().getNotificationPublisher().postError("Targets", "You don't have any targets!", 2500);
                    else {
                        chat("List of all targets:");
                        targets.forEach(this::chat);
                    }
                    break;

                case "remove":
                    getBlueZenith().getNotificationPublisher().postError("Targets", "Provide an IGN of a target to remove.", 2500);
                    break;

                case "add":
                    getBlueZenith().getNotificationPublisher().postError("Targets", "Provide an IGN of a target to add.", 2500);
                    break;

                default:
                    final boolean flag = getBlueZenith().getTargetManager().isTarget(args[1]);
                    if(flag)
                        getBlueZenith().getNotificationPublisher().postError("Targets", args[1] + " is already on your targets list!", 2500);
                    else {
                        getBlueZenith().getTargetManager().addTarget(args[1]);
                        getBlueZenith().getNotificationPublisher().postSuccess("Targets", args[1] + " is now your target.", 2500);
                    }
                    break;
            }
        } else if(args.length >= 3) {
            switch (args[1].toLowerCase()) {
                case "clear":
                    getBlueZenith().getTargetManager().clearList();
                    getBlueZenith().getNotificationPublisher().postSuccess("Targets", "Cleared your targets list.", 2500);
                    break;

                case "list":
                    final List<String> targets = getBlueZenith().getTargetManager().getTargetsList();
                    if(targets.isEmpty())
                        getBlueZenith().getNotificationPublisher().postError("Targets", "You don't have any tagets!", 2500);
                    else {
                        chat("List of all targets:");
                        targets.forEach(this::chat);
                    }
                    break;

                case "remove":
                    if(getBlueZenith().getTargetManager().isTarget(args[2])) {
                        getBlueZenith().getTargetManager().removeTarget(args[2]);
                        getBlueZenith().getNotificationPublisher().postSuccess("Targets", "Removed " + args[2] + " from targets list.", 2500);
                    } else getBlueZenith().getNotificationPublisher().postError("Targets", args[2] + " is not your target!", 2500);
                    break;

                case "add":
                    if(getBlueZenith().getTargetManager().isTarget(args[2])) {
                        getBlueZenith().getNotificationPublisher().postError("Targets", args[2] + " is already on your targets list!", 2500);
                    } else {
                        getBlueZenith().getTargetManager().addTarget(args[2]);
                        getBlueZenith().getNotificationPublisher().postSuccess("Targets", args[2] + " is now your target.", 2500);
                    }
                    break;
            }
        }
    }
}