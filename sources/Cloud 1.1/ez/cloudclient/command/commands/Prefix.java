package ez.cloudclient.command.commands;

import ez.cloudclient.CloudClient;
import ez.cloudclient.command.Command;
import ez.cloudclient.util.MessageUtil;

public class Prefix extends Command {

    public Prefix() {
        super("Prefix", "Change command prefix", "p", "prefix");
    }

    @Override
    protected void call(String[] args) {
        if (!(CloudClient.PREFIX == null)) {
            if (args.length >= 1) {
                CloudClient.PREFIX = args[0];
                MessageUtil.sendMessage("Command Prefix now set to " + CloudClient.PREFIX, MessageUtil.Color.GREEN);
            }
        }
    }
}
