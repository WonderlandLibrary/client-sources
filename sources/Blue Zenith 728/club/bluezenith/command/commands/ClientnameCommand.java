package club.bluezenith.command.commands;

import club.bluezenith.BlueZenith;
import club.bluezenith.command.Command;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.types.StringValue;

@SuppressWarnings("all")
public class ClientnameCommand extends Command {

    public ClientnameCommand() {
        super("ClientName", "Rename your very first client.", "clientname <new name>", "cname", "clientname");
    }

    @Override
    public void execute(String[] args) {
        StringBuilder output = new StringBuilder();
        if(args.length < 2) {
            BlueZenith.getBlueZenith().getNotificationPublisher().postError("Client name", "Provide a new name!", 2500);
            return;
        }

        for (String arg : args) {
            if(arg == args[0]) continue; //skip first arg

            if(args[args.length - 1] != arg) //check if there are other words after space
            arg += " ";
            output.append(arg);
        }
        ((StringValue) BlueZenith.getBlueZenith().getModuleManager().getModule(HUD.class).getValue("Client name")).set(output.toString());
        BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Client name", "Set name to " + output.toString(), 2500);
    }
}
